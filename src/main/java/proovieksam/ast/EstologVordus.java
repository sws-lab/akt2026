package proovieksam.ast;

public record EstologVordus(EstologNode left, EstologNode right) implements EstologBinOp {
    @Override
    public String toString() {
        return "vordus(" +
                "" + left +
                ", " + right +
                ")";
    }
}
