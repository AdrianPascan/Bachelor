package Controller;

import Exceptions.MyException;
import Model.ProgramState.ExecutionStack;
import Model.ProgramState.Heap;
import Model.ProgramState.ProgramState;
import Model.ProgramState.TypeEnvironment;
import Model.Statements.InterfaceStatement;
import Model.Types.ReferenceType;
import Model.Values.ReferenceValue;
import Model.Values.Value;
import Repository.InterfaceRepository;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Controller{
    private InterfaceRepository repository;
    private boolean display;
    private ExecutorService executor;

    public Controller(InterfaceRepository repository, boolean display){
        this.repository = repository;
        this.display = display;
    }

    public void allStepExecution() {
        try {
            if (repository.getProgramStateList().size() > 0) {
                typeCheckingForProgramState(repository.getProgramStateList().get(0));
            }

            executor = Executors.newFixedThreadPool(2);

            List<ProgramState> programStates = removeCompletedPrograms(repository.getProgramStateList());
            while (programStates.size() > 0) {
                Heap heap = programStates.get(0).getHeap();
                heap.setContent(safeGarbageCollector(getAddressesFromSymbolTable(getValuesFromAllSymbolTables()), heap.getContent()));

                oneStepExecutionForAllPrograms(programStates);
                programStates = removeCompletedPrograms(repository.getProgramStateList());
            }

            executor.shutdownNow();
            repository.setProgramStateList(programStates);
        }
        catch (MyException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void oneStepExecutionForAllPrograms(List<ProgramState> programStates) {
        programStates.forEach(programState -> {
            try{
                repository.logProgramStateExecution(programState);
                if (display) {
                    System.out.println(programState.toString());
                }
            }
            catch (MyException exception) {
                System.out.println(exception.getMessage());
            }
        });

        List<Callable<ProgramState>> callableList = programStates.stream()
                .map((ProgramState programState) -> (Callable<ProgramState>)(() -> {return programState.oneStep();}))
                .collect(Collectors.toList());

        try {
            executor = Executors.newFixedThreadPool(2);
            List<ProgramState> newProgramStates = executor.invokeAll(callableList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        }
                        catch (InterruptedException | ExecutionException exception){
                            System.out.println(exception.getMessage());
                            return null;
                        }
                    })
                    .filter(programState -> programState != null)
                    .collect(Collectors.toList());
            executor.shutdownNow();

            programStates.addAll(newProgramStates);
            programStates.forEach(programState -> {
                try {
                    repository.logProgramStateExecution(programState);
                    if (display) {
                        System.out.println(programState.toString());
                    }
                } catch (MyException exception) {
                    System.out.println(exception.getMessage());
                }
            });
        }
        catch (InterruptedException exception) {
            System.out.println(exception.getMessage());
        }

        repository.setProgramStateList(programStates);
    }

    public void typeCheckingForProgramState(ProgramState programState) throws MyException{
        TypeEnvironment typeEnvironment = programState.getTypeEnvironment();
        InterfaceStatement program = programState.getProgram();

        program.typeCheck(typeEnvironment);
        System.out.println(typeEnvironment.toString());
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> programStates) {
        return programStates.stream()
                .filter(programState -> programState.isNotCompleted())
                .collect(Collectors.toList());
    }

    public List<Integer> getAddressesFromSymbolTable(Collection<Value> symbolTableValues){
        return symbolTableValues.stream()
                .filter(value -> value instanceof ReferenceValue)
                .map(value -> {ReferenceValue auxiliary = (ReferenceValue) value; return auxiliary.getAddress(); })
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(addressCounter -> addressCounter.getValue() == 1)
                .map(addressCounter -> addressCounter.getKey())
                .collect(Collectors.toList());
    }

    public Map<Integer, Value> unsafeGarbageCollector(List<Integer> symbolTableAddresses, Map<Integer, Value> heap) {
        return heap.entrySet().stream()
                .filter(element -> symbolTableAddresses.contains(element.getKey()))
                .collect(Collectors.toMap(Map.Entry<Integer, Value>::getKey, Map.Entry<Integer, Value>::getValue));
    }

    public Map<Integer, Value> safeGarbageCollector(List<Integer> symbolTableAddresses, Map<Integer, Value> heap) {

        return heap.entrySet().stream()
                .filter(value -> {
                    if (value.getValue() instanceof ReferenceValue) {
                        ReferenceValue reference = (ReferenceValue) value.getValue();
                        while (heap.containsKey(reference.getAddress()) && reference.getLocationType() instanceof ReferenceType) {
                            reference = (ReferenceValue) heap.get(reference.getAddress());
                        }
                        if (heap.containsKey(reference.getAddress()) && !(reference.getLocationType() instanceof ReferenceType)) {
                            return true;
                        }
                        return false;
                    }
                    return true;
                }
                )
                .collect(Collectors.toMap(Map.Entry<Integer, Value>::getKey, Map.Entry<Integer, Value>::getValue));
    }

    public Collection<Value> getValuesFromAllSymbolTables() {
        Collection<Value> values = new ArrayList<Value>();
        for (ProgramState programState: repository.getProgramStateList() ) {
            values.addAll(programState.getSymbolTable().getContent().values());
        }
        return values;
    }
}