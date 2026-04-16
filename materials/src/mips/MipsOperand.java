package mips;

public class MipsOperand {
    public enum Type {
        VREG, IMM, LABEL, PREG, MEM
    }
    public final Type type;
    public final String value;
    public final long imm;
    public final String base;
    public final long offset;
    // initializers for different types of operands
    private MipsOperand(Type type, String name, long imm, String base, long offset) {
        this.type = type;
        this.value = name;
        this.imm = imm;
        this.base = base;
        this.offset = offset;
    }
    public static MipsOperand vreg(String name) {
        return new MipsOperand(Type.VREG, name, 0, null, 0);
    }

    public static MipsOperand imm(long value) {
        return new MipsOperand(Type.IMM, null, value, null, 0);
    }

    public static MipsOperand preg(String name) {
        return new MipsOperand(Type.PREG, name, 0, null, 0);
    }

    public static MipsOperand mem(String base, long offset) { // 5(arr)
        return new MipsOperand(Type.MEM, null, 0, base, offset);
    }
    public static MipsOperand label(String name) {
        return new MipsOperand(Type.LABEL, name, 0, null, 0);
    }
}