package Expressions;

public class Modulo implements Expression {
    Expression expression;

    public Modulo(Expression expression){
        this.expression = expression;
    }

    @Override
    public int evaluate() {
        return (expression.evaluate()) % 10;
    }

    @Override
    public String toString() {
        return "( " + expression.toString() + " ) % 10";
    }
}
