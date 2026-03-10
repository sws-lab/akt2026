package toylangs.pullet.ast;

/**
 * Vaba muutuja, mis saab võtta täisarvulisi väärtuseid.
 */
public record PulletVar(String name) implements PulletNode {
    @Override
    public String toString() {
        return "var(" +
                "\"" + name + "\"" +
                ")";
    }
}
