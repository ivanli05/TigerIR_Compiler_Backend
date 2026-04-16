package selector;
import ir.*;
import ir.operand.*;
import java.util.ArrayList;
import java.util.List;
import mips.*;
import static mips.MipsOperand.*;
public class InstructionSelector {
    // translate IRInstruction to MipsInstruction
    public List<MipsInstruction> res = new ArrayList<>();
    private static int tmpCounter = 0;
    public frame frame = new frame();
    public IRFunction func;
    private java.util.Set<String> paramNames = new java.util.HashSet<>();

    public List<MipsInstruction> selectBody(IRFunction fn) {
        this.func = fn;
        this.frame = new frame();
        this.res = new ArrayList<>();
        this.paramNames = new java.util.HashSet<>();
        this.allVregNames = new ArrayList<>();
        for (IRVariableOperand p : fn.parameters) paramNames.add(p.getName());
        bindParams(fn);
        initLocals(fn);
        for (IRInstruction ins : fn.instructions) translate(ins);
        return res;
    }

    private List<String> allVregNames = new ArrayList<>();

    private void bindParams(IRFunction fn) {
        List<IRVariableOperand> params = fn.parameters;
        for (int i = 0; i < params.size(); ++i) {
            if (i < 4) {
                res.add(new MipsInstruction(MipsOp.MOVE, vreg(params.get(i).getName()), MipsOperand.preg(PReg.A[i])));
            } else {
                res.add(new MipsInstruction(MipsOp.LW, vreg(params.get(i).getName()),
                    MipsOperand.mem(PReg.FP, (i - 4) * 4)));
            }
        }
    }

    private void initLocals(IRFunction fn) {
        for (IRVariableOperand p : fn.parameters) {
            frame.allocSpill(p.getName());
            allVregNames.add(p.getName());
        }
        for (IRVariableOperand v : fn.variables) {
            String name = v.getName();
            if (paramNames.contains(name)) continue;
            frame.allocSpill(name);
            allVregNames.add(name);
            if (v.type instanceof ir.datatype.IRArrayType at) {
                int off = frame.allocArray(name, at.getSize());
                res.add(new MipsInstruction(MipsOp.ADDI, vreg(name), MipsOperand.preg(PReg.SP), MipsOperand.imm(8 + off)));
            } else {
                res.add(new MipsInstruction(MipsOp.LI, vreg(name), MipsOperand.imm(0)));
            }
        }
    }

    private void emitCallerSave() {
        for (String name : allVregNames)
            res.add(new MipsInstruction(MipsOp.SW, vreg(name), MipsOperand.mem(PReg.SP, frame.spillOffset(name))));
    }

    private void emitCallerRestore() {
        for (String name : allVregNames)
            res.add(new MipsInstruction(MipsOp.LW, vreg(name), MipsOperand.mem(PReg.SP, frame.spillOffset(name))));
    }

