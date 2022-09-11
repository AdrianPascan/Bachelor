package Model.Expressions;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.ProgramState.Heap;
import Model.ProgramState.SymbolTable;
import Model.Types.Type;
import Model.Values.Value;

public interface InterfaceExpression {
    Value evaluate(SymbolTable symbolTable, Heap heap) throws MyException;
    Type typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException;
}
