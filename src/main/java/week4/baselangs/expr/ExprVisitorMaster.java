package week4.baselangs.expr;

import week4.baselangs.expr.ast.*;

import java.util.*;

import static week4.baselangs.expr.ast.ExprNode.*;

public class ExprVisitorMaster {

    /**
     * Kogub kokku kõik tippudes esinevad arvud (järjestatult vasakult paremale).
     */
    public static List<Integer> valueList(ExprNode node) {
        return null;
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
        return null;
    }
}

