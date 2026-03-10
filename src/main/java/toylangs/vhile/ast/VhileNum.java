package toylangs.vhile.ast;

public record VhileNum(int value) implements VhileExpr {
    @Override
    public String toString() {
        return "num(" +
                "" + value +
                ")";
    }
}
