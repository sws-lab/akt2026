package toylangs.modul.ast;

public record ModulMul(ModulExpr left, ModulExpr right) implements ModulExpr {
    @Override
    public String toString() {
        return "mul(" +
                "" + left +
                ", " + right +
                ")";
    }
}
