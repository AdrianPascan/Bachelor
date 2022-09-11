package Model.ProgramState;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.Statements.InterfaceStatement;
import Model.Values.Value;

import java.util.concurrent.atomic.AtomicInteger;

public class ProgramState {
    private ExecutionStack executionStack;
    private SymbolTable symbolTable;
    private Output outputList;
    private FileTable fileTable;
    private Heap heap;
    private TypeEnvironment typeEnvironment;
    private LockTable lockTable;
    private InterfaceStatement originalProgram;
    private int statementId;
    private static AtomicInteger nextStatementId = new AtomicInteger(1);

    public ProgramState(InterfaceStatement program){
        executionStack = new ExecutionStack();
        executionStack.push(program);
        symbolTable = new SymbolTable();
        outputList = new Output();
        fileTable = new FileTable();
        heap = new Heap();
        typeEnvironment = new TypeEnvironment();
        lockTable = new LockTable();
        originalProgram = program;

        statementId = ProgramState.getNextStatementId();
    }

    public ProgramState(InterfaceStatement program, ExecutionStack executionStack, SymbolTable symbolTable, Output outputList, FileTable fileTable, Heap heap, TypeEnvironment typeEnvironment, LockTable lockTable) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.outputList = outputList;
        this.fileTable = fileTable;
        this.heap = heap;
        this.typeEnvironment = typeEnvironment;
        this.lockTable = lockTable;
        this.originalProgram = program;

        statementId = ProgramState.getNextStatementId();
    }

    public static int getNextStatementId() {
        return nextStatementId.getAndIncrement();
    }

    public boolean isNotCompleted() {
        return !( executionStack.isEmpty() );
    }

    public ProgramState oneStep() throws MyException {
        if (executionStack.isEmpty()) {
            throw new MyException("Execution stack of program state is empty.");
        }
        InterfaceStatement currentStatement = executionStack.pop();
        return currentStatement.execute(this);
    }

    @Override
    public String toString() {
        return "PROGRAM STATE " +
                Integer.toString(statementId) +
                "\n" +  executionStack.toString() + symbolTable.toString() + outputList.toString() + fileTable.toString() + heap.toString() + lockTable.toString();
    }

    public ExecutionStack getExecutionStack() {
        return executionStack;
    }

    public void setExecutionStack(ExecutionStack newExecutionStack) {
        executionStack = newExecutionStack;
    }

    public SymbolTable getSymbolTable(){
        return symbolTable;
    }

    public void setSymbolTable(SymbolTable newSymbolTable) {
        symbolTable = newSymbolTable;
    }

    public Output getOutput(){
        return outputList;
    }

    public void setOutput(Output newOutput) {
        outputList = newOutput;
    }

    public FileTable getFileTable() {
        return fileTable;
    }

    public void setFileTable(FileTable newFileTable) {
        fileTable = newFileTable;
    }

    public Heap getHeap() { return heap; }

    public void setHeap(Heap newHeap) {
        heap = newHeap;
    }

    public TypeEnvironment getTypeEnvironment() {
        return typeEnvironment;
    }

    public LockTable getLockTable() {
        return lockTable;
    }

    public InterfaceStatement getProgram() {
        return originalProgram;
    }

    public void setProgram(InterfaceStatement program) {
        this.originalProgram = program;
    }

    public int getStatementId() {
        return statementId;
    }
}
