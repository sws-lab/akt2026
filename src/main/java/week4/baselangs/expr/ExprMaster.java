package week4.baselangs.expr;

import week4.baselangs.expr.ast.*;

import java.util.*;

import static week4.baselangs.expr.ast.ExprNode.*;

public class ExprMaster {

    /**
     * Kogub kokku kõik tippudes esinevad arvud (järjestatult vasakult paremale).
     */
    public static List<Integer> valueList(ExprNode node) {
        throw new UnsupportedOperationException();
    }

    /**
     * Asendab jagamised nende väärtustega.
     */
    public static ExprNode replaceDivs(ExprNode node) {
        return null;
    }

    /**
     * Kogub kokku kõik tippudes esinevad arvud (järjestatult vasakult paremale) niimoodi, et summa oleks sama nagu avaldisel.
     * Eeldab, et jagamisi ei esine.
     */
    public static List<Integer> signedValueList(ExprNode node) {
        return null;
    }

    /**
     * Elimineerib miinused.
     */
    public static ExprNode elimNegs(ExprNode node) {
        return null;
    }

    /**
     * Trükib avaldise "ilusalt", st. võimalikult väheste vajalike sulgudega.
     */
    public static String pretty(ExprNode node) {
        int priority = priorityOf(node);
        return switch (node) {
            case ExprNum(int value) -> Integer.toString(value);
            case ExprNeg(ExprNode expr) -> "-" + pretty(expr, priority);
            case ExprAdd(ExprNode left, ExprNode right) ->
                    pretty(left, priority) + "+" + pretty(right, priority);
            case ExprDiv(ExprNode numerator, ExprNode denominator) ->
                    pretty(numerator, priority) + "/" + pretty(denominator, priority + 1); // vasakassotsiatiivsus
        };
    }

    /**
     * Otsustab konteksti põhjal, kas sulge on vaja või mitte.
     */
    private static String pretty(ExprNode node, int contextPriority) {
        if (priorityOf(node) < contextPriority)
            return '(' + pretty(node) + ')';
        else
            return pretty(node);
    }

    /**
     * Tagastab tehte prioriteedi.
     */
    static int priorityOf(ExprNode node) {
        return switch (node) {
            case ExprAdd _ -> 1; // kõige madalama prioriteediga
            case ExprDiv _ -> 2;
            case ExprNeg _ -> 3;
            case ExprNum _ -> 4; // kõige kõrgema prioriteediga
        };
    }
}

