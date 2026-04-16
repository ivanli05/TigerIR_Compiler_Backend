package mips;

// translate IRInstruction to MipsInstruction
public class MipsInstruction {
    public final MipsOp opCode;
    public final MipsOperand[] operands;
    public String text;
    public MipsInstruction(MipsOp opCode, MipsOperand... operands) {
        this.opCode = opCode;
        this.operands = operands;
    }
    
    public static MipsInstruction label(String name) {
        MipsInstruction instr = new MipsInstruction(MipsOp.LABEL);
        instr.text = name;
        return instr;
    }

}