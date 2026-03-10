package toylangs.imp.ast;

public record ImpNum(int value) implements ImpNode {
    @Override
    public String toString() {
        return "num(" +
                "" + value +
                ")";
    }
}
