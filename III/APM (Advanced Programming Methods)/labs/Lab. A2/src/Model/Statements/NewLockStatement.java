package Model.Statements;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.ProgramState.LockTable;
import Model.ProgramState.ProgramState;
import Model.ProgramState.SymbolTable;
import Model.Types.IntegerType;
import Model.Types.Type;
import Model.Values.IntegerValue;
import Model.Values.Value;

public class NewLockStatement implements InterfaceStatement {
    private String id;

    public NewLockStatement(String id) {
        this.id = id;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        SymbolTable symbolTable = programState.getSymbolTable();
        LockTable lockTable = programState.getLockTable();

        Value value = symbolTable.lookup(id);
        if (! (value.getType().equals(new IntegerType()))) {
            throw new MyException("Variable " + id + " is not of type integer.");
        }

        int address = lockTable.getFreeAddressAndComputeNext();
        symbolTable.update(id, new IntegerValue(address));
        lockTable.update(address, -1);

        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        Type type = typeEnvironment.lookup(id);
        if (! (type.equals(new IntegerType())) ){
            throw new MyException("Argument of new lock statement is not integer.");
        }

        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "newLock(" + id + ")";
    }
}
