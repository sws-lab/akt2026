package toylangs.hulk;

import cma.CMaProgram;
import cma.CMaProgramWriter;
import toylangs.hulk.ast.*;

import java.util.*;

import static cma.CMaUtils.bool2int;

public class HulkCompiler {
    private static final List<Character> SET_VARIABLES = Arrays.asList('X', 'A', 'B', 'C', 'D', 'G', 'H', 'V'); // kasuta SET_VARIABLES.indexOf

    // kasuta hulga literaali teisendamiseks arvuks (bitset)
    private static int set2int(Set<Character> set) {
        List<Character> ELEM_VARIABLES = Arrays.asList('x', 'y', 'z', 'a', 'b', 'c', 'u', 'v');
        int result = 0;
        if (set != null) {
            for (int i = 0; i < ELEM_VARIABLES.size(); i++) {
                result |= bool2int(set.contains(ELEM_VARIABLES.get(i))) << i;
            }
        }
        return result;
    }

    private final CMaProgramWriter pw = new CMaProgramWriter();

    public static CMaProgram compile(HulkProg prog) {
        HulkCompiler hulkCompiler = new HulkCompiler();
        hulkCompiler.compileNode(prog);
        return hulkCompiler.pw.toProgram();
    }

    private void compileNode(HulkNode node) {
        throw new UnsupportedOperationException();
    }
}
