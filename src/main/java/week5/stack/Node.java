package week5.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public record Node(String label, List<Node> children) {

    // alamkonstruktorid kutsuvad välja Record peamise konstruktori
    public Node(String label) {
        this(label, new ArrayList<>());
    }

    public Node(char label) {
        this(Character.toString(label));
    }

    public void add(Node n) {
        children.add(n);
    }

    public String toString() {
        if (children.isEmpty()) return label;
        StringJoiner joiner = new StringJoiner(",", label + "(", ")");
        for (Node child : children) joiner.add(child.toString());
        return joiner.toString();
    }
}

