package toylangs.imp.ast;

import toylangs.AbstractNode;

import java.util.Arrays;
import java.util.List;

public sealed interface ImpNode extends AbstractNode permits ImpAdd, ImpAssign, ImpDiv, ImpNeg, ImpNum, ImpProg, ImpVar {
    // Võimaldavad natuke mugavamalt luua neid objekte:
    static ImpNum num(int value) {
        return new ImpNum(value);
    }

    static ImpNeg neg(ImpNode expr) {
        return new ImpNeg(expr);
    }

    static ImpAdd add(ImpNode left, ImpNode right) {
        return new ImpAdd(left, right);
    }

    static ImpDiv div(ImpNode numerator, ImpNode denominator) {
        return new ImpDiv(numerator, denominator);
    }

    static ImpVar var(char name) {
        return new ImpVar(name);
    }

    static ImpAssign assign(char name, ImpNode expr) {
        return new ImpAssign(name, expr);
    }

    static ImpProg prog(ImpNode expr, List<ImpAssign> assigns) {
        return new ImpProg(expr, assigns);
    }

    static ImpProg prog(ImpNode expr, ImpAssign... assigns) {
        return new ImpProg(expr, Arrays.asList(assigns));
    }
}
