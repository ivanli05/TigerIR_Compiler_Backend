package selector;
import java.util.LinkedHashMap;
import java.util.Map;
// keeps track of frame

public class frame {
    private int arrayBytes = 0;
    private int spillBytes = 0;
    public Map<String, Integer> array = new LinkedHashMap<>();
    public Map<String, Integer> spill = new LinkedHashMap<>();
    public int allocArray(String name, int size) {
        return array.computeIfAbsent(name, k -> {
            int offset = arrayBytes;
            arrayBytes += 4 * size;
            return offset;
        });
    }
    public int allocSpill(String name) {
        return spill.computeIfAbsent(name, k -> {
            int offset = spillBytes;
            spillBytes += 4;
            return offset;
        });
    }
    public int totalArrayBytes() { return arrayBytes; }
    public int totalSpillBytes() { return spillBytes; }
    public int totalVarBytes() { return arrayBytes + spillBytes; }
    public int spillOffset(String name) { return 8 + arrayBytes + spill.get(name); }
}