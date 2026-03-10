package week4.baselangs.rnd.ast;

public sealed interface RndNode permits RndAdd, RndFlip, RndNeg, RndNum {
    static RndAdd add(RndNode left, RndNode right) {
        return new RndAdd(left, right);
    }

    static RndFlip flip(RndNode left, RndNode right) {
        return new RndFlip(left, right);
    }

    static RndNeg neg(RndNode expr) {
        return new RndNeg(expr);
    }

    static RndNum num(int value) {
        return new RndNum(value);
    }
}
