package toylangs.modul.ast;

import com.google.common.base.Preconditions;

public record ModulPow(ModulExpr base, int power) implements ModulExpr {
    public ModulPow {
        Preconditions.checkArgument(power >= 0, "power is negative: %s", power);
    }

    @Override
    public String toString() {
        return "pow(" +
                "" + base +
                ", " + power +
                ")";
    }
}
