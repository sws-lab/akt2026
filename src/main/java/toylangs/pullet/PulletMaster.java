package toylangs.pullet;

import toylangs.pullet.ast.*;

import static toylangs.pullet.ast.PulletNode.*;

public class PulletMaster {
    public static boolean isLive(String name, PulletNode node) {
        throw new UnsupportedOperationException();
    }

    public static PulletNode optimize(PulletNode node) {
        throw new UnsupportedOperationException();
    }

    static void main() {
        PulletNode avaldis = let("a",num(666),let("b", diff(var("a"),num(1)),var("a")));
        System.out.println(avaldis);
        System.out.println(PulletMaster.optimize(avaldis));
    }
}
