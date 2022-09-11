package Repository;

import Exceptions.MyException;
import Model.ProgramState.ProgramState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class DefaultRepository implements InterfaceRepository {
    private List<ProgramState> states;
    private String logFilePath;

    public DefaultRepository(ProgramState state){
        states = new ArrayList<ProgramState>();
        states.add(state);
        logFilePath = new String("");
    }

    public DefaultRepository(ProgramState state, String logFilePath){
        states = new ArrayList<ProgramState>();
        states.add(state);
        this.logFilePath = logFilePath;
    }

    @Override
    public void logProgramStateExecution(ProgramState programState) throws MyException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println(programState.toString());
            logFile.close();
        }
        catch (IOException error) {
            throw new MyException("Error when writing into file: " + logFilePath);
        }
    }

    @Override
    public List<ProgramState> getProgramStateList(){
        return states;
    }

    @Override
    public void setProgramStateList(List<ProgramState> newStates) {
        states = newStates;
    }
}
