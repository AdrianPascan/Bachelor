import Expressions.Expression;

import java.util.ArrayList;
import java.util.List;

public class Repository implements RepositoryInterface {
    List<Expression> expressions;

    Repository(){
        expressions = new ArrayList<Expression>();
    }

    @Override
    public void add(Expression expression) {
        expressions.add(expression);
    }

    @Override
    public List<Expression> getAll() {
        return expressions;
    }
}
