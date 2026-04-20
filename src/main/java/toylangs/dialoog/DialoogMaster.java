package toylangs.dialoog;

import toylangs.dialoog.ast.*;

import java.util.HashMap;
import java.util.Map;

import static toylangs.dialoog.ast.DialoogNode.*;

public class DialoogMaster {
    public enum Type {TInt, TBool}

    private final Map<String, Type> typeEnv = new HashMap<>();

    public static Type typecheck(DialoogProg prog) {
        DialoogMaster dialoogMaster = new DialoogMaster();
        return dialoogMaster.typecheckNode(prog);
    }

    private Type typecheckNode(DialoogNode node) {
        throw new UnsupportedOperationException();
    }

    public static DialoogNode symbex(DialoogNode node) {
        throw new UnsupportedOperationException();
    }

    static void main() {
        DialoogNode expr = ifte(eq(var("x"), il(10)), var("error"), var("good"));
        System.out.println(symbex(expr));
    }
}
