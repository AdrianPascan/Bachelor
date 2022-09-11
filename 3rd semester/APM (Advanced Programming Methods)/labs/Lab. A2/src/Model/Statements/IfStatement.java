package Model.Statements;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.Expressions.InterfaceExpression;
import Model.ProgramState.ExecutionStack;
import Model.ProgramState.ProgramState;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class IfStatement implements InterfaceStatement {
    private InterfaceExpression condition;
    private InterfaceStatement thenStatement;
    private InterfaceStatement elseStatement;

    public IfStatement(InterfaceExpression condition, InterfaceStatement thenStatement, InterfaceStatement elseStatement){
        this.condition = condition;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        ExecutionStack executionStack = programState.getExecutionStack();
        Value conditionValue = condition.evaluate(programState.getSymbolTable(), programState.getHeap());
        if (! conditionValue.getType().equals(new BoolType())){
            throw new MyException("Conditional expression is not a logic expression");
        }
        if (conditionValue.isEqual(new BoolValue(true))){
            executionStack.push(thenStatement);
        }
        else{
            executionStack.push(elseStatement);
        }
        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        Type typeExpression = condition.typeCheck(typeEnvironment);
        if (typeExpression.equals(new BoolType())) {
            thenStatement.typeCheck(typeEnvironment);
            elseStatement.typeCheck(typeEnvironment);
            return typeEnvironment;
        }
        else {
            throw new MyException("The condition of if statement does not evaluate to boolean.");
        }
    }

    @Override
    public String toString() {
        return "If (" + condition.toString() + ") Then( " + thenStatement.toString() +
                " ) Else( " + elseStatement.toString() + " )";
    }
}
