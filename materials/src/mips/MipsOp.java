package mips;
// all the supported Mips instructions to cover all functionalities of TigerIR
//TODO: add more instructions on the fly
public enum MipsOp {
    SW, LI, 
    ADD, ADDI, SUB, SUBI, MULT, DIV, AND, ANDI, OR, ORI,
    MOVE,
    SYSCALL,
    J, JAL,
    SLL,
    LW,
    LABEL,
    JR,
    BEQ, BNE, BLT, BGT, BGE,
}