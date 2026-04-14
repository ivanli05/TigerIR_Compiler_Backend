import ir.*;

import java.util.List;
import java.util.Set;

public class IRBasicBlock {
    public IRInstruction leader;
    public List<IRInstruction> instructions;
    public List<IRBasicBlock> descendents;
    public Set<IRInstruction> genSet;
    public Set<IRInstruction> killSet;
    public Set<IRInstruction> inSet;
    public Set<IRInstruction> outSet;

    public IRBasicBlock(IRInstruction leader, List<IRInstruction> instructions, List<IRBasicBlock> descendents,
        Set<IRInstruction> genSet, Set<IRInstruction> killSet, Set<IRInstruction> inSet, Set<IRInstruction> outSet) {
        this.leader = leader;
        this.instructions = instructions;
        this.descendents = descendents;
        this.genSet = genSet;
        this.killSet = killSet;
        this.inSet = inSet;
        this.outSet = outSet;
    }
}
