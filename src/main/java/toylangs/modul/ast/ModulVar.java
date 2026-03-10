package toylangs.modul.ast;

public record ModulVar(String name) implements ModulExpr {
    @Override
    public String toString() {
        return "var(" +
                "\"" + name + "\"" +
                ")";
    }
}