    private void translate(IRInstruction irInstruction) {
        switch (irInstruction.opCode) {
            case ASSIGN: translateAssign(irInstruction); break;
            case ADD, SUB, MULT, DIV, AND, OR: translateBinaryOp(irInstruction); break;
            case GOTO: translateGoto(irInstruction); break;
            case BREQ, BRNEQ, BRLT, BRGT, BRGEQ: translateBranch(irInstruction); break;
            case RETURN: translateReturn(irInstruction); break;
            case CALL, CALLR: translateCall(irInstruction); break;
            case ARRAY_LOAD: translateArrayLoad(irInstruction); break;
            case ARRAY_STORE: translateArrayStore(irInstruction); break;
            case LABEL: translateLabel(irInstruction); break;
        }
    }
    private void translateBinaryOp(IRInstruction irInstruction) {
        MipsOp op; 
        switch (irInstruction.opCode) {
            case ADD: op = MipsOp.ADD; break;
            case SUB: op = MipsOp.SUB; break;
            case MULT: op = MipsOp.MULT; break;
            case DIV: op = MipsOp.DIV; break;
            case AND: op = MipsOp.AND; break;
            case OR: op = MipsOp.OR; break;
            default: throw new IllegalArgumentException("Unsupported binary operation");
        }
        // op, dest, src1, src2
        MipsOperand dest = vreg(irInstruction.operands[0].toString());
        MipsOperand src1 = addVreg(irInstruction.operands[1]);
        MipsOperand src2 = addVreg(irInstruction.operands[2]);
        res.add(new MipsInstruction(op, dest, src1, src2));
    }
    private void translateAssign(IRInstruction irInstruction) {
        if (irInstruction.operands.length == 2) {
            // op, dest, src
            MipsOperand dest = vreg(irInstruction.operands[0].toString());
            IROperand srcOperand = irInstruction.operands[1];
            if (srcOperand instanceof IRVariableOperand) {
                MipsOperand src = vreg(srcOperand.toString());
                res.add(new MipsInstruction(MipsOp.MOVE, dest, src));
            } else {
                // immediate value
                long immValue = Long.parseLong(srcOperand.toString());
                MipsOperand src = imm(immValue);
                res.add(new MipsInstruction(MipsOp.LI, dest, src));
            }
        } else {
            // arr, N, val
            String arrName = irInstruction.operands[0].toString();
            MipsOperand arr = vreg(arrName);
            long n = Long.parseLong(irInstruction.operands[1].toString());
            MipsOperand val = addVreg(irInstruction.operands[2]);
            if (!paramNames.contains(arrName)) {
                res.add(new MipsInstruction(MipsOp.ADDI, arr, MipsOperand.preg(PReg.SP), MipsOperand.imm(8 + frame.allocArray(arrName, (int) n))));
            }
            for (int i = 0; i < n; ++i) {
                res.add(new MipsInstruction(MipsOp.SW, val, MipsOperand.mem(arr.value, i * 4)));
            }

        }
    }
    private void translateGoto(IRInstruction iRInstruction) {
        res.add(new MipsInstruction(MipsOp.J, label(qualifyLabel(iRInstruction.operands[0].toString()))));
    }
    private void translateBranch(IRInstruction irInstruction) {
        // branch label, x, y
        MipsOperand lb = label(qualifyLabel(irInstruction.operands[0].toString()));
        MipsOperand src1 = addVreg(irInstruction.operands[1]);
        MipsOperand src2 = addVreg(irInstruction.operands[2]);
        switch (irInstruction.opCode) {
            case BREQ -> res.add(new MipsInstruction(MipsOp.BEQ, src1, src2, lb));
            case BRNEQ -> res.add(new MipsInstruction(MipsOp.BNE, src1, src2, lb));
            case BRLT -> res.add(new MipsInstruction(MipsOp.BLT, src1, src2, lb));
            case BRGT -> res.add(new MipsInstruction(MipsOp.BGT, src1, src2, lb));
            case BRGEQ -> res.add(new MipsInstruction(MipsOp.BGE, src1, src2, lb));
        }
    }
    private void translateReturn(IRInstruction irInstruction) {
        if (irInstruction.operands.length > 0) {
            MipsOperand retVal = addVreg(irInstruction.operands[0]);
            res.add(new MipsInstruction(MipsOp.MOVE, MipsOperand.preg(PReg.V0), retVal)); // move return value to $v0
        }
        res.add(new MipsInstruction(MipsOp.J, label(func.name + "_func_end")));
    }
    private void translateCall(IRInstruction irInstruction) {
        // handles calling functions
        boolean returns = irInstruction.opCode == IRInstruction.OpCode.CALLR;   
        String funcName = irInstruction.operands[returns ? 1 : 0].toString();
        int st = returns ? 2 : 1; 
        if (isSystemCall(funcName)) {
            // handle system calls separately
            translateSysCall(funcName, irInstruction.operands, returns);
        } else {
            // regular function call
            int nargs = irInstruction.operands.length - st;
            int stackArgs = Math.max(0, nargs - 4);
            int stackBytes = stackArgs * 4;

            if (stackBytes > 0) {
                res.add(new MipsInstruction(MipsOp.ADDI, MipsOperand.preg(PReg.SP),
                    MipsOperand.preg(PReg.SP), MipsOperand.imm(-stackBytes)));
            }

            for (int i = 0; i < nargs; ++i) {
                MipsOperand arg = addVreg(irInstruction.operands[st + i]);
                if (i < 4) {
                    res.add(new MipsInstruction(MipsOp.MOVE, MipsOperand.preg(PReg.A[i]), arg));
                } else {
                    res.add(new MipsInstruction(MipsOp.SW, arg, MipsOperand.mem(PReg.SP, (i - 4) * 4)));
                }
            }

            emitCallerSave();
            res.add(new MipsInstruction(MipsOp.JAL, label(funcName)));
            emitCallerRestore();

            // restore stack
            if (stackBytes > 0) {
                res.add(new MipsInstruction(MipsOp.ADDI, MipsOperand.preg(PReg.SP),
                    MipsOperand.preg(PReg.SP), MipsOperand.imm(stackBytes)));
            }

            if (returns) {
                MipsOperand retVal = vreg(irInstruction.operands[0].toString());
                res.add(new MipsInstruction(MipsOp.MOVE, retVal, MipsOperand.preg(PReg.V0)));
            }
        }
    }
    private void translateArrayLoad(IRInstruction irInstruction) {
        // dest, arr, idx
        MipsOperand dest = vreg(irInstruction.operands[0].toString());
        MipsOperand arr = vreg(irInstruction.operands[1].toString());
        MipsOperand idx = addVreg(irInstruction.operands[2]);
        MipsOperand offset = vreg("offset" + tmpCounter++);
        MipsOperand addr = vreg("addr" + tmpCounter++);
        res.add(new MipsInstruction(MipsOp.SLL, offset, idx, imm(2)));
        res.add(new MipsInstruction(MipsOp.ADD, addr, arr, offset)); // addr = arr + offset
        res.add(new MipsInstruction(MipsOp.LW, dest, MipsOperand.mem(addr.value, 0))); // load word from addr to dest
    }
    private void translateArrayStore(IRInstruction irInstruction) {
        // src, arr, idx
        MipsOperand src = addVreg(irInstruction.operands[0]);
        MipsOperand arr = vreg(irInstruction.operands[1].toString());
        MipsOperand idx = addVreg(irInstruction.operands[2]);
        MipsOperand offset = vreg("offset" + tmpCounter++);
        MipsOperand addr = vreg("addr" + tmpCounter++);
        res.add(new MipsInstruction(MipsOp.SLL, offset, idx, imm(2)));
        res.add(new MipsInstruction(MipsOp.ADD, addr, arr, offset)); // addr = arr + offset
        res.add(new MipsInstruction(MipsOp.SW, src, MipsOperand.mem(addr.value, 0))); // store word from src to addr    
    }
    private void translateLabel(IRInstruction irInstruction) {
        res.add(MipsInstruction.label(qualifyLabel(irInstruction.operands[0].toString())));
    }

