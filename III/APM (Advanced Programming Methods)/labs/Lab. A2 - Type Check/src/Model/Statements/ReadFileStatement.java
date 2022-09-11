package Model.Statements;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.Expressions.InterfaceExpression;
import Model.Expressions.VariableExpression;
import Model.ProgramState.FileTable;
import Model.ProgramState.Heap;
import Model.ProgramState.ProgramState;
import Model.ProgramState.SymbolTable;
import Model.Types.IntegerType;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.IntegerValue;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements InterfaceStatement {
    private InterfaceExpression expression;
    private String variableId;

    public ReadFileStatement(InterfaceExpression expression, String variableId){
        this.expression = expression;
        this.variableId = variableId;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        SymbolTable symbolTable = programState.getSymbolTable();
        FileTable fileTable = programState.getFileTable();
        Heap heap = programState.getHeap();

        Value value = symbolTable.lookup(variableId);
        if (! value.getType().equals(new IntegerType()) ){
            throw new MyException("Variable " + variableId + " is not of type integer.");
        }

        Value valueExpression = expression.evaluate(symbolTable, heap);
        if (! valueExpression.getType().equals(new StringType()) ){
            throw new MyException("Expression of read file statement is not of type string.");
        }
        StringValue stringValueExpression = (StringValue) valueExpression;

        BufferedReader reader = fileTable.lookup(stringValueExpression);
        try{
            String line = reader.readLine();
            symbolTable.update(variableId, new IntegerValue( Integer.parseInt(line) ) );
        } catch (IOException error) {
            symbolTable.update(variableId, new IntegerValue(0));
        }

        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        Type typeVariable = typeEnvironment.lookup(variableId);
        if (! (typeVariable.equals(new IntegerType())) ){
            throw new MyException("Variable " + variableId + " is not of type integer in read file statement.");
        }

        Type typeExpression = expression.typeCheck(typeEnvironment);
        if (! (typeExpression.equals(new StringType())) ) {
            throw new MyException("Expression of read file statement does not evaluate to string.");
        }

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "ReadFile (" + expression.toString() + ", " + variableId.toString() + ")";
    }
}
