package toylangs.pullet.ast;

/**
 * Avaldis, mis seisneb mingi teise avaldise mingite väärtuste omavahelises liitmises.
 * PulletSum seob enda kehas ÜHE muutuja, mille väärtus on igal korral erinev.
 * Tsüklimuutuja algväärtuseks on avaldise lo väärtus, ning seda hakatakse suurendama
 * ühe kaupa, kuni tema väärtus on väiksem kui avaldise hi väärtus.
 * <p>
 * NB! Avaldise hi väärtus on välja arvatud ega saa kunagi tsüklimuutuja väärtuseks.
 */
public record PulletSum(String name, PulletNode lo, PulletNode hi, PulletNode body) implements PulletNode {
    @Override
    public String toString() {
        return "sum(" +
                "\"" + name + "\"" +
                ", " + lo +
                ", " + hi +
                ", " + body +
                ")";
    }
}
