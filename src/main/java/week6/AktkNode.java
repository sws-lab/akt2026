package week6;

import java.util.Map;

import static week5.AktkToken.Type.*;

public interface AktkNode {

    static AktkNode num(int value) {
        throw new UnsupportedOperationException();
    }

    static AktkNode var(String name) {
        throw new UnsupportedOperationException();
    }

    static AktkNode plus(AktkNode left, AktkNode right) {
        throw new UnsupportedOperationException();
    }

    static AktkNode minus(AktkNode left, AktkNode right) {
        throw new UnsupportedOperationException();
    }

    static AktkNode mul(AktkNode left, AktkNode right) {
        throw new UnsupportedOperationException();
    }

    static AktkNode div(AktkNode left, AktkNode right) {
        throw new UnsupportedOperationException();
    }

    int eval(Map<String, Integer> env);

    default String toPrettyString() {
        return toString();
    }
}
