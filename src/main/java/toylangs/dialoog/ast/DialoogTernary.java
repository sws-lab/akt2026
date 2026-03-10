package toylangs.dialoog.ast;

public record DialoogTernary(DialoogNode guardExpr, DialoogNode trueExpr, DialoogNode falseExpr) implements DialoogNode {
    @Override
    public String getNodeLabel() {
        return "ifte";
    }

    @Override
    public String toString() {
        return "ifte(" +
                "" + guardExpr +
                ", " + trueExpr +
                ", " + falseExpr +
                ")";
    }
}
