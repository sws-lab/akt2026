package toylangs.imp.ast;

import java.util.List;

public record ImpProg(ImpNode expr, List<ImpAssign> assigns) implements ImpNode {
    @Override
    public String toString() {
        return "prog(" +
                "" + expr +
                ", " + assigns.toString().replaceAll("[\\[\\]{}]", "") +
                ")";
    }
}
