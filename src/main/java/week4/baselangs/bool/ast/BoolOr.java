package week4.baselangs.bool.ast;

public record BoolOr(BoolNode left, BoolNode right) implements BoolNode {
}