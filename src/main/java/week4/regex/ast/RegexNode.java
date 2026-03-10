package week4.regex.ast;

/**
 * Regulaaravaldise süntakspuu tipude ülemklass. Annab ka homogeense vaade ASTile.
 */
public sealed interface RegexNode permits Alternation, Concatenation, Epsilon, Letter, Repetition {

}


