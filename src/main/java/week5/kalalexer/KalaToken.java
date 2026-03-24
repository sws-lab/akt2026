package week5.kalalexer;

public record KalaToken(Type type, Object data) {
    public enum Type {
        IDENT,
        COMMA,
        LPAREN, RPAREN,
        NULL,
        EOF
    }

    public KalaToken {
        assert type != null;
    }

    public KalaToken(Type type) {
        this(type, null);
    }

    @Override
    public String toString() {
        return "<" + type
                + (data == null ? "" : (":" + data))
                + ">";
    }
}
