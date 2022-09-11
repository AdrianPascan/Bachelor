package Model.Expressions;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.ProgramState.Heap;
import Model.ProgramState.SymbolTable;
import Model.Types.Type;
import Model.Values.Value;

public class VariableExpression implements InterfaceExpression{
    private String id;

    public VariableExpression(String id){
        this.id = id;
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws MyException {
        return symbolTable.lookup(id);
    }

    @Override
    public Type typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        return typeEnvironment.lookup(id);
    }

    @Override
    public String toString() {
        return id;
    }
}
