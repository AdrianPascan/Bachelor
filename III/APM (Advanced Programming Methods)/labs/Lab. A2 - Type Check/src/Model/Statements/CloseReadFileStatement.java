package Model.Statements;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.Expressions.InterfaceExpression;
import Model.ProgramState.FileTable;
import Model.ProgramState.Heap;
import Model.ProgramState.ProgramState;
import Model.ProgramState.SymbolTable;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFileStatement implements InterfaceStatement {
    private InterfaceExpression expression;

    public CloseReadFileStatement(InterfaceExpression expression){
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        FileTable fileTable = programState.getFileTable();
        SymbolTable symbolTable = programState.getSymbolTable();
        Heap heap = programState.getHeap();

        Value value = expression.evaluate(symbolTable, heap);
        if (!value.getType().equals(new StringType())){
            throw new MyException("Expression of open read file statement is not of type string.");
        }
        StringValue stringValue = (StringValue) value;

        BufferedReader reader = fileTable.lookup(stringValue);
        try{
            reader.close();
        } catch (IOException error) {
            throw new MyException("Cannot close file: " + stringValue);
        }
        fileTable.remove(stringValue);

        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        Type type = expression.typeCheck(typeEnvironment);

        if (type.equals(new StringType())) {
            return typeEnvironment;
        }
        else {
            throw new MyException("Expression of close read file statement does not evaluate to string.");
        }
    }

    @Override
    public String toString() {
        return "CloseReadFile (" + expression.toString() + ")";
    }
}
