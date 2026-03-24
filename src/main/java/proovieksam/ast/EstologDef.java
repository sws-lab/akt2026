package proovieksam.ast;

public record EstologDef(String nimi, EstologNode avaldis) implements EstologNode {
    @Override
    public String toString() {
        return "def(" +
                "\"" + nimi + "\"" +
                ", " + avaldis +
                ")";
    }
}
