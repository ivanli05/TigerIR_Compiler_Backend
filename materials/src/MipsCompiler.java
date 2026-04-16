import allocator.NaiveAllocator;
import ir.*;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import mips.MipsInstruction;
import mips.MipsPrinter;
import selector.InstructionSelector;
import selector.Wrapper;

public class MipsCompiler {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("usage: MipsCompiler <input.ir> <output.s> --naive|--greedy");
            System.exit(1);
        }
        String inPath = args[0];
        String outPath = args[1];
        String mode = args.length >= 3 ? args[2] : "--naive";

        IRProgram program = new IRReader().parseIRFile(inPath);

        InstructionSelector sel = new InstructionSelector();
        List<MipsInstruction> all = new ArrayList<>();
        for (IRFunction fn : program.functions) {
            List<MipsInstruction> body = sel.selectBody(fn);
            if (mode.equals("--naive")) {
                body = NaiveAllocator.allocate(body, sel.frame);
            }
            all.addAll(Wrapper.emit(fn, body, sel.frame));
        }

        try (PrintStream out = new PrintStream(outPath)) {
            new MipsPrinter(out).printProgram(all);
        }
    }
}
