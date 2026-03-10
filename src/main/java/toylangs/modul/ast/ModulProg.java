package toylangs.modul.ast;

import com.google.common.base.Preconditions;

public record ModulProg(ModulExpr expr, int modulus) implements ModulNode {
    public ModulProg {
        Preconditions.checkArgument(modulus >= 1, "modulus is less than 1: %s", modulus);
    }

    @Override
    public String toString() {
        return "prog(" +
                "" + expr +
                ", " + modulus +
                ")";
    }
}
