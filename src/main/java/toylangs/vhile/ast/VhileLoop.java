package toylangs.vhile.ast;

public record VhileLoop(VhileExpr condition, VhileStmt body) implements VhileStmt {
    @Override
    public String toString() {
        return "loop(" +
                "" + condition +
                ", " + body +
                ")";
    }
}
