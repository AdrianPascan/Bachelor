import Expressions.Expression;

import java.util.List;

public interface RepositoryInterface {
    public void add (Expression expression);
    public List<Expression> getAll();
}
