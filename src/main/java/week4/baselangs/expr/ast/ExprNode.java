package week4.baselangs.expr.ast;

// See liides on kinnine (sealed), seega ExprEvaluator-is kompilaator teab, et default juhtu pole vaja.
// See on võimalik alates Java 17-st.
public sealed interface ExprNode permits ExprNum, ExprNeg, ExprAdd, ExprDiv {

    // Abimeetodid, mis võimaldavad objekte mugavamalt luua:

    static ExprNum num(int value) {
        return new ExprNum(value);
    }

    static ExprNeg neg(ExprNode expr) {
        return new ExprNeg(expr);
    }

    static ExprAdd add(ExprNode left, ExprNode right) {
        return new ExprAdd(left, right);
    }

    static ExprDiv div(ExprNode numerator, ExprNode denominator) {
        return new ExprDiv(numerator, denominator);
    }

    // Visitori mustri implementeerimiseks.
    <T> T accept(ExprVisitor<T> visitor);

    /**
     * Väärtustab avaldise.
     */
    int eval();


    static void main() {
        ExprNode expr = div(add(num(5), add(num(3), neg(num(2)))), num(2));
        System.out.println(expr.eval()); // tulemus: 3
    }
}
