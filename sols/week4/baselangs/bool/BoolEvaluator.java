package week4.baselangs.bool;

import week4.baselangs.bool.ast.*;

import java.util.Set;

public class BoolEvaluator {

    // Väärtustada tõeväärtusavaldis, kui ette antud on tõeste muutujate hulk.
    public static boolean eval(BoolNode node, Set<Character> tv) {
        return switch (node) {
            case BoolVar(char name) -> tv.contains(name);
            case BoolOr(BoolNode left, BoolNode right) -> eval(left, tv) || eval(right, tv);
            case BoolImp(BoolNode antedecent, BoolNode consequent) -> !eval(antedecent, tv) || eval(consequent, tv);
            case BoolNot(BoolNode exp) -> !eval(exp, tv);
        };
    }
}
