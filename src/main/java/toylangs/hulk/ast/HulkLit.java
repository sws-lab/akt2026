package toylangs.hulk.ast;

import java.util.Set;

// Esindab elementide hulka.
// Elementideks on Characterid (kokkuleppeliselt väiketähed)
public record HulkLit(Set<Character> elements) implements HulkExpr {
    @Override
    public String getNodeLabel() {
        return "lit";
    }

    @Override
    public String prettyPrint() {
        return elements.toString().replace('[', '{').replace(']', '}');
    }

    @Override
    public String toString() {
        return "lit(" +
                "" + elements.stream().map(s -> "'" + s + "'").reduce((s1, s2) -> s1 + ", " + s2).orElse("") +
                ")";
    }
}
