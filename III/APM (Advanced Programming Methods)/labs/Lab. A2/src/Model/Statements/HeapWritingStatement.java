package Model.Statements;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.Expressions.InterfaceExpression;
import Model.ProgramState.Heap;
import Model.ProgramState.ProgramState;
import Model.ProgramState.SymbolTable;
import Model.Types.ReferenceType;
import Model.Types.Type;
import Model.Values.ReferenceValue;
import Model.Values.Value;

public class HeapWritingStatement implements InterfaceStatement {
    private String id;
    private InterfaceExpression expression;

    public HeapWritingStatement(String id, InterfaceExpression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        SymbolTable symbolTable = programState.getSymbolTable();
        Heap heap = programState.getHeap();

        Value value = symbolTable.lookup(id);
        if (! (value instanceof ReferenceValue) ) {
            throw new MyException("Variable " + id + " is not a reference value.");
        }
        ReferenceValue referenceValue = (ReferenceValue) value;

        int address = referenceValue.getAddress();
        if (! heap.isDefined(address) ){
            throw new MyException("Variable " + id + " does not reference a valid location in the heap.");
        }

        Value expressionValue = expression.evaluate(symbolTable, heap);
        if (! referenceValue.getLocationType().equals( expressionValue.getType() ) ) {
            throw new MyException("Type of the value referenced by variable " + id + " does not coincide with the value of expression.");
        }

        heap.update(address, expressionValue);

        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        Type typeVariable = typeEnvironment.lookup(id);
        Type typeExpression = expression.typeCheck(typeEnvironment);

        if (typeVariable instanceof ReferenceType &&  ((ReferenceType) typeVariable).getInner().equals(typeExpression)) {
            return typeEnvironment;
        }
        else {
            throw new MyException("Left hand side and right hand side of heap writing statement have different types.");
        }
    }

    @Override
    public String toString() {
        return "wH(" + id + ", " + expression.toString() + ")";
    }
}
