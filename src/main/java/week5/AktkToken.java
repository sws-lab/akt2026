package week5;

import org.apache.commons.text.StringEscapeUtils;

import java.util.Objects;

/**
 * @param type
 * @param data tüüp sõltub type-ist, vt. täpsemalt TokenType juurest
 * @param offset token-ile vastava teksti alguse indeks
 * @param length token-ile vastava teksti pikkus
 */
public record AktkToken(Type type, Object data, int offset, int length) {

    public enum Type {
        VARIABLE,                // Token-i data peab olema muutuja nimi (String)
        STRING, INTEGER,         // Literaalid. Token-i data peab olema vastav väärtus (String või Integer)
        LPAREN, RPAREN,          // Alustav ja lõpetav ümarsulg. Token-i data peab olema null
        PLUS, MINUS, TIMES, DIV, // Operaatorid. Token-i data peab olema null
        IF, WHILE, VAR,          // Võtmesõnad. Token-i data peab olema null
        EOF                      // Token-i data peab olema null
    }

    public AktkToken(Type type) {
        this(type, null, -1, -1);
    }

    public AktkToken(Type type, Object data) {
        this(type, data, -1, -1);
    }

    public AktkToken(Type type, int offset, int length) {
        this(type, null, offset, length);
    }

    // kasutab LexerRegex - teised kutsuvad otse positsiooni ja pikkusega välja
    // teeb kutsutavast objektist koopia, kirjutades seejuures üle algusindeksi & pikkuse
    public AktkToken withPosition(int offset, int length) {
        return new AktkToken(this.type, this.data, offset, length);
    }

    public boolean noOffset() {
        return offset < 0;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append('<').append(type);
        if (data != null) {
            result.append(":").append(StringEscapeUtils.escapeJava(data.toString()));
            result.append(':').append(data.getClass().getSimpleName());
        }
        result.append('>');
        if (offset >= 0) result.append("@").append(offset).append("-").append(offset + length);
        return result.toString();
    }

    /** Vaikimisi genereeritud equals() ei oska arvestada juhtumitega noOffset() || token.noOffset().
     *  Selle meetodi eemaldamisel ei läheks AktkHandwrittenLexerTest testid läbi.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AktkToken token = (AktkToken) o;
        return type == token.type && Objects.equals(data, token.data) &&
                (noOffset() || token.noOffset() || offset == token.offset && length == token.length);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, data); // Vaikimisi hashCode() arvestaks ka offset-i ja length-iga, kuid equals()-iga kooskõlaks tuleb neid mitte arvestada.
    }
}
