package toylangs.bolog;

import cma.CMaProgram;
import cma.CMaProgramWriter;
import toylangs.bolog.ast.*;

import java.util.Arrays;
import java.util.List;

public class BologCompiler {

    private static final List<String> VARS = Arrays.asList("P", "Q", "R", "S", "T", "U", "V"); // kasuta VARS.indexOf

    private final CMaProgramWriter pw = new CMaProgramWriter();

    public static CMaProgram compile(BologNode node) {
        BologCompiler bologCompiler = new BologCompiler();
        bologCompiler.compileNode(node);
        return bologCompiler.pw.toProgram();
    }

    private void compileNode(BologNode node) {
        throw new UnsupportedOperationException();
    }
}
