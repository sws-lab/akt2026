package proovieksam.ast;

public record EstologVoi(EstologNode left, EstologNode right) implements EstologBinOp {
    @Override
    public String toString() {
        return "voi(" +
                "" + left +
                ", " + right +
                ")";
    }
}
