package selector;

import ir.IRFunction;
import ir.operand.IRVariableOperand;
import java.util.ArrayList;
import java.util.List;
import mips.*;
import static mips.MipsOperand.*;

public final class Wrapper {
    private Wrapper() {}

    public static int frameSize(frame f) {
        int size = 8 + f.totalVarBytes(); // ra + fp + arrays + spill slots
        if (size % 8 != 0) size += 4;       // 8-byte align
        return size;
    }

    public static List<MipsInstruction> emit(IRFunction fn, List<MipsInstruction> body, frame f) {
        int fs = frameSize(f);
        List<MipsInstruction> out = new ArrayList<>();

        /*
        addi sp, sp, -frameSize
        sw ra, 0(sp)
        sw fp, 4(sp)
        move fp, sp
        */

        out.add(MipsInstruction.label(fn.name));
        out.add(new MipsInstruction(MipsOp.ADDI, preg(PReg.SP), preg(PReg.SP), imm(-fs)));
        out.add(new MipsInstruction(MipsOp.SW, preg(PReg.RA), mem(PReg.SP, 0)));
        out.add(new MipsInstruction(MipsOp.SW, preg(PReg.FP), mem(PReg.SP, 4)));
        // $fp = entry $sp (top of frame) so params 5+ can be read at (i-4)*4($fp)
        out.add(new MipsInstruction(MipsOp.ADDI, preg(PReg.FP), preg(PReg.SP), imm(fs)));

        // param binding now lives in InstructionSelector.bindParams() so the allocator sees it
        out.addAll(body);

        out.add(MipsInstruction.label(fn.name + "_func_end"));
        out.add(new MipsInstruction(MipsOp.LW, preg(PReg.RA), mem(PReg.SP, 0)));
        out.add(new MipsInstruction(MipsOp.LW, preg(PReg.FP), mem(PReg.SP, 4)));
        out.add(new MipsInstruction(MipsOp.ADDI, preg(PReg.SP), preg(PReg.SP), imm(fs)));
        if (fn.name.equals("main")) {
            out.add(new MipsInstruction(MipsOp.LI, preg(PReg.V0), imm(10)));
            out.add(new MipsInstruction(MipsOp.SYSCALL));
        } else {
            out.add(new MipsInstruction(MipsOp.JR, preg(PReg.RA)));
        }
        return out;
    }
}
