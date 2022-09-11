import Expressions.Expression;

import java.util.List;

public class Controller {
    private RepositoryInterface repository;

    public Controller(RepositoryInterface repository) {
        this.repository = repository;
    }

    public void evaluateAll() {
//        List<Expression> expressions = repository.getAll();
        for (Expression expression: repository.getAll()) {
            int value = expression.evaluate();
            System.out.println(expression.toString() + " = " + Integer.toString(value));
        }
    }
}
