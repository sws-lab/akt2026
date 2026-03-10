package toylangs.vhile.ast;

public record VhileVar(String name) implements VhileExpr {
    @Override
    public String toString() {
        return "var(" +
                "\"" + name + "\"" +
                ")";
    }
}
