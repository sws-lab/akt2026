package toylangs.dialoog.ast;

import java.util.List;


public record DialoogProg(List<DialoogDecl> decls, DialoogNode expression) implements DialoogNode {

    @Override
    public String toString() {
        return "prog(" +
                "decls(" + decls.toString().replaceAll("[\\[\\]{}]", "") + ")" +
                ", " + expression +
                ")";
    }
}