    // debugging 1.1
    private String qualifyLabel(String raw) {
        return func.name + "_" + raw;
    }

    private void translateSysCall(String funcName, IROperand[] operands, boolean returns) {
        int code = -1;
        boolean takesArg = false;
        boolean hasResult = false;
        switch (funcName) {
            case "puti": code = 1;  takesArg = true;  hasResult = false; break; // print int 
            case "putc": code = 11; takesArg = true;  hasResult = false; break; // print char
            case "putf": code = 2;  takesArg = true;  hasResult = false; break; // print float
            case "geti": code = 5;  takesArg = false; hasResult = true;  break; // read int
            case "getc": code = 12; takesArg = false; hasResult = true;  break; // read char
            case "getf": code = 6;  takesArg = false; hasResult = true;  break; // read float
        }
        if (takesArg) {
            res.add(new MipsInstruction(MipsOp.MOVE, MipsOperand.preg(PReg.A[0]), addVreg(operands[returns ? 2 : 1])));
        }
        res.add(new MipsInstruction(MipsOp.LI, MipsOperand.preg(PReg.V0), imm(code)));
        res.add(new MipsInstruction(MipsOp.SYSCALL));
        if (hasResult && returns) {
            // Handle the result of the system call
            res.add(new MipsInstruction(MipsOp.MOVE, vreg(operands[0].toString()), MipsOperand.preg(PReg.V0)));
        }

    }

    // ---- Helper Code ----
    private MipsOperand addVreg(IROperand operand) { // does not differentiate between variable and immediate, always returns a vreg, and adds LI if it's an immediate
        if (operand instanceof IRVariableOperand) {
            return vreg(operand.toString());
        } else {
            // immediate value
            MipsOperand immOp = vreg("imm" + tmpCounter++);
            res.add(new MipsInstruction(MipsOp.LI, immOp, imm(Long.parseLong(operand.toString()))));
            return immOp;
        }
    }
    private boolean isSystemCall(String funcName) {
        return funcName.equals("puti") || funcName.equals("putc") || funcName.equals("putf")
            || funcName.equals("geti") || funcName.equals("getc") || funcName.equals("getf");
    }
}
