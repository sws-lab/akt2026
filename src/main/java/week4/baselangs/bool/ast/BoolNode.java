package week4.baselangs.bool.ast;

public sealed interface BoolNode permits BoolImp, BoolNot, BoolOr, BoolVar {

    // Võimaldavad natuke mugavamalt luua neid objekte:
    static BoolVar var(char name) {
        return new BoolVar(name);
    }

    static BoolImp imp(BoolNode antedecent, BoolNode consequent) {
        return new BoolImp(antedecent, consequent);
    }

    static BoolOr or(BoolNode left, BoolNode right) {
        return new BoolOr(left, right);
    }

    static BoolNot not(BoolNode exp) {
        return new BoolNot(exp);
    }
}
