package toylangs.dialoog;

import cma.CMaProgram;
import cma.CMaProgramWriter;
import toylangs.dialoog.ast.*;

public class DialoogCompiler {
    private final CMaProgramWriter pw = new CMaProgramWriter();

    public static CMaProgram compile(DialoogProg prog) {
        DialoogCompiler dialoogCompiler = new DialoogCompiler();
        dialoogCompiler.compileNode(prog);
        return dialoogCompiler.pw.toProgram();
    }

    private void compileNode(DialoogNode node) {
        throw new UnsupportedOperationException();
    }
}
