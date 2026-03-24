package proovieksam.ast;

public record EstologMuutuja(String nimi) implements EstologNode {
    @Override
    public String getNodeLabel() {
        return "var";
    }

    @Override
    public String toString() {
        return "var(" +
                "\"" + nimi + "\"" +
                ")";
    }
}
