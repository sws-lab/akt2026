package toylangs.imp.ast;

public record ImpDiv(ImpNode numerator, ImpNode denominator) implements ImpNode {
    @Override
    public String toString() {
        return "div(" +
                "" + numerator +
                ", " + denominator +
                ")";
    }
}
