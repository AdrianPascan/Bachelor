package Repository;

import Exceptions.MyException;
import Model.ProgramState.ProgramState;

import java.util.List;

public interface InterfaceRepository {
    void logProgramStateExecution(ProgramState programState) throws MyException;
    List<ProgramState> getProgramStateList();
    void setProgramStateList(List<ProgramState> newStates);
}
