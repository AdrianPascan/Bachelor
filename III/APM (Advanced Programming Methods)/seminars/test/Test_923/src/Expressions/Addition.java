package Expressions;

public class Addition implements Expression {
    Expression expression;
    Expression expression2;

    public Addition(Expression expression, Expression expression2) {
        this.expression = expression;
        this.expression2 = expression2;
    }

    @Override
    public int evaluate() {
        return (expression.evaluate() + expression2.evaluate()) % 10;
    }

    @Override
    public String toString() {
        return "( "+ expression.toString() + " + " + expression2.toString() + " ) % 10";
    }
}
