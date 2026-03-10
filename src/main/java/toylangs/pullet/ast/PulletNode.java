package toylangs.pullet.ast;

import toylangs.AbstractNode;

public sealed interface PulletNode extends AbstractNode permits PulletBinding, PulletDiff, PulletNum, PulletSum, PulletVar {

    static PulletNum num(int n) {
        return new PulletNum(n);
    }

    static PulletVar var(String name) {
        return new PulletVar(name);
    }

    static PulletDiff diff(PulletNode left, PulletNode right) {
        return new PulletDiff(left, right);
    }

    static PulletBinding let(String name, PulletNode value, PulletNode body) {
        return new PulletBinding(name, value, body);
    }

    static PulletSum sum(String name, PulletNode lo, PulletNode hi, PulletNode body) {
        return new PulletSum(name, lo, hi, body);
    }
}
