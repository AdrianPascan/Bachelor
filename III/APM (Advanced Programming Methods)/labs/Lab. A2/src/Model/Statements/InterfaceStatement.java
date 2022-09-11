package Model.Statements;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.ProgramState.ProgramState;
import Model.Types.Type;

public interface InterfaceStatement {
    ProgramState execute(ProgramState programState) throws MyException;
    MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException;
}
