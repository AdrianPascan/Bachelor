package Model.Expressions;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.ProgramState.Heap;
import Model.ProgramState.SymbolTable;
import Model.Types.Type;
import Model.Values.Value;

public class ValueExpression implements InterfaceExpression {
    private Value value;

    public ValueExpression(Value value){
        this.value = value;
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws MyException {
        return value;
    }

    @Override
    public Type typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        return value.getType();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
