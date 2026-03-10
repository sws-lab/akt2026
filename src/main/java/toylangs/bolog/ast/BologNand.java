package toylangs.bolog.ast;

public record BologNand(BologNode left, BologNode right) implements BologNode {
    @Override
    public String toString() {
        return "nand(" +
                "" + left +
                ", " + right +
                ")";
    }
}
