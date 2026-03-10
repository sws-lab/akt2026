package toylangs.dialoog.ast;

public record DialoogDecl(String name, boolean intType) implements DialoogNode {
    @Override
    public String getNodeLabel() {
        return intType ? "iv" : "bv";
    }

    @Override
    public String toString() {
        return (intType ? "iv" : "bv") + "(" +
                "\"" + name + "\"" +
                ")";
    }
}
