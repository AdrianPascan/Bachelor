package Model.Statements;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.ProgramState.ProgramState;
import Model.Types.Type;

public class NoOperationStatement implements InterfaceStatement {
    public NoOperationStatement(ProgramState programState){};

    @Override
    public ProgramState execute(ProgramState programState) {
        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "no operation";
    }
}
