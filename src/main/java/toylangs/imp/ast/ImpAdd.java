package toylangs.imp.ast;

public record ImpAdd(ImpNode left, ImpNode right) implements ImpNode {
    @Override
    public String toString() {
        return "add(" +
                "" + left +
                ", " + right +
                ")";
    }
}
