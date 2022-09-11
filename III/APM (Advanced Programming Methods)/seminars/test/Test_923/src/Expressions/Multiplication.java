package Expressions;

public class Multiplication implements Expression {
    Expression expression;
    Expression expression2;

    public Multiplication(Expression expression, Expression expression2){
        this.expression = expression;
        this.expression2 = expression2;
    }

    @Override
    public int evaluate() {
        return (expression.evaluate() * expression2.evaluate()) % 10;
    }

    @Override
    public String toString() {
        return "( " + expression.toString() + " * " + expression2.toString() + " ) % 10";
    }
}
