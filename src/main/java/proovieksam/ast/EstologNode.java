package proovieksam.ast;

import toylangs.AbstractNode;

import java.util.Arrays;
import java.util.List;

public sealed interface EstologNode extends AbstractNode permits EstologBinOp, EstologDef, EstologKui, EstologLiteraal, EstologMuutuja, EstologProg {

    static EstologMuutuja var(String nimi) {
        return new EstologMuutuja(nimi);
    }

    static EstologLiteraal lit(boolean value) {
        return new EstologLiteraal(value);
    }

    static EstologJa ja(EstologNode left, EstologNode right) {
        return new EstologJa(left, right);
    }

    static EstologVoi voi(EstologNode left, EstologNode right) {
        return new EstologVoi(left, right);
    }

    static EstologVordus vordus(EstologNode left, EstologNode right) {
        return new EstologVordus(left, right);
    }

    static EstologKui kui(EstologNode kui, EstologNode siis, EstologNode muidu) {
        return new EstologKui(kui, siis, muidu);
    }

    static EstologKui kui(EstologNode kui, EstologNode siis) {
        return new EstologKui(kui, siis);
    }

    static EstologDef def(String nimi, EstologNode avaldis) {
        return new EstologDef(nimi, avaldis);
    }

    static EstologProg prog(EstologNode avaldis, List<EstologDef> defs) {
        return new EstologProg(avaldis, defs);
    }

    static EstologProg prog(EstologNode avaldis, EstologDef... defs) {
        return new EstologProg(avaldis, Arrays.asList(defs));
    }
}
