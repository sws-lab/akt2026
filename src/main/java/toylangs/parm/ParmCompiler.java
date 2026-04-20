package toylangs.parm;

import cma.CMaProgram;
import cma.CMaProgramWriter;
import toylangs.parm.ast.*;

import java.util.Arrays;
import java.util.List;

import static toylangs.parm.ast.ParmNode.*;

public class ParmCompiler {

    public static final List<String> VARS = Arrays.asList("X", "Y", "Z", "A", "B", "C", "D"); // kasuta VARS.indexOf

    private final CMaProgramWriter pw = new CMaProgramWriter();

    public static CMaProgram compile(ParmNode node) {
        ParmCompiler parmCompiler = new ParmCompiler();
        parmCompiler.compileNode(node);
        return parmCompiler.pw.toProgram();
    }

    private void compileNode(ParmNode node) {
        throw new UnsupportedOperationException();
    }

    static void main() {
        CMaProgram cMaProgram = compile(plus(lit(10), lit(12)));
        System.out.println(cMaProgram.toString());
    }
}
