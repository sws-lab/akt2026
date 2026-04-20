package week7.ast;

public sealed interface VariableBinding permits FunctionParameter, VariableDeclaration {
    String variableName();
    String type();
}
