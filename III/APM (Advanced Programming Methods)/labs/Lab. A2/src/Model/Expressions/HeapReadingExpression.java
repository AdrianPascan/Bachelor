package Model.Expressions;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.ProgramState.Heap;
import Model.ProgramState.SymbolTable;
import Model.Types.ReferenceType;
import Model.Types.Type;
import Model.Values.ReferenceValue;
import Model.Values.Value;

public class HeapReadingExpression implements InterfaceExpression {
    private InterfaceExpression expression;

    public HeapReadingExpression(InterfaceExpression expression) {
        this.expression = expression;
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws MyException {
        Value value = expression.evaluate(symbolTable, heap);
        if (! (value instanceof ReferenceValue) ){
            throw new MyException("Value of expression is not a reference value.");
        }
        ReferenceValue referenceValue = (ReferenceValue) value;

        int address = referenceValue.getAddress();
        return heap.lookup(address);
    }

    @Override
    public Type typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        Type type = expression.typeCheck(typeEnvironment);
        if (type instanceof ReferenceType) {
            ReferenceType reference = (ReferenceType) type;
            return reference.getInner();
        }
        else {
            throw new MyException("The expression of heap reading is not a reference type.");
        }
    }

    @Override
    public String toString() {
        return "rH(" + expression.toString() + ")";
    }
}
