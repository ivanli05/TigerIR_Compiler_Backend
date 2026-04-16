package allocator;

import java.util.*;
import mips.*;
import selector.*;

public class NaiveAllocator {
    public static List<MipsInstruction> allocate(List<MipsInstruction> instructions, frame f) {
        for (MipsInstruction i : instructions) {
            for (MipsOperand op : i.operands) {
                if (op.type == MipsOperand.Type.VREG) {
                    f.allocSpill(op.value);
                }
                if (op.type == MipsOperand.Type.MEM && op.base != null && !op.base.startsWith("$")) {
                    f.allocSpill(op.base);
                }
            }
        }
        List<MipsInstruction> res = new ArrayList<>(); 
        for (MipsInstruction i : instructions) {
            if (i.opCode == MipsOp.LABEL || i.opCode == MipsOp.SYSCALL || i.opCode == MipsOp.J || i.opCode == MipsOp.JAL) {
                // nothing special
                res.add(i);
                continue;
            } 
            allocate(i, f, res);
        }
        return res;
    }
    private static boolean hasDef(MipsOp op) {
        return switch (op) {
            case ADD, SUB, MULT, DIV, AND, OR,
                 ADDI, SUBI, ANDI, ORI,
                 SLL, MOVE, LI, LW -> true;
            default -> false;
        };
    }
    private static void allocate(MipsInstruction ins, frame f, List<MipsInstruction> res) {
        MipsOperand[] operands = new MipsOperand[ins.operands.length];
        boolean defi = hasDef(ins.opCode);
        int cur = 0;
        for (int i = 0; i < ins.operands.length; ++i) {
            MipsOperand op = ins.operands[i];
            if (i == 0 && defi) {
                operands[i] = op;
                continue;
            }
            // lw
            if (op.type == MipsOperand.Type.VREG) {
                res.add(new MipsInstruction(MipsOp.LW, MipsOperand.preg(PReg.T[cur++]), MipsOperand.mem(PReg.SP, f.spillOffset(op.value))));
                operands[i] = MipsOperand.preg(PReg.T[cur - 1]);
            } else if (op.type == MipsOperand.Type.MEM && op.base != null && !op.base.startsWith("$")) {
                res.add(new MipsInstruction(MipsOp.LW, MipsOperand.preg(PReg.T[cur++]), MipsOperand.mem(PReg.SP, f.spillOffset(op.base))));
                operands[i] = MipsOperand.mem(PReg.T[cur - 1], op.offset);
            } else {
                operands[i] = op;
            }
        }
        // sw
        String definedReg = null;
        if (defi && ins.operands[0].type == MipsOperand.Type.VREG) {
            definedReg = PReg.T[cur++];
            operands[0] = MipsOperand.preg(definedReg);
        }
        res.add(new MipsInstruction(ins.opCode, operands));
        if (definedReg != null) {
            res.add(new MipsInstruction(MipsOp.SW, MipsOperand.preg(definedReg), MipsOperand.mem(PReg.SP, f.spillOffset(ins.operands[0].value))));
        }
    }

}