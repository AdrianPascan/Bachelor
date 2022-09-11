package Model.Statements;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.ProgramState.ProgramState;
import Model.ProgramState.SymbolTable;
import Model.Types.Type;

public class ForkStatement implements InterfaceStatement {
    private InterfaceStatement statement;

    public ForkStatement(InterfaceStatement statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        ProgramState newProgramState = new ProgramState(statement);
        newProgramState.setSymbolTable((SymbolTable) programState.getSymbolTable().getClone());
        newProgramState.setHeap(programState.getHeap());
        newProgramState.setFileTable(programState.getFileTable());
        newProgramState.setOutput(programState.getOutput());
        newProgramState.setProgram(statement);

        return newProgramState;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        statement.typeCheck(typeEnvironment);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "fork(" + statement.toString() + ")";
    }
}
