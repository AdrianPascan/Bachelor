package Model.Statements;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.Expressions.InterfaceExpression;
import Model.ProgramState.*;
import Model.Types.StringType;
import Model.Types.Type;
import Model.Values.StringValue;
import Model.Values.Value;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenReadFileStatement implements InterfaceStatement {
    InterfaceExpression expression;

    public OpenReadFileStatement(InterfaceExpression expression){
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        SymbolTable symbolTable = programState.getSymbolTable();
        FileTable fileTable = programState.getFileTable();
        Heap heap = programState.getHeap();

        Value value = expression.evaluate(symbolTable, heap);
        if (!value.getType().equals(new StringType())){
            throw new MyException("Expression of open read file statement is not of type string.");
        }
        StringValue stringValue = (StringValue) value;

        if (fileTable.isDefined(stringValue) ){
            throw new MyException("File " + value + " is already opened.");
        }

        try{
            BufferedReader reader = new BufferedReader(new FileReader(stringValue.getValue()));
            fileTable.update(stringValue, reader);
        } catch (FileNotFoundException error) {
            throw new MyException("File " + stringValue.getValue() + " not found.");
        }

        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        Type type = expression.typeCheck(typeEnvironment);

        if (type.equals(new StringType())) {
            return typeEnvironment;
        }
        else {
            throw new MyException("Expression of open read file statement does not evaluate to string.");
        }
    }

    @Override
    public String toString() {
        return "OpenReadFile (" + expression.toString() + ")";
    }
}
