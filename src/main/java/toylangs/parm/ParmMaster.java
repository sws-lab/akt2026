package toylangs.parm;

import cma.CMaProgram;
import cma.CMaProgramWriter;
import toylangs.parm.ast.*;

import static toylangs.parm.ast.ParmNode.lit;
import static toylangs.parm.ast.ParmNode.plus;

public class ParmMaster {
    private final CMaProgramWriter pw = new CMaProgramWriter();

    public static CMaProgram compile(ParmNode node) {
        ParmMaster parmMaster = new ParmMaster();
        parmMaster.compileNode(node);
        return parmMaster.pw.toProgram();
    }

    private void compileNode(ParmNode node) {
        throw new UnsupportedOperationException();
    }

    static void main() {
        CMaProgram cMaProgram = compile(plus(lit(10), lit(12)));
        System.out.println(cMaProgram.toString());
    }
}
