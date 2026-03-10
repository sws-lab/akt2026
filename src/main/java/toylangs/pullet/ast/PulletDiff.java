package toylangs.pullet.ast;

/**
 * Lahutamine.
 */
public record PulletDiff(PulletNode left, PulletNode right) implements PulletNode {
    @Override
    public String toString() {
        return "diff(" +
                "" + left +
                ", " + right +
                ")";
    }
}
