package toylangs.pullet.ast;

/**
 * PulletBinding seob muutujatele väärtuse, mis kehtib avaldise kehas.
 * Muutuja sidumiseks tuleb anda sellele väärtuseks samuti mingi avaldis.
 * <p>
 * NB! Juba seotud muutuja uuesti sidumine kehtib ainult let-sidumise keha piires.
 */
public record PulletBinding(String name, PulletNode value, PulletNode body) implements PulletNode {
    @Override
    public String getNodeLabel() {
        return "let";
    }

    @Override
    public String toString() {
        return "let(" +
                "\"" + name + "\"" +
                ", " + value +
                ", " + body +
                ")";
    }
}
