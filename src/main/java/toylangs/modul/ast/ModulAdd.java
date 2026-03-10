package toylangs.modul.ast;

public record ModulAdd(ModulExpr left, ModulExpr right) implements ModulExpr {
    @Override
    public String toString() {
        return "add(" +
                "" + left +
                ", " + right +
                ")";
    }
}
