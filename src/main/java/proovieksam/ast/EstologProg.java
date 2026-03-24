package proovieksam.ast;

import java.util.List;

public record EstologProg(EstologNode avaldis, List<EstologDef> defs) implements EstologNode {
    @Override
    public String toString() {
        return "prog(" +
                "" + avaldis +
                ", " + defs.toString().replaceAll("[\\[\\]{}]", "") +
                ")";
    }
}
