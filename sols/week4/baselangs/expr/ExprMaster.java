package week4.baselangs.expr;

import week4.baselangs.expr.ast.*;

import java.util.*;

import static week4.baselangs.expr.ast.ExprNode.*;

public class ExprMaster {

    /**
     * Kogub kokku kõik tippudes esinevad arvud (järjestatult vasakult paremale).
     */
    public static List<Integer> valueList(ExprNode node) {
        return switch (node) {
            case ExprNum(int value) -> Collections.singletonList(value);
            case ExprNeg(ExprNode expr) -> valueList(expr);
            case ExprAdd(ExprNode left, ExprNode right) -> {
                List<Integer> result = new ArrayList<>();
                result.addAll(valueList(left));
                result.addAll(valueList(right));
                yield result;
            }
            case ExprDiv(ExprNode numerator, ExprNode denominator) ->  {
                List<Integer> result = new ArrayList<>();
                result.addAll(valueList(numerator));
                result.addAll(valueList(denominator));
                yield result;
            }
        };
    }

    /**
     * Asendab jagamised nende väärtustega.
     */
    public static ExprNode replaceDivs(ExprNode node) {
        return switch (node) {
            case ExprNum num ->
                    num; // Võime lihtsalt sama tipu tagastada, kuna teda ei saa muuta.
            case ExprNeg(ExprNode expr) ->
                    neg(replaceDivs(expr)); // Siin peab koopia tegema, sest miinusmärgi all teeme teisendusi.
            case ExprAdd(ExprNode left, ExprNode right) ->
                    add(replaceDivs(left), replaceDivs(right)); // Jällegi uus tipp teisendatud alampuudega.
            case ExprDiv div ->
                    num(div.eval()); // Siin lõpuks toimub sisuline teisendus!
        };
    }

    /**
     * Kogub kokku kõik tippudes esinevad arvud (järjestatult vasakult paremale) niimoodi, et summa oleks sama nagu avaldisel.
     * Eeldab, et jagamisi ei esine.
     */
    public static List<Integer> signedValueList(ExprNode node) {
        return switch (node) {
            case ExprNum(int value) -> Collections.singletonList(value);
            case ExprNeg(ExprNode expr) ->
                    signedValueList(expr).stream().map(i -> -i).toList(); // Kõikide elementide märke tuleb lihtsalt muuta
            case ExprAdd(ExprNode left, ExprNode right) -> {
                List<Integer> result = new ArrayList<>();
                result.addAll(signedValueList(left));
                result.addAll(signedValueList(right));
                yield result;
            }
            case ExprDiv _ -> throw new UnsupportedOperationException();
        };
    }

    /**
     * Elimineerib miinused.
     */
    public static ExprNode elimNegs(ExprNode node) {
        return elimNegs(node, 1);
    }

    private static ExprNode elimNegs(ExprNode node, int sign) {
        return switch (node) {
            case ExprNum(int value) -> num(sign * value);
            case ExprNeg(ExprNode expr) -> elimNegs(expr, -sign);
            case ExprAdd(ExprNode left, ExprNode right) ->
                    add(elimNegs(left, sign), elimNegs(right, sign));
            case ExprDiv(ExprNode numerator, ExprNode denominator) ->
                    div(elimNegs(numerator, sign), elimNegs(denominator, 1));
        };
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

