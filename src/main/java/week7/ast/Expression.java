package week7.ast;

/**
 * Erinevate avaldiseliikide ülemklass.
 */
public sealed interface Expression extends AstNode permits FunctionCall, IntegerLiteral, StringLiteral, Variable {
}
