package toylangs.safdi;

import cma.CMaProgram;
import cma.CMaProgramWriter;
import toylangs.safdi.ast.*;

import java.util.List;

public class SafdiCompiler {
    private final CMaProgramWriter pw = new CMaProgramWriter();
    private final List<String> variables;

    public SafdiCompiler(List<String> variables) {
        this.variables = variables;
    }

    public static CMaProgram compile(SafdiNode node, List<String> variables) {
        SafdiCompiler safdiCompiler = new SafdiCompiler(variables);
        safdiCompiler.compile(node);
        return safdiCompiler.pw.toProgram();
    }

    private void compile(SafdiNode node) {
        throw new UnsupportedOperationException();
    }
}
