package toylangs.pullet.ast;

/**
 * Täisarv.
 */
public record PulletNum(int num) implements PulletNode {
    @Override
    public String toString() {
        return "num(" +
                "" + num +
                ")";
    }
}
