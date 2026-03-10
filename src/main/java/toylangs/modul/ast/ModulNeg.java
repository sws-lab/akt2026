package toylangs.modul.ast;

public record ModulNeg(ModulExpr expr) implements ModulExpr {
    @Override
    public String toString() {
        return "neg(" +
                "" + expr +
                ")";
    }
}
