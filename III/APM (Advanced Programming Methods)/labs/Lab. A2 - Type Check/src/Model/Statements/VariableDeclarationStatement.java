package Model.Statements;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.ProgramState.ExecutionStack;
import Model.ProgramState.ProgramState;
import Model.ProgramState.SymbolTable;
import Model.ProgramState.TypeEnvironment;
import Model.Types.Type;

public class VariableDeclarationStatement implements InterfaceStatement {
    private String name;
    private Type type;

    public VariableDeclarationStatement(String name, Type type){
        this.name = name;
        this.type = type;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        SymbolTable symbolTable = programState.getSymbolTable();
        TypeEnvironment typeEnvironment = programState.getTypeEnvironment();
        if (symbolTable.isDefined(name)){
            throw new MyException("Variable " + name + "is already declared.");
        }
        symbolTable.update(name, type.getDefaultValue());
        typeEnvironment.update(name, type);
        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        typeEnvironment.update(name, type);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }
}
