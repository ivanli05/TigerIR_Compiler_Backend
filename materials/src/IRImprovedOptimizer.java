import ir.*;
import ir.IRInstruction.OpCode;
import ir.operand.*;
import java.io.PrintStream;
import java.util.*;

public class IRImprovedOptimizer {

    static String getVar(IRInstruction instruction) {
        switch (instruction.opCode) {
            case ASSIGN:
            case ADD: case SUB: case MULT: case DIV: case AND: case OR:
            case CALLR:
            case ARRAY_LOAD:
                return ((IRVariableOperand) instruction.operands[0]).getName();
            default:
                return null;
        }
    }

    static Set<String> getUsedVars(IRInstruction instruction) {
        Set<String> used = new HashSet<>();
        IROperand[] ops = instruction.operands;
        switch (instruction.opCode) {
            case ASSIGN:   
                if (ops[1] instanceof IRVariableOperand)
                    used.add(((IRVariableOperand) ops[1]).getName());
                break;
            case ADD: case SUB: case MULT: case DIV: case AND: case OR:  // op dest, s1, s2
                if (ops[1] instanceof IRVariableOperand)
                    used.add(((IRVariableOperand) ops[1]).getName());
                if (ops[2] instanceof IRVariableOperand)
                    used.add(((IRVariableOperand) ops[2]).getName());
                break;
            case CALLR:
                for (int i = 2; i < ops.length; i++)
                    if (ops[i] instanceof IRVariableOperand)
                        used.add(((IRVariableOperand) ops[i]).getName());
                break;
            case CALL:     
                for (int i = 1; i < ops.length; i++)
                    if (ops[i] instanceof IRVariableOperand)
                        used.add(((IRVariableOperand) ops[i]).getName());
                break;
            case RETURN:   
                if (ops.length > 0 && ops[0] instanceof IRVariableOperand)
                    used.add(((IRVariableOperand) ops[0]).getName());
                break;
            case BREQ: case BRNEQ: case BRLT: case BRGT: case BRGEQ:  
                if (ops[1] instanceof IRVariableOperand)
                    used.add(((IRVariableOperand) ops[1]).getName());
                if (ops[2] instanceof IRVariableOperand)
                    used.add(((IRVariableOperand) ops[2]).getName());
                break;
            case ARRAY_STORE:  
                for (IROperand op : ops)
                    if (op instanceof IRVariableOperand)
                        used.add(((IRVariableOperand) op).getName());
                break;
            case ARRAY_LOAD:   
                if (ops[1] instanceof IRVariableOperand)
                    used.add(((IRVariableOperand) ops[1]).getName());
                if (ops[2] instanceof IRVariableOperand)
                    used.add(((IRVariableOperand) ops[2]).getName());
                break;
            default:
                break;
        }
        return used;
    }
    public static void main(String[] args) throws Exception {
        IRReader irReader = new IRReader();
        IRProgram program = irReader.parseIRFile(args[0]);

        HashSet<OpCode> branchOpCodes = new HashSet<>(Arrays.asList(
            OpCode.GOTO, OpCode.BREQ, OpCode.BRNEQ, OpCode.BRLT, OpCode.BRGT, OpCode.BRGEQ
        ));

        HashSet<OpCode> criticalOpCodes = new HashSet<>(Arrays.asList(
            OpCode.GOTO,
            OpCode.BREQ, OpCode.BRNEQ, OpCode.BRLT, OpCode.BRGT, OpCode.BRGEQ,
            OpCode.RETURN,
            OpCode.CALL, OpCode.CALLR,
            OpCode.LABEL,
            OpCode.ARRAY_STORE
        ));

        HashSet<OpCode> assignOpCodes = new HashSet<>(Arrays.asList(
            OpCode.ASSIGN,
            OpCode.ADD, OpCode.SUB, OpCode.MULT, OpCode.DIV, OpCode.AND, OpCode.OR,
            OpCode.CALLR,
            OpCode.ARRAY_STORE, OpCode.ARRAY_LOAD
        ));

        // Step 1: identify critical instructions and build cfg
        for (IRFunction function : program.functions) {
            HashSet<IRInstruction> leaders = new HashSet<>();
            Boolean afterBranch = true;
            Set<String> targetLabels = new HashSet<>();
            for (IRInstruction instruction : function.instructions) {
                if (afterBranch) {
                    leaders.add(instruction);
                    afterBranch = false;
                }
                if (branchOpCodes.contains(instruction.opCode)) {
                    afterBranch = true;
                    if (instruction.operands[0] instanceof IRLabelOperand) {
                        targetLabels.add(((IRLabelOperand) instruction.operands[0]).getName());
                    }
                }
            }
            // add targetLabels to leadeers, map name to instruction
            Map<String, IRInstruction> targets = new HashMap<>(); // name -> instruction
            for (IRInstruction  instruction : function.instructions) {
                if (instruction.opCode == OpCode.LABEL
                    && targetLabels.contains(((IRLabelOperand)instruction.operands[0]).getName())) {
                    leaders.add(instruction);
                    targets.put(((IRLabelOperand)instruction.operands[0]).getName(), instruction);
                }
            }
            // create basic blocks
            Map<Integer, IRBasicBlock> basicBlocks = new HashMap<>();
            for (IRInstruction leader : leaders) {
                IRBasicBlock block = new IRBasicBlock(
                    leader, 
                    new ArrayList<>(), 
                    new ArrayList<>(),
                    new HashSet<>(),
                    new HashSet<>(),
                    new HashSet<>(),
                    new HashSet<>()
                );
                basicBlocks.put(leader.irLineNumber, block);
            }

            IRBasicBlock currBlock = null;
            Boolean lastGOTO = false;
            for (IRInstruction instruction : function.instructions) {
                if (leaders.contains(instruction)) {
                    if (currBlock != null && !lastGOTO) {
                        currBlock.descendents.add(basicBlocks.get(instruction.irLineNumber));
                    }
                    currBlock = basicBlocks.get(instruction.irLineNumber);
                    lastGOTO = false;
                }
                if (branchOpCodes.contains(instruction.opCode)) {
                    if (instruction.operands[0] instanceof IRLabelOperand) {
                        int targetLine = targets.get(((IRLabelOperand)instruction.operands[0]).getName()).irLineNumber;
                        currBlock.descendents.add(basicBlocks.get(targetLine));
                        lastGOTO = (instruction.opCode == OpCode.GOTO);
                    } 
                }
                currBlock.instructions.add(instruction);
            }
            
            // predecessors map for each block preparing for reachability analysis
            Map<IRBasicBlock, List<IRBasicBlock>> predecessors = new HashMap<>();
            for (IRBasicBlock b : basicBlocks.values())
                predecessors.put(b, new ArrayList<>());
            for (IRBasicBlock b : basicBlocks.values())
                for (IRBasicBlock succ : b.descendents)
                    predecessors.get(succ).add(b);

            // Step 2: reachability analysis
            Map<String, Set<IRInstruction>> defs = new HashMap<>();
            for (IRBasicBlock block : basicBlocks.values()) {
                for (IRInstruction instruction : block.instructions) {
                    String def = getVar(instruction);
                    if (def != null) {
                        defs.computeIfAbsent(def, k -> new HashSet<>()).add(instruction);
                    }
                }
            }

            for (IRBasicBlock block : basicBlocks.values()) {
                // reverse to compute gen set
                Set<String> seenInBlock = new HashSet<>();
                for (int i = block.instructions.size() - 1; i >= 0; i--) {
                    IRInstruction instruction = block.instructions.get(i);
                    String def = getVar(instruction);
                    if (def != null && seenInBlock.add(def)) {
                        block.genSet.add(instruction);
                    }
                }
                // kill set 
                for (String var : seenInBlock) {
                    for (IRInstruction def : defs.getOrDefault(var, Collections.emptySet())) {
                        if (!block.instructions.contains(def)) {
                            block.killSet.add(def);
                        }
                    }
                }
            }

            // Step 3: iterative calculation of IN and OUT sets
            boolean changed = true;
            while (changed) {
                changed = false;
                for (IRBasicBlock block : basicBlocks.values()) {
                    Set<IRInstruction> newIn = new HashSet<>();
                    for (IRBasicBlock pred : predecessors.get(block)) {
                        newIn.addAll(pred.outSet);
                    }
                    Set<IRInstruction> newOut = new HashSet<>(newIn);
                    newOut.removeAll(block.killSet);
                    newOut.addAll(block.genSet);
                    if (!newIn.equals(block.inSet) || !newOut.equals(block.outSet)) {
                        block.inSet = newIn;
                        block.outSet = newOut;
                        changed = true;
                    }
                }
            }


            // Step 4: Calculate reachability from in set
            Map<IRInstruction, Set<IRInstruction>> reachability = new HashMap<>();
            for (IRBasicBlock block : basicBlocks.values()) {
                Set<IRInstruction> current = new HashSet<>(block.inSet);
                for (IRInstruction instruction : block.instructions) {
                    reachability.put(instruction, new HashSet<>(current));
                    String def = getVar(instruction);
                    if (def != null) {
                        current.removeIf(d -> def.equals(getVar(d)));
                        current.add(instruction);
                    }
                }
            }

            //Step 5: worklist algorithm
            Set<IRInstruction> marked = new HashSet<>();
            Queue<IRInstruction> worklist = new LinkedList<>();
            
            // initialize  
            for (IRInstruction instruction : function.instructions) {
                if (criticalOpCodes.contains(instruction.opCode)) {
                    worklist.add(instruction);
                    marked.add(instruction);
                }
            }

            while(!worklist.isEmpty()) {
                IRInstruction instruction = worklist.poll();
                Set<IRInstruction> reachable = reachability.getOrDefault(instruction, Collections.emptySet());
                if (reachable.isEmpty()) continue;
                for (String var : getUsedVars(instruction)) {
                    for (IRInstruction reach : reachable) {
                        if (var.equals(getVar(reach)) && marked.add(reach)) {
                            worklist.add(reach);
                        }
                    }
                }
            }


            // Step 6: clean up
            function.instructions.removeIf(x -> !marked.contains(x));
        }
        IRPrinter filePrinter = new IRPrinter(new PrintStream(args[1]));
        filePrinter.printProgram(program);
    }

}
