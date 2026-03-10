package toylangs.vhile.ast;

public record VhileAssign(String name, VhileExpr expr) implements VhileStmt {
    @Override
    public String toString() {
        return "assign(" +
                "\"" + name + "\"" +
                ", " + expr +
                ")";
    }
}
