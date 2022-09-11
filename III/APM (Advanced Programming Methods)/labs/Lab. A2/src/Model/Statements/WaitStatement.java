package Model.Statements;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.Expressions.InterfaceExpression;
import Model.Expressions.ValueExpression;
import Model.ProgramState.ExecutionStack;
import Model.ProgramState.Heap;
import Model.ProgramState.ProgramState;
import Model.ProgramState.SymbolTable;
import Model.Types.IntegerType;
import Model.Types.Type;
import Model.Values.IntegerValue;
import Model.Values.Value;

public class WaitStatement implements InterfaceStatement {
    private InterfaceExpression expression;

    public WaitStatement(InterfaceExpression expression) {
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        SymbolTable symbolTable = programState.getSymbolTable();
        Heap heap = programState.getHeap();
        ExecutionStack executionStack = programState.getExecutionStack();

        Value value = expression.evaluate(symbolTable, heap);
        if (value.getType().equals( new IntegerType() )) {
            int integerValue = ((IntegerValue) value).getValue();

            if (integerValue < 0) {
                throw new MyException("Argument of wait statement must be positive.");
            }
            if (integerValue > 0) {
                InterfaceStatement compoundStatement = new CompoundStatement(
                        new PrintStatement(new ValueExpression(new IntegerValue(integerValue))),
                        new WaitStatement(new ValueExpression(new IntegerValue(integerValue - 1)))
                    );
                executionStack.push(compoundStatement);
            }
        }
        else {
            throw new MyException("Argument of wait statement is not an integer.");
        }

        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        Type type = expression.typeCheck(typeEnvironment);

        if (type.equals(new IntegerType())){
            return typeEnvironment;
        }
        else {
            throw new MyException("Argument of wait statement is not an integer.");
        }
    }

    @Override
    public String toString() {
        return "wait(" + expression.toString() + ")";
    }
}
