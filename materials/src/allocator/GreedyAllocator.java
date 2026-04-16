package allocator;

import mips.*;
import selector.frame;
import java.util.*;

public class GreedyAllocator {
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
        List<List<MipsInstruction>> blocks = split(instructions);
        List<MipsInstruction> res = new ArrayList<>();
        for (List<MipsInstruction> block : blocks) {
            allocateBlock(block, f, res);
        }
        return res;
    }
    private static List<List<MipsInstruction>> split(List<MipsInstruction> instructions) {

    }
    private static void allocateBlock(List<MipsInstruction> block, frame f, List<MipsInstruction> res) {

    
}
