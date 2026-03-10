package toylangs.bolog.ast;

import java.util.List;

public record BologImp(BologNode conclusion, List<BologNode> assumptions) implements BologNode {

    public BologImp(BologNode conclusion, BologNode... assumptions) {
        this(conclusion, List.of(assumptions));
    }

    @Override
    public String toString() {
        return "imp(" +
                "" + conclusion +
                ", " + assumptions.toString().replaceAll("[\\[\\]{}]", "") +
                ")";
    }
}
