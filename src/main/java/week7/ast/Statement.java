package week7.ast;

/**
 * Erinevate lauseliikide ülemklass
 */
public sealed interface Statement extends AstNode permits Assignment, Block, ExpressionStatement, FunctionDefinition, IfStatement, ReturnStatement, VariableDeclaration, WhileStatement {
}
