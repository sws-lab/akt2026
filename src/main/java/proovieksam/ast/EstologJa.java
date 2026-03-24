package proovieksam.ast;

public record EstologJa(EstologNode left, EstologNode right) implements EstologBinOp {
    @Override
    public String toString() {
        return "ja(" +
                "" + left +
                ", " + right +
                ")";
    }
}
