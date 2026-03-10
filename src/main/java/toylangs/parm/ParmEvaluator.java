package toylangs.parm;

import toylangs.parm.ast.*;

public class ParmEvaluator {

    public static int eval(ParmNode node) {
        ParmEvaluator parmEvaluator = new ParmEvaluator();
        return parmEvaluator.evalNode(node);
    }

    private int evalNode(ParmNode node) {
        throw new UnsupportedOperationException();
    }
}
