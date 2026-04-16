package mips;
import java.io.PrintStream;
import java.util.List;

public final class MipsPrinter {
    private final PrintStream out;

    public MipsPrinter(PrintStream out) { this.out = out; }

    public void printProgram(List<MipsInstruction> instrs) {
        out.println(".text");
        out.println(".globl main");
        out.println("    j main");
        for (MipsInstruction ins : instrs) printInstruction(ins);
    }

    public void printInstruction(MipsInstruction ins) {
        if (ins.opCode == MipsOp.LABEL) {
            out.println(ins.text + ":");
            return;
        }
        switch (ins.opCode) {
            case MULT -> out.println("    mul " + fmt(ins.operands[0]) + ", " + fmt(ins.operands[1]) + ", " + fmt(ins.operands[2]));
            case DIV  -> out.println("    div " + fmt(ins.operands[0]) + ", " + fmt(ins.operands[1]) + ", " + fmt(ins.operands[2]));
            case SYSCALL -> out.println("    syscall");
            case JR -> out.println("    jr " + fmt(ins.operands[0]));
            case J  -> out.println("    j " + fmt(ins.operands[0]));
            case JAL -> out.println("    jal " + fmt(ins.operands[0]));
            case LI  -> out.println("    li " + fmt(ins.operands[0]) + ", " + fmt(ins.operands[1]));
            case MOVE -> out.println("    move " + fmt(ins.operands[0]) + ", " + fmt(ins.operands[1]));
            case LW -> out.println("    lw " + fmt(ins.operands[0]) + ", " + fmt(ins.operands[1]));
            case SW -> out.println("    sw " + fmt(ins.operands[0]) + ", " + fmt(ins.operands[1]));
            case BEQ, BNE, BLT, BGT, BGE -> out.println("    " + ins.opCode.name().toLowerCase()
                    + " " + fmt(ins.operands[0]) + ", " + fmt(ins.operands[1]) + ", " + fmt(ins.operands[2]));
            case SLL -> out.println("    sll " + fmt(ins.operands[0]) + ", " + fmt(ins.operands[1]) + ", " + fmt(ins.operands[2]));
            case ADDI, SUBI, ANDI, ORI -> out.println("    " + ins.opCode.name().toLowerCase()
                    + " " + fmt(ins.operands[0]) + ", " + fmt(ins.operands[1]) + ", " + fmt(ins.operands[2]));
            case ADD, SUB, AND, OR -> out.println("    " + ins.opCode.name().toLowerCase()
                    + " " + fmt(ins.operands[0]) + ", " + fmt(ins.operands[1]) + ", " + fmt(ins.operands[2]));
            default -> out.println("    # unhandled: " + ins.opCode);
        }
    }

    private static String fmt(MipsOperand op) {
        switch (op.type) {
            case PREG:  return op.value;
            case VREG:  return "$" + op.value; // virtual reg sentinel — allocator replaces
            case IMM:   return Long.toString(op.imm);
            case LABEL: return op.value;
            case MEM:   return op.offset + "(" + baseFmt(op.base) + ")";
            default: throw new IllegalStateException("bad operand " + op.type);
        }
    }

    // base may be a physical reg ($sp, $fp, $a0...) or a virtual reg name from the selector
    private static String baseFmt(String base) {
        if (base != null && base.startsWith("$")) return base;
        return "$" + base;
    }
}
