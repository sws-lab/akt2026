package toylangs.imp;

import toylangs.imp.ast.*;

public class ImpEvaluator {

    public static int eval(ImpProg prog) {
        ImpEvaluator impEvaluator = new ImpEvaluator();
        return impEvaluator.evalNode(prog);
    }

    private int evalNode(ImpNode node) {
        throw new UnsupportedOperationException();
    }
}
