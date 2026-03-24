package proovieksam;

import proovieksam.ast.*;

import static proovieksam.ast.EstologNode.*;

public class EstologEvaluator {

    public static boolean eval(EstologProg prog) {
        EstologEvaluator estologEvaluator = new EstologEvaluator();
        return estologEvaluator.evalNode(prog);
    }

    private boolean evalNode(EstologNode node) {
        throw new UnsupportedOperationException();
    }

    static void main() {
        EstologProg prog = prog(
                kui(vordus(var("x"), var("y")), var("a"), var("b")),

                def("x", lit(false)),
                def("y", lit(true)),
                def("a", ja(var("x"), var("y"))),
                def("b", voi(var("x"), var("y")))
        );

        System.out.println(prog);
        System.out.println(eval(prog));
    }
}
