package Model.Statements;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.Expressions.InterfaceExpression;
import Model.ProgramState.Heap;
import Model.ProgramState.ProgramState;
import Model.ProgramState.SymbolTable;
import Model.Types.Type;
import Model.Values.Value;

public class AssignmentStatement implements InterfaceStatement {
    private String id;
    private InterfaceExpression expression;

    public AssignmentStatement(String id, InterfaceExpression expression){
        this.id = id;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        SymbolTable symbolTable = programState.getSymbolTable();
        Heap heap = programState.getHeap();

        if (symbolTable.isDefined(id)){
            Value value = expression.evaluate(symbolTable, heap);
            Type type = symbolTable.getValue(id).getType();
            if (value.getType().equals(type)){
                symbolTable.update(id, value);
            }
            else{
                throw new MyException("The declared type of variable " + id + " and the type of the expression do not match.");
            }
        }
        else {
            throw new MyException("Variable " + id + " was not declared before.");
        }

        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        Type typeVariable = typeEnvironment.lookup(id);
        Type typeExpression = expression.typeCheck(typeEnvironment);

        if (typeVariable.equals(typeExpression)) {
            return typeEnvironment;
        }
        else {
            throw new MyException("Left hand side and right hand side of assignment statement have different types.");
        }
    }

    @Override
    public String toString() {
        return id + " = " + expression.toString();
    }
}
