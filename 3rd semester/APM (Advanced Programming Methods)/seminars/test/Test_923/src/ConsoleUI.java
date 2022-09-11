import Expressions.*;
import Expressions.Number;

public class ConsoleUI {
    public void start() {
        Expression expression = new Addition(new Number(2), new Number(10));
        Expression expression2 = new Multiplication(new Number(3), new Number(10));
        Expression expression3 = new Modulo(new Number(2));
        Expression expression4 = new Addition(new Addition(new Number(2), new Number(10)),
                new Multiplication(new Number(7), new Number(4)));

        RepositoryInterface repository = new Repository();
        repository.add(expression);
        repository.add(expression2);
        repository.add(expression3);
        repository.add(expression4);

        Controller controller = new Controller(repository);
        controller.evaluateAll();
    }
}
