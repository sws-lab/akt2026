package toylangs.bolog.ast;

import toylangs.AbstractNode;

import java.util.List;

public sealed interface BologNode extends AbstractNode permits BologImp, BologLit, BologNand, BologVar {
    // Literaalid ja muutuja kasutus
    static BologLit tv(boolean value) { return new BologLit(value); }
    static BologVar var(String name) { return new BologVar(name); }

    // Implikatsioon
    static BologImp imp(BologNode conclusion, List<BologNode> assumptions) {
        return new BologImp(conclusion, assumptions);
    }
    static BologImp imp(BologNode conclusion, BologNode... assumptions) {
        return new BologImp(conclusion, assumptions);
    }

    // Nand
    static BologNand nand(BologNode left, BologNode right) {
        return new BologNand(left, right);
    }
    static BologNode not(BologNode expr) {
        return nand(tv(true), expr);
    }
    static BologNode and(BologNode left, BologNode right) {
        return not(nand(left, right));
    }
    static BologNode or(BologNode left, BologNode right) {
        return nand(not(left), not(right));
    }
    static BologNode xor(BologNode left, BologNode right) {
        return and(or(left,right), nand(left,right));
    }
}
