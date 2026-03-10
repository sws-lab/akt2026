package week4.baselangs.rnd;

import week4.baselangs.rnd.ast.*;

import java.util.function.BooleanSupplier;

public class RndEvaluator {
    // Väärtusta antud avaldist vasakult paremale, kasutades ettantud münti.
    public static int eval(RndNode node, BooleanSupplier coin) {
        return switch (node) {
            case RndNum(int num) -> num;
            case RndNeg(RndNode neg) -> -eval(neg, coin);
            case RndAdd(RndNode left, RndNode right) -> eval(left, coin) + eval(right, coin);
            case RndFlip(RndNode left, RndNode right) -> eval(coin.getAsBoolean() ? left : right, coin);
        };
    }
}
