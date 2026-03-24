package proovieksam.ast;

public record EstologKui(EstologNode kui, EstologNode siis, EstologNode muidu) implements EstologNode {
    public EstologKui(EstologNode kui, EstologNode siis) {
        this(kui, siis, null);
    }

    @Override
    public String toString() {
        return "kui(" +
                "" + kui +
                ", " + siis +
                ", " + muidu +
                ")";
    }
}
