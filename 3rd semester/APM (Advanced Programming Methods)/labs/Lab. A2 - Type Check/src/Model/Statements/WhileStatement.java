package Model.Statements;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.Expressions.InterfaceExpression;
import Model.ProgramState.ExecutionStack;
import Model.ProgramState.Heap;
import Model.ProgramState.ProgramState;
import Model.ProgramState.SymbolTable;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class WhileStatement implements InterfaceStatement {
    private InterfaceExpression expression;
    private InterfaceStatement statement;

    public WhileStatement(InterfaceExpression expression, InterfaceStatement statement) {
        this.expression = expression;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        ExecutionStack executionStack = programState.getExecutionStack();
        SymbolTable symbolTable = programState.getSymbolTable();
        Heap heap = programState.getHeap();

        Value value = expression.evaluate(symbolTable, heap);
        if (value instanceof BoolValue) {
            boolean condition = ((BoolValue) value).getValue();
            if (condition) {
                executionStack.push(this);
                executionStack.push(statement);
            }
        }
        else {
            throw new MyException("Conditional expression of while statement does not evaluate as a boolean value.");
        }

        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        Type type = expression.typeCheck(typeEnvironment);

        if (type.equals(new BoolType())){
            statement.typeCheck(typeEnvironment);
            return typeEnvironment;
        }
        else {
            throw new MyException("The condition of while statement does not evaluate to boolean.");
        }
    }

    @Override
    public String toString() {
        return "While(" + expression.toString() + ") " + statement.toString();
    }
}
