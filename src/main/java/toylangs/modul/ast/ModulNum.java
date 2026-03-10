package toylangs.modul.ast;

public record ModulNum(int value) implements ModulExpr {
    @Override
    public String toString() {
        return "num(" +
                "" + value +
                ")";
    }
}
