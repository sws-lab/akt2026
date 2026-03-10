package toylangs.imp.ast;

public record ImpNeg(ImpNode expr) implements ImpNode {
    @Override
    public String toString() {
        return "neg(" +
                "" + expr +
                ")";
    }
}
