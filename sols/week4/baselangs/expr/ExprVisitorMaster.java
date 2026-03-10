package week4.baselangs.expr;

import week4.baselangs.expr.ast.*;

import java.util.*;

import static week4.baselangs.expr.ast.ExprNode.*;

public class ExprVisitorMaster {

    /**
     * Kogub kokku kõik tippudes esinevad arvud (järjestatult vasakult paremale).
     */
    public static List<Integer> valueList(ExprNode node) {
        List<Integer> result = new ArrayList<>();

        ExprVisitor<Void> visitor = new ExprVisitor.BaseVisitor<>() {
            @Override
            protected Void visit(ExprNum num) {
                result.add(num.value());
                return null; // Geneerikus kasutatav Void (mitte void!) tüüp nõuab tagastust.
            }
        };

        node.accept(visitor);
        return result;
    }

    /**
     * Asendab jagamised nende väärtustega.
     */
    public static ExprNode replaceDivs(ExprNode node) {
        return new ExprVisitor<ExprNode>() {
            @Override
            public ExprNode visit(ExprNum num) {
                // Võime lihtsalt sama tipu tagastada, kuna teda ei saa muuta.
                return num;
            }

            @Override
            protected ExprNode visit(ExprNeg neg) {
                // Siin peab koopia tegema, sest miinusmärgi all teeme teisendusi.
                return neg(visit(neg.expr()));
            }

            @Override
            public ExprNode visit(ExprAdd add) {
                // Jällegi uus tipp teisendatud alampuudega.
                return add(visit(add.left()), visit(add.right()));
            }

            @Override
            public ExprNode visit(ExprDiv div) {
                // Siin lõpuks toimub sisuline teisendus!
                return num(div.eval());
            }
        }.visit(node);
    }

    /**
     * Kogub kokku kõik tippudes esinevad arvud (järjestatult vasakult paremale) niimoodi, et summa oleks sama nagu avaldisel.
     * Eeldab, et jagamisi ei esine.
     */
    public static List<Integer> signedValueList(ExprNode node) {
        return new ExprVisitor<List<Integer>>() {
            @Override
            public List<Integer> visit(ExprNum num) {
                return Collections.singletonList(num.value());
            }

            @Override
            public List<Integer> visit(ExprNeg neg) {
                // Kõikide elementide märke tuleb lihtsalt muuta:
                return visit(neg.expr()).stream().map(i -> -i).toList();
            }

            @Override
            public List<Integer> visit(ExprAdd add) {
                List<Integer> result = new ArrayList<>();
                result.addAll(visit(add.left()));
                result.addAll(visit(add.right()));
                return result;
            }

            @Override
            public List<Integer> visit(ExprDiv div) {
                throw new UnsupportedOperationException();
            }

        }.visit(node);
    }

    /**
     * Elimineerib miinused.
     */
    public static ExprNode elimNegs(ExprNode node) {
        return elimNegs(node, 1);
    }

    private static ExprNode elimNegs(ExprNode node, int sign) {
        return new ExprVisitor<ExprNode>() {
            @Override
            public ExprNode visit(ExprNum num) {
                return num(sign * num.value());
            }

            @Override
            public ExprNode visit(ExprNeg neg) {
                return elimNegs(neg.expr(), -sign);
            }

            @Override
            public ExprNode visit(ExprAdd add) {
                return add(visit(add.left()), visit(add.right()));
            }

            @Override
            public ExprNode visit(ExprDiv div) {
                return div(visit(div.numerator()), elimNegs(div.denominator(), 1));
            }
        }.visit(node);
    }

    /**
     * Trükib avaldise "ilusalt", st. võimalikult väheste vajalike sulgudega.
     */
    public static String pretty(ExprNode node) {
        return new ExprVisitor<String>() {
            @Override
            public String visit(ExprNum num) {
                return Integer.toString(num.value());
            }

            @Override
            public String visit(ExprNeg neg) {
                return "-" + visit(neg.expr(), priorityOf(neg));
            }

            @Override
            public String visit(ExprAdd add) {
                return visit(add.left(), priorityOf(add)) + "+" + visit(add.right(), priorityOf(add));
            }

            @Override
            public String visit(ExprDiv div) {
                return visit(div.numerator(), priorityOf(div)) + "/" +
                        visit(div.denominator(), priorityOf(div) + 1); // vasakassotsiatiivsus
            }

            /**
             * Otsustab konteksti põhjal, kas sulge on vaja või mitte.
             */
            private String visit(ExprNode node, int contextPriority) {
                if (priorityOf(node) < contextPriority)
                    return '(' + visit(node) + ')';
                else
                    return visit(node);
            }

            private static int priorityOf(ExprNode node) {
                return ExprMaster.priorityOf(node);
            }

        }.visit(node);
    }
}

