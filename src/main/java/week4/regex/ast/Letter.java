package week4.regex.ast;

/**
 * Tähistab regulaaravaldist, mis sobitub näidatud sümboliga
 * (see sümbol ei pea olema tingimata täht, nagu klassi nimest võiks arvata).
 */
public record Letter(char symbol) implements RegexNode {

    public Letter {
        if (symbol == 'ε') {
            throw new IllegalArgumentException("ε-nodes should be created from record Epsilon");
        }
    }

    @Override
    public String toString() {
        return Character.toString(symbol);
    }
}
