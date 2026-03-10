package toylangs.parm.ast;

import toylangs.AbstractNode;

public sealed interface ParmNode extends AbstractNode permits ParmCompose, ParmLit, ParmPlus, ParmUpdate, ParmVar {

    // Literaalid ja muutuja kasutus
    static ParmLit lit(int value) { return new ParmLit(value); }
    static ParmVar var(String name) { return new ParmVar(name); }

    // Liitmine
    static ParmPlus plus(ParmNode left, ParmNode right) {
        return new ParmPlus(left, right);
    }

    // Omistus
    static ParmUpdate up(String variable, ParmNode value) {
        return new ParmUpdate(variable, value);
    }

    // Komponeerimine järjestikune ja paralleelne
    static ParmCompose seq(ParmNode fst, ParmNode snd) {
        return new ParmCompose(fst, snd, false);
    }
    static ParmCompose par(ParmNode fst, ParmNode snd) {
        return new ParmCompose(fst, snd, true);
    }
}
