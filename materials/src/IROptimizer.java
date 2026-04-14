import ir.*;
import ir.datatype.IRArrayType;
import ir.datatype.IRIntType;
import ir.datatype.IRType;
import ir.operand.IRConstantOperand;
import ir.operand.IROperand;
import ir.operand.IRVariableOperand;
import ir.IRInstruction.OpCode;

import java.io.PrintStream;
import java.util.*;

public class IROptimizer {
    public static void main(String[] args) throws Exception {
        IRReader irReader = new IRReader();
        IRProgram program = irReader.parseIRFile(args[0]);

        HashSet<OpCode> criticalOpCodes = new HashSet<>(Arrays.asList(
            OpCode.GOTO,
            OpCode.BREQ, OpCode.BRNEQ, OpCode.BRLT, OpCode.BRGT, OpCode.BRGEQ,
            OpCode.RETURN,
            OpCode.CALL, OpCode.CALLR,
            OpCode.LABEL
        ));

        HashSet<OpCode> assignOpCodes = new HashSet<>(Arrays.asList(
            OpCode.ASSIGN,
            OpCode.ADD, OpCode.SUB, OpCode.MULT, OpCode.DIV, OpCode.AND, OpCode.OR,
            OpCode.CALLR,
            OpCode.ARRAY_STORE, OpCode.ARRAY_LOAD
        ));

        HashSet<IRInstruction> marked = new HashSet<>();
        Queue<IRInstruction> workList= new LinkedList<>();

        for (IRFunction function : program.functions) {
            for (IRInstruction instruction : function.instructions) {
                if (criticalOpCodes.contains(instruction.opCode)) {
                    marked.add(instruction);
                    workList.add(instruction);
                }
            }
        }

        while (!workList.isEmpty()) {
            IRInstruction operation = workList.poll();
            HashSet<String> vars = new HashSet<>();
            for (IROperand operand : operation.operands) {
                if (operand instanceof IRVariableOperand) {
                    IRVariableOperand variableOperand = (IRVariableOperand) operand;
                    vars.add(variableOperand.getName());
                }
            }
            for (IRFunction function : program.functions) {
                for (IRInstruction instruction : function.instructions) {
                    if (!marked.contains(instruction) && assignOpCodes.contains(instruction.opCode)
                        && vars.contains(((IRVariableOperand)instruction.operands[0]).getName())) {
                        marked.add(instruction);
                        workList.add(instruction);
                    }
                }
            }
        }

        // sweep
        for (IRFunction function : program.functions) {
            Iterator<IRInstruction> iterator = function.instructions.iterator();
            while (iterator.hasNext()) {
                IRInstruction instruction = iterator.next();
                if (!marked.contains(instruction)) iterator.remove();
            }
        }

        IRPrinter filePrinter = new IRPrinter(new PrintStream(args[1]));
        filePrinter.printProgram(program);
    }
}
