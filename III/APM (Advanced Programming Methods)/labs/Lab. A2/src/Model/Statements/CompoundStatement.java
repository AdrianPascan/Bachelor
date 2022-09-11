package Model.Statements;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.ProgramState.ExecutionStack;
import Model.ProgramState.ProgramState;
import Model.Types.Type;

public class CompoundStatement implements InterfaceStatement {
    private InterfaceStatement first;
    private InterfaceStatement second;

    public CompoundStatement(InterfaceStatement firstStatement, InterfaceStatement secondStatement){
        this.first = firstStatement;
        this.second = secondStatement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        ExecutionStack executionStack = programState.getExecutionStack();
        executionStack.push(second);
        executionStack.push(first);
        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        return second.typeCheck(first.typeCheck(typeEnvironment));
    }

    @Override
    public String toString() {
        return "(" + first.toString() + ";" + second.toString() + ")";
    }
}
