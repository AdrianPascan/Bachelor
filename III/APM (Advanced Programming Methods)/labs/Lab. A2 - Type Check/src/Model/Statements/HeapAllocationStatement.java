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

public class HeapAllocationStatement implements InterfaceStatement {
    private String id;
    private InterfaceExpression expression;

    public HeapAllocationStatement(String id, InterfaceExpression expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        SymbolTable symbolTable = programState.getSymbolTable();
        Heap heap = programState.getHeap();

        Value value = symbolTable.lookup(id);
        if (! (value.getType() instanceof ReferenceType) ){
            throw new MyException("Variable " + id + " is not of type reference.");
        }
        ReferenceValue variableValue = (ReferenceValue) value;

        Value expressionValue = expression.evaluate(symbolTable, heap);
        if (! variableValue.getLocationType().equals(expressionValue.getType()) ){
            throw new MyException("The type referenced by variable " + id + " does not coincide with the type of the value of the expression.");
        }

        int address = heap.getFreeAddress();
        heap.update(address, expressionValue);

        symbolTable.update(id, new ReferenceValue(address, variableValue.getLocationType()));

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
            throw new MyException("Left hand side and right hand side of heap allocation statement have different types.");
        }
    }

    @Override
    public String toString() {
        return "new(" + id + ", " + expression.toString() + ")";
    }
}
