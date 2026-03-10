package toylangs.modul.ast;

import toylangs.AbstractNode;

public sealed interface ModulNode extends AbstractNode permits ModulExpr, ModulProg {

    static ModulNum num(int value) {
        return new ModulNum(value);
    }

    static ModulVar var(String name) {
        return new ModulVar(name);
    }

    static ModulNeg neg(ModulExpr expr) {
        return new ModulNeg(expr);
    }

    static ModulAdd add(ModulExpr left, ModulExpr right) {
        return new ModulAdd(left, right);
    }

    static ModulExpr sub(ModulExpr left, ModulExpr right) {
        return add(left, neg(right));
    }

    static ModulMul mul(ModulExpr left, ModulExpr right) {
        return new ModulMul(left, right);
    }

    static ModulPow pow(ModulExpr base, int power) {
        return new ModulPow(base, power);
    }

    static ModulProg prog(ModulExpr expr, int modulus) {
        return new ModulProg(expr, modulus);
    }
}
