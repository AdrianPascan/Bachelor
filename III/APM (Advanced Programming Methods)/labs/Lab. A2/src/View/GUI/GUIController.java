package View.GUI;

import Controller.Controller;
import Exceptions.MyException;
import Model.Expressions.*;
import Model.ProgramState.*;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntegerType;
import Model.Types.ReferenceType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntegerValue;
import Model.Values.StringValue;
import Model.Values.Value;
import Repository.DefaultRepository;
import Repository.InterfaceRepository;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class GUIController {

    @FXML private ListView<String> exampleListView;
    @FXML private TextField nrOfProgramStatesTextField;
    @FXML private TableView<Pair<Integer, Value>> heapTableView;
    @FXML private TableColumn<Pair<Integer, Value>, Integer> adressHeapTableColumn;
    @FXML private TableColumn<Pair<Integer, Value>, Value> valueHeapTableView;
    @FXML private TableView<Pair<Integer, Integer>> lockTableTableView;
    @FXML private TableColumn<Pair<Integer, Integer>, Integer> addressLockTableTableColumn;
    @FXML private TableColumn<Pair<Integer, Integer>, Integer> valueLockTableTableColumn;
    @FXML private ListView<String> outListView;
    @FXML private ListView<String> fileTableListView;
    @FXML private ListView<String> programStateIdentifierListView;
    @FXML private TableView<Pair<String, Value>> symbolTableTableView;
    @FXML private TableColumn<Pair<String, Value>, String> variableNameSymbolTableTableColumn;
    @FXML private TableColumn<Pair<String, Value>, Value> valueSymbolTableTableColumn;
    @FXML private ListView<String> executionStackListView;
    @FXML private Button oneStepButton;
    @FXML private Button allStepsButton;

    private List<Controller> controllers;
    private List<InterfaceRepository> repositories;
    private List<Boolean> typeCheckingResults;
    private int nrOfExamples;


    public GUIController() {
        controllers = new ArrayList<Controller>();
        repositories = new ArrayList<InterfaceRepository>();
        typeCheckingResults = new ArrayList<Boolean>();
    };

    public void initialise() {
        setupExamples();
        setupLogFiles();
        setupTypeChecking();

        adressHeapTableColumn.setCellValueFactory(new PropertyValueFactory< Pair<Integer, Value>, Integer >("key"));
        valueHeapTableView.setCellValueFactory(new PropertyValueFactory< Pair<Integer, Value>, Value>("value"));

        variableNameSymbolTableTableColumn.setCellValueFactory(new PropertyValueFactory< Pair<String, Value>, String >("key"));
        valueSymbolTableTableColumn.setCellValueFactory(new PropertyValueFactory< Pair<String, Value>, Value >("value"));

        addressLockTableTableColumn.setCellValueFactory(new PropertyValueFactory< Pair<Integer, Integer>, Integer >("key"));
        valueLockTableTableColumn.setCellValueFactory(new PropertyValueFactory< Pair<Integer, Integer>, Integer>("value"));
    }

    @FXML
    void handleOneStepButton(){
        int indexExample = exampleListView.getSelectionModel().getSelectedIndex();
        Controller controller = controllers.get(indexExample);
        InterfaceRepository repository = repositories.get(indexExample);
        List<ProgramState> programStates = repository.getProgramStateList();

        if (programStates.size() > 0) {
            Heap heap = programStates.get(0).getHeap();
            heap.setContent(controller.safeGarbageCollector(controller.getAddressesFromSymbolTable(controller.getValuesFromAllSymbolTables()), heap.getContent()));
            controller.oneStepExecutionForAllPrograms(programStates);
            programStates = controller.removeCompletedPrograms(repository.getProgramStateList());
            repository.setProgramStateList(programStates);
        }

        setupExample();
    }

    @FXML
    public void handleSelectedExample(MouseEvent mouseEvent) {
        if (exampleListView.getSelectionModel().getSelectedIndex() >= 0){
            setupExample();
        }
    }

    @FXML
    public void handleSelectedProgramStateID(MouseEvent mouseEvent) {
        if (programStateIdentifierListView.getSelectionModel().getSelectedIndex() >= 0)
        {
            ProgramState programState = repositories.get(exampleListView.getSelectionModel().getSelectedIndex())
                                                    .getProgramStateList()
                                                    .get(programStateIdentifierListView.getSelectionModel().getSelectedIndex());
            setupProgramState(programState);
        }
    }

    @FXML
    public void handleAllStepsButton(MouseEvent mouseEvent) {
        int indexExample = exampleListView.getSelectionModel().getSelectedIndex();
        controllers.get(indexExample).allStepExecution();

        setupExample();
    }

    public void setupExample() {
        int index = exampleListView.getSelectionModel().getSelectedIndex();

        List<ProgramState> programStates = repositories.get(index).getProgramStateList();
        int nrOfProgramStates = programStates.size();
        boolean passedTypeChecking = typeCheckingResults.get(index);

        nrOfProgramStatesTextField.setText( Integer.toString( nrOfProgramStates ));
        if (nrOfProgramStates > 0) {
            setupHeap(programStates.get(0).getHeap());
            setupOutput(programStates.get(0).getOutput());
            setupFileTable(programStates.get(0).getFileTable());
            setupLockTable(programStates.get(0).getLockTable());

            setupProgramStateIdentifiers(programStates);

            int indexId = programStateIdentifierListView.getSelectionModel().getSelectedIndex();
            if (indexId < 0) {
                indexId = 0;
            }

            setupSymbolTable(programStates.get(indexId).getSymbolTable());
            setupExecutionStack(programStates.get(indexId).getExecutionStack());
        }
        else {
            heapTableView.getItems().clear();
            outListView.getItems().clear();
            fileTableListView.getItems().clear();
            lockTableTableView.getItems().clear();
            programStateIdentifierListView.getItems().clear();
            symbolTableTableView.getItems().clear();
            executionStackListView.getItems().clear();
        }

        if (nrOfProgramStates > 0 && passedTypeChecking) {
            oneStepButton.setDisable(false);
            allStepsButton.setDisable(false);
        }
        else {
            oneStepButton.setDisable(true);
            allStepsButton.setDisable(true);
        }
    }

    public void setupLockTable(LockTable lockTable){
        lockTableTableView.getItems().clear();

        Map<Integer, Integer> table = lockTable.getContent();
        for (Integer id: table.keySet()){
            lockTableTableView.getItems().add(new Pair<Integer, Integer> (id, table.get(id) ));
        }
    }

    public void setupProgramState(ProgramState programState) {
        setupSymbolTable(programState.getSymbolTable());
        setupExecutionStack(programState.getExecutionStack());
    }

    private void setupHeap(Heap heap) {
        heapTableView.getItems().clear();

        Map<Integer, Value> table = heap.getContent();
        for (Integer address : table.keySet()){
            heapTableView.getItems().add(new Pair<Integer, Value>(address, table.get(address)));
        }
    }

    private void setupOutput(Output output) {
        outListView.getItems().clear();

        List<Value> list = output.getContent();
        for (Value value: list) {
            outListView.getItems().add(value.toString());
        }
    }

    private void setupFileTable(FileTable fileTable) {
        fileTableListView.getItems().clear();

        Map<StringValue, BufferedReader> table = fileTable.getContent();
        for (StringValue value: table.keySet()) {
            fileTableListView.getItems().add(value.toString());
        }
    }

    private void setupProgramStateIdentifiers(List<ProgramState> programStates){
        programStateIdentifierListView.getItems().clear();

        for (ProgramState programState: programStates){
            programStateIdentifierListView.getItems().add(Integer.toString(programState.getStatementId()));
        }
    }

    private void setupSymbolTable(SymbolTable symbolTable){
        symbolTableTableView.getItems().clear();

        Map<String, Value> table = symbolTable.getContent();
        for (String id: table.keySet()){
            symbolTableTableView.getItems().add(new Pair<String, Value> (id, table.get(id)));
        }
    }

    private void setupExecutionStack(ExecutionStack executionStack) {
        executionStackListView.getItems().clear();

        Stack<InterfaceStatement> clone = (Stack<InterfaceStatement>) executionStack.getStack().clone();
        while ( !(clone.isEmpty()) ){
            InterfaceStatement top = clone.pop();
            executionStackListView.getItems().add(top.toString());
        }
    }

    public void setupExamples(){
        try {
            nrOfExamples = 15;

            // EXAMPLE 1: int v; v=2; Print(v)
            InterfaceStatement printStatement = new PrintStatement(new VariableExpression("v"));
            InterfaceStatement assignmentStatement = new AssignmentStatement("v", new ValueExpression(new IntegerValue(2)));
            InterfaceStatement variableDeclarationStatement = new VariableDeclarationStatement("v", new IntegerType());
            InterfaceStatement compoundStatement2 = new CompoundStatement(assignmentStatement, printStatement);
            InterfaceStatement compoundStatement = new CompoundStatement(variableDeclarationStatement, compoundStatement2);
            ProgramState programState = new ProgramState(compoundStatement);
            InterfaceRepository repository = new DefaultRepository(programState, "src/Files/log1.txt");
            Controller controller = new Controller(repository, true);
            controllers.add(controller);
            repositories.add(repository);
            exampleListView.getItems().add(compoundStatement.toString());



            // EXAMPLE 2: int a; int b; a=2+3*5; b=a+1; Print(b)
            InterfaceStatement printStatement_2 = new PrintStatement(new VariableExpression("b"));
            InterfaceStatement assignmentStatement2_2 = new AssignmentStatement("b",
                    new ArithmeticExpression("+", new VariableExpression("a"), new ValueExpression(new IntegerValue(1))));
            InterfaceStatement assignmentStatement_2 = new AssignmentStatement("a", new ArithmeticExpression("+", new ValueExpression(new IntegerValue(2)),
                    new ArithmeticExpression("*", new ValueExpression(new IntegerValue(3)), new ValueExpression((new IntegerValue(5))))));
            InterfaceStatement bDeclarationStatement_2 = new VariableDeclarationStatement("b", new IntegerType());
            InterfaceStatement aDeclarationStatement_2 = new VariableDeclarationStatement("a", new IntegerType());
            InterfaceStatement compoundStatement4_2 = new CompoundStatement(assignmentStatement2_2, printStatement_2);
            InterfaceStatement compoundStatement3_2 = new CompoundStatement(assignmentStatement_2, compoundStatement4_2);
            InterfaceStatement compoundStatement2_2 = new CompoundStatement(bDeclarationStatement_2, compoundStatement3_2);
            InterfaceStatement compoundStatement_2 = new CompoundStatement(aDeclarationStatement_2, compoundStatement2_2);
            ProgramState programState_2 = new ProgramState(compoundStatement_2);
            InterfaceRepository repository_2 = new DefaultRepository(programState_2, "src/Files/log2.txt");
            Controller controller_2 = new Controller(repository_2, true);
            controllers.add(controller_2);
            repositories.add(repository_2);
            exampleListView.getItems().add(compoundStatement_2.toString());



            // EXAMPLE 3: bool a; int v; a=true; (If a Then v=2 Else v=3); Print(v)
            InterfaceStatement printStatement_3 = new PrintStatement(new VariableExpression("v"));
            InterfaceStatement ifStatement_3 = new IfStatement(new VariableExpression("a"),
                    new AssignmentStatement("v", new ValueExpression(new IntegerValue(2))),
                    new AssignmentStatement("v", new ValueExpression(new IntegerValue(3))));
            InterfaceStatement assignmentStatement_3 = new AssignmentStatement("a", new ValueExpression(new BoolValue(true)));
            InterfaceStatement vDeclarationStatement_3 = new VariableDeclarationStatement("v", new IntegerType());
            InterfaceStatement aDeclarationStatement_3 = new VariableDeclarationStatement("a", new BoolType());
            InterfaceStatement compoundStatement4_3 = new CompoundStatement(ifStatement_3, printStatement_3);
            InterfaceStatement compoundStatement3_3 = new CompoundStatement(assignmentStatement_3, compoundStatement4_3);
            InterfaceStatement compoundStatement2_3 = new CompoundStatement(vDeclarationStatement_3, compoundStatement3_3);
            InterfaceStatement compoundStatement_3 = new CompoundStatement(aDeclarationStatement_3, compoundStatement2_3);
            ProgramState programState_3 = new ProgramState(compoundStatement_3);
            InterfaceRepository repository_3 = new DefaultRepository(programState_3, "src/Files/log3.txt");
            Controller controller_3 = new Controller(repository_3, true);
            controllers.add(controller_3);
            repositories.add(repository_3);
            exampleListView.getItems().add(compoundStatement_3.toString());



            // EXAMPLE 4
            InterfaceStatement varfDeclarationStatement_4 = new VariableDeclarationStatement("varf", new StringType());
            InterfaceStatement varfAssignmentStatement_4 = new AssignmentStatement("varf", new ValueExpression(new StringValue("src/Files/test.in")));
            InterfaceStatement openReadFileStatement_4 = new OpenReadFileStatement(new VariableExpression("varf"));
            InterfaceStatement varcDeclarationStatement_4 = new VariableDeclarationStatement("varc", new IntegerType());
            InterfaceStatement readFileStatement_4 = new ReadFileStatement(new VariableExpression("varf"), "varc");
            InterfaceStatement printStatement_4 = new PrintStatement(new VariableExpression("varc"));
            InterfaceStatement closeReadFileStatement_4 = new CloseReadFileStatement(new VariableExpression("varf"));
            InterfaceStatement compoundStatement8_4 = new CompoundStatement(printStatement_4, closeReadFileStatement_4);
            InterfaceStatement compoundStatement7_4 = new CompoundStatement(readFileStatement_4, compoundStatement8_4);
            InterfaceStatement compoundStatement6_4 = new CompoundStatement(printStatement_4, compoundStatement7_4);
            InterfaceStatement compoundStatement5_4 = new CompoundStatement(readFileStatement_4, compoundStatement6_4);
            InterfaceStatement compoundStatement4_4 = new CompoundStatement(varcDeclarationStatement_4, compoundStatement5_4);
            InterfaceStatement compoundStatement3_4 = new CompoundStatement(openReadFileStatement_4, compoundStatement4_4);
            InterfaceStatement compoundStatement2_4 = new CompoundStatement(varfAssignmentStatement_4, compoundStatement3_4);
            InterfaceStatement compoundStatement_4 = new CompoundStatement(varfDeclarationStatement_4, compoundStatement2_4);
            ProgramState programState_4 = new ProgramState(compoundStatement_4);
            InterfaceRepository repository_4 = new DefaultRepository(programState_4, "src/Files/log4.txt");
            Controller controller_4 = new Controller(repository_4, true);
            controllers.add(controller_4);
            repositories.add(repository_4);
            exampleListView.getItems().add(compoundStatement_4.toString());



            //EXAMPLE 5: bool a; a = 9 > 5; print(a)
            InterfaceStatement aDeclarationStatement_5 = new VariableDeclarationStatement("a", new BoolType());
            InterfaceStatement assignmentStatement_5 = new AssignmentStatement("a", new RelationalExpression(">",
                    new ValueExpression(new IntegerValue(9)), new ValueExpression(new IntegerValue(5))));
            InterfaceStatement printStatement_5 = new PrintStatement(new VariableExpression("a"));
            InterfaceStatement compoundStatement2_5 = new CompoundStatement(assignmentStatement_5, printStatement_5);
            InterfaceStatement compoundStatement_5 = new CompoundStatement(aDeclarationStatement_5, compoundStatement2_5);
            ProgramState programState_5 = new ProgramState(compoundStatement_5);
            InterfaceRepository repository_5 = new DefaultRepository(programState_5, "src/Files/log5.txt");
            Controller controller_5 = new Controller(repository_5, true);
            controllers.add(controller_5);
            repositories.add(repository_5);
            exampleListView.getItems().add(compoundStatement_5.toString());



            //EXAMPLE 6: Ref int v; new(v,20); Ref Ref int a; new(a,v); print(v); print(a)
            InterfaceStatement vDeclarationStatement_6 = new VariableDeclarationStatement("v", new ReferenceType(new IntegerType()));
            InterfaceStatement vHeapAllocationStatement_6 = new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20)));
            InterfaceStatement aDeclarationStatement_6 = new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType())));
            InterfaceStatement aHeapDeclarationStatement_6 = new HeapAllocationStatement("a", new VariableExpression("v"));
            InterfaceStatement vPrintStatement_6 = new PrintStatement(new VariableExpression("v"));
            InterfaceStatement aPrintStatement_6 = new PrintStatement(new VariableExpression("a"));
            InterfaceStatement compoundStatement5_6 = new CompoundStatement(vPrintStatement_6, aPrintStatement_6);
            InterfaceStatement compoundStatement4_6 = new CompoundStatement(aHeapDeclarationStatement_6, compoundStatement5_6);
            InterfaceStatement compoundStatement3_6 = new CompoundStatement(aDeclarationStatement_6, compoundStatement4_6);
            InterfaceStatement compoundStatement2_6 = new CompoundStatement(vHeapAllocationStatement_6, compoundStatement3_6);
            InterfaceStatement compoundStatement_6 = new CompoundStatement(vDeclarationStatement_6, compoundStatement2_6);
            ProgramState programState_6 = new ProgramState(compoundStatement_6);
            InterfaceRepository repository_6 = new DefaultRepository(programState_6, "src/Files/log6.txt");
            Controller controller_6 = new Controller(repository_6, true);
            controllers.add(controller_6);
            repositories.add(repository_6);
            exampleListView.getItems().add(compoundStatement_6.toString());



            //EXAMPLE 7: Ref int v; new(v,20); Ref Ref int a; new(a,v); print(rH(v)); print(rH(rH(a))+5)
            InterfaceStatement vDeclarationStatement_7 = new VariableDeclarationStatement("v", new ReferenceType(new IntegerType()));
            InterfaceStatement vHeapAllocationStatement_7 = new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20)));
            InterfaceStatement aDeclarationStatement_7 = new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType())));
            InterfaceStatement aHeapDeclarationStatement_7 = new HeapAllocationStatement("a", new VariableExpression("v"));
            InterfaceStatement vPrintStatement_7 = new PrintStatement(new HeapReadingExpression(new VariableExpression("v")));
            InterfaceStatement aPrintStatement_7 = new PrintStatement(new ArithmeticExpression("+",
                    new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))),
                    new ValueExpression(new IntegerValue(5))));
            InterfaceStatement compoundStatement5_7 = new CompoundStatement(vPrintStatement_7, aPrintStatement_7);
            InterfaceStatement compoundStatement4_7 = new CompoundStatement(aHeapDeclarationStatement_7, compoundStatement5_7);
            InterfaceStatement compoundStatement3_7 = new CompoundStatement(aDeclarationStatement_7, compoundStatement4_7);
            InterfaceStatement compoundStatement2_7 = new CompoundStatement(vHeapAllocationStatement_7, compoundStatement3_7);
            InterfaceStatement compoundStatement_7 = new CompoundStatement(vDeclarationStatement_7, compoundStatement2_7);
            ProgramState programState_7 = new ProgramState(compoundStatement_7);
            InterfaceRepository repository_7 = new DefaultRepository(programState_7, "src/Files/log7.txt");
            Controller controller_7 = new Controller(repository_7, true);
            controllers.add(controller_7);
            repositories.add(repository_7);
            exampleListView.getItems().add(compoundStatement_7.toString());



            //EXAMPLE 8: Ref int v; new(v,20); print(rH(v)); wH(v,30); print(rH(v)+5)
            InterfaceStatement declarationStatement_8 = new VariableDeclarationStatement("v", new ReferenceType(new IntegerType()));
            InterfaceStatement heapAllocationStatement_8 = new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20)));
            InterfaceStatement printStatement_8 = new PrintStatement(new HeapReadingExpression(new VariableExpression("v")));
            InterfaceStatement heapWritingStatement_8 = new HeapWritingStatement("v", new ValueExpression(new IntegerValue(30)));
            InterfaceStatement printStatement2_8 = new PrintStatement(new ArithmeticExpression("+",
                    new HeapReadingExpression(new VariableExpression("v")),
                    new ValueExpression(new IntegerValue(5))));
            InterfaceStatement compoundStatement4_8 = new CompoundStatement(heapWritingStatement_8, printStatement2_8);
            InterfaceStatement compoundStatement3_8 = new CompoundStatement(printStatement_8, compoundStatement4_8);
            InterfaceStatement compoundStatement2_8 = new CompoundStatement(heapAllocationStatement_8, compoundStatement3_8);
            InterfaceStatement compoundStatement_8 = new CompoundStatement(declarationStatement_8, compoundStatement2_8);
            ProgramState programState_8 = new ProgramState(compoundStatement_8);
            InterfaceRepository repository_8 = new DefaultRepository(programState_8, "src/Files/log8.txt");
            Controller controller_8 = new Controller(repository_8, true);
            controllers.add(controller_8);
            repositories.add(repository_8);
            exampleListView.getItems().add(compoundStatement_8.toString());



            //EXAMPLE 9: Ref int v; new(v,20); Ref Ref int a; new(a,v); new(v,30); print(rH(rH(a)))
            InterfaceStatement vDeclarationStatement_9 = new VariableDeclarationStatement("v", new ReferenceType(new IntegerType()));
            InterfaceStatement vHeapAllocationStatement_9 = new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(20)));
            InterfaceStatement aDeclarationStatement_9 = new VariableDeclarationStatement("a", new ReferenceType(new ReferenceType(new IntegerType())));
            InterfaceStatement aHeapDeclarationStatement_9 = new HeapAllocationStatement("a", new VariableExpression("v"));
            InterfaceStatement vHeapAllocationStatement2_9 = new HeapAllocationStatement("v", new ValueExpression(new IntegerValue(30)));
            InterfaceStatement printStatement_9 = new PrintStatement(new HeapReadingExpression(new HeapReadingExpression(new VariableExpression("a"))));
            InterfaceStatement compoundStatement5_9 = new CompoundStatement(vHeapAllocationStatement2_9, printStatement_9);
            InterfaceStatement compoundStatement4_9 = new CompoundStatement(aHeapDeclarationStatement_9, compoundStatement5_9);
            InterfaceStatement compoundStatement3_9 = new CompoundStatement(aDeclarationStatement_9, compoundStatement4_9);
            InterfaceStatement compoundStatement2_9 = new CompoundStatement(vHeapAllocationStatement_9, compoundStatement3_9);
            InterfaceStatement compoundStatement_9 = new CompoundStatement(vDeclarationStatement_9, compoundStatement2_9);
            ProgramState programState_9 = new ProgramState(compoundStatement_9);
            InterfaceRepository repository_9 = new DefaultRepository(programState_9, "src/Files/log9.txt");
            Controller controller_9 = new Controller(repository_9, true);
            controllers.add(controller_9);
            repositories.add(repository_9);
            exampleListView.getItems().add(compoundStatement_9.toString());



            //EXAMPLE 10: int v; v=4; (while (v>0) print(v); v=v-1); print(v)
            InterfaceStatement vDeclarationStatement_10 = new VariableDeclarationStatement("v", new IntegerType());
            InterfaceStatement assignmentStatement_10 = new AssignmentStatement("v", new ValueExpression(new IntegerValue(4)));
            InterfaceStatement whileStatement_10 = new WhileStatement(
                    new RelationalExpression(">", new VariableExpression("v"), new ValueExpression(new IntegerValue(0))),
                    new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                            new AssignmentStatement("v", new ArithmeticExpression("-", new VariableExpression("v"), new ValueExpression(new IntegerValue(1)))))
            );
            InterfaceStatement printStatement_10 = new PrintStatement(new VariableExpression("v"));
            InterfaceStatement compoundStatement3_10 = new CompoundStatement(whileStatement_10, printStatement_10);
            InterfaceStatement compoundStatement2_10 = new CompoundStatement(assignmentStatement_10, compoundStatement3_10);
            InterfaceStatement compoundStatement_10 = new CompoundStatement(vDeclarationStatement_10, compoundStatement2_10);
            ProgramState programState_10 = new ProgramState(compoundStatement_10);
            InterfaceRepository repository_10 = new DefaultRepository(programState_10, "src/Files/log10.txt");
            Controller controller_10 = new Controller(repository_10, true);
            controllers.add(controller_10);
            repositories.add(repository_10);
            exampleListView.getItems().add(compoundStatement_10.toString());



            // EXAMPLE 11: int v; Ref int a; v=10; new(a,22);
            //             fork(wH(a,30); v=32; print(v); print(rH(a)));
            //             print(v); print(rH(a))
            InterfaceStatement vDeclarationStatement_11 = new VariableDeclarationStatement("v", new IntegerType());
            InterfaceStatement aDeclarationStatement_11 = new VariableDeclarationStatement("a", new ReferenceType(new IntegerType()));
            InterfaceStatement vAssignmentStatement_11 = new AssignmentStatement("v", new ValueExpression(new IntegerValue(10)));
            InterfaceStatement aHeapAllocationStatement_11 = new HeapAllocationStatement("a", new ValueExpression(new IntegerValue(22)));
            InterfaceStatement forkStatement_11 = new ForkStatement(
                    new CompoundStatement(new HeapWritingStatement("a", new ValueExpression(new IntegerValue(30))),
                            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntegerValue(32))),
                                    new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                            new PrintStatement(new HeapReadingExpression(new VariableExpression("a")))
                                    )
                            )
                    )
            );
            InterfaceStatement vPrintStatement_11 = new PrintStatement(new VariableExpression("v"));
            InterfaceStatement aPrintStatement_11 = new PrintStatement(new HeapReadingExpression(new VariableExpression("a")));
            InterfaceStatement compoundStatement6_11 = new CompoundStatement(vPrintStatement_11, aPrintStatement_11);
            InterfaceStatement compoundStatement5_11 = new CompoundStatement(forkStatement_11, compoundStatement6_11);
            InterfaceStatement compoundStatement4_11 = new CompoundStatement(aHeapAllocationStatement_11, compoundStatement5_11);
            InterfaceStatement compoundStatement3_11 = new CompoundStatement(vAssignmentStatement_11, compoundStatement4_11);
            InterfaceStatement compoundStatement2_11 = new CompoundStatement(aDeclarationStatement_11, compoundStatement3_11);
            InterfaceStatement compoundStatement_11 = new CompoundStatement(vDeclarationStatement_11, compoundStatement2_11);
            ProgramState programState_11 = new ProgramState(compoundStatement_11);
            InterfaceRepository repository_11 = new DefaultRepository(programState_11, "src/Files/log11.txt");
            Controller controller_11 = new Controller(repository_11, true);
            controllers.add(controller_11);
            repositories.add(repository_11);
            exampleListView.getItems().add(compoundStatement_11.toString());



            // EXAMPLE 12: int a; (if (a) (a = 5) a = 10)
            InterfaceStatement variableDeclarationStatement_12 = new VariableDeclarationStatement("a", new IntegerType());
            InterfaceStatement ifStatement_12 = new IfStatement(new VariableExpression("a"),
                    new AssignmentStatement("a", new ValueExpression(new IntegerValue(5))),
                    new AssignmentStatement("a", new ValueExpression(new IntegerValue(10))));
            InterfaceStatement compoundStatement_12 = new CompoundStatement(variableDeclarationStatement_12, ifStatement_12);
            ProgramState programState_12 = new ProgramState(compoundStatement_12);
            InterfaceRepository repository_12 = new DefaultRepository(programState_12, "src/Files/log12.txt");
            Controller controller_12 = new Controller(repository_12, true);
            controllers.add(controller_12);
            repositories.add(repository_12);
            exampleListView.getItems().add(compoundStatement_12.toString());



            // EXAMPLE 13: int v; v=20; wait(10);print(v*10)
            InterfaceStatement variableDeclarationStatement_13 = new VariableDeclarationStatement("v", new IntegerType());
            InterfaceStatement assignmentStatement_13 = new AssignmentStatement("v", new ValueExpression(new IntegerValue(20)));
            InterfaceStatement waitStatement_13 = new WaitStatement(new ValueExpression(new IntegerValue(10)));
            InterfaceStatement printStatement_13 = new PrintStatement(
                    new ArithmeticExpression("*", new VariableExpression("v"), new ValueExpression(new IntegerValue(10)))
                );
            InterfaceStatement compoundStatement3_13 = new CompoundStatement(waitStatement_13, printStatement_13);
            InterfaceStatement compoundStatement2_13 = new CompoundStatement(assignmentStatement_13, compoundStatement3_13);
            InterfaceStatement compoundStatement_13 = new CompoundStatement(variableDeclarationStatement_13, compoundStatement2_13);
            ProgramState programState_13 = new ProgramState(compoundStatement_13);
            InterfaceRepository repository_13 = new DefaultRepository(programState_13, "src/Files/log13.txt");
            Controller controller_13 = new Controller(repository_13, true);
            controllers.add(controller_13);
            repositories.add(repository_13);
            exampleListView.getItems().add(compoundStatement_13.toString());



            // EXAMPLE 14:
            //
            // Ref int v1; Ref int v2; int x; int q;
            // new(v1,20);new(v2,30);newLock(x);
            // fork(
            //    fork(lock(x);wh(v1,rh(v1)-1);unlock(x));
            //    lock(x);wh(v1,rh(v1)*10);unlock(x)
            // );
            // newLock(q);
            // fork(
            //    fork(lock(q);wh(v2,rh(v2)+5);unlock(q));
            //    lock(q);wh(v2,rh(v2)*10);unlock(q)
            // );
            // nop;nop;nop;nop;
            // lock(x); print(rh(v1)); unlock(x);
            // lock(q); print(rh(v2)); unlock(q);

            InterfaceStatement v1DeclarationStatement_14 = new VariableDeclarationStatement("v1", new ReferenceType(new IntegerType()));
            InterfaceStatement v2DeclarationStatement_14 = new VariableDeclarationStatement("v2", new ReferenceType(new IntegerType()));
            InterfaceStatement xDeclarationStatement_14 = new VariableDeclarationStatement("x", new IntegerType());
            InterfaceStatement qDeclarationStatement_14 = new VariableDeclarationStatement("q", new IntegerType());
            InterfaceStatement v1HeapAllocationStatement_14 = new HeapAllocationStatement("v1", new ValueExpression(new IntegerValue(20)));
            InterfaceStatement v2HeapAllocationStatement_14 = new HeapAllocationStatement("v2", new ValueExpression(new IntegerValue(30)));
            InterfaceStatement xNewLockStatement_14 = new NewLockStatement("x");
            InterfaceStatement forkStatement_14 = new ForkStatement(
                    new CompoundStatement(
                        new ForkStatement(
                                new CompoundStatement(
                                        new LockStatement("x"),
                                new CompoundStatement(
                                        new HeapWritingStatement("v1",
                                                new ArithmeticExpression("-", new HeapReadingExpression(new VariableExpression("v1")), new ValueExpression(new IntegerValue(1)))),
                                        new UnlockStatement("x")
                                ))
                        ),
                            new CompoundStatement(
                                    new LockStatement("x"),
                                    new CompoundStatement(
                                            new HeapWritingStatement("v1",
                                                    new ArithmeticExpression("*", new HeapReadingExpression(new VariableExpression("v1")), new ValueExpression(new IntegerValue(10)))),
                                            new UnlockStatement("x")
                                    ))
                    )
            );
            InterfaceStatement qNewLockStatement_14 = new NewLockStatement("q");
            InterfaceStatement forkStatement2_14 = new ForkStatement(
                    new CompoundStatement(
                            new ForkStatement(
                                    new CompoundStatement(
                                            new LockStatement("q"),
                                            new CompoundStatement(
                                                    new HeapWritingStatement("v2",
                                                            new ArithmeticExpression("+", new HeapReadingExpression(new VariableExpression("v2")), new ValueExpression(new IntegerValue(5)))),
                                                    new UnlockStatement("q")
                                            ))
                            ),
                            new CompoundStatement(
                                    new LockStatement("q"),
                                    new CompoundStatement(
                                            new HeapWritingStatement("v2",
                                                    new ArithmeticExpression("*", new HeapReadingExpression(new VariableExpression("v2")), new ValueExpression(new IntegerValue(10)))),
                                            new UnlockStatement("q")
                                    ))
                    )
            );
            InterfaceStatement noOperationStatements_14 = new CompoundStatement(
                    new NoOperationStatement(null),
                    new CompoundStatement(
                            new NoOperationStatement(null),
                            new CompoundStatement(
                                    new NoOperationStatement(null),
                                    new NoOperationStatement(null)
                                    )
                            )
                    );
            InterfaceStatement compoundStatement12_1_14 = new CompoundStatement(
                    new LockStatement("x"),
                    new CompoundStatement(
                            new PrintStatement(new HeapReadingExpression(new VariableExpression("v1"))),
                            new UnlockStatement("x")
                    )
            );
            InterfaceStatement compoundStatement12_2_14 = new CompoundStatement(
                    new LockStatement("q"),
                    new CompoundStatement(
                            new PrintStatement(new HeapReadingExpression(new VariableExpression("v2"))),
                            new UnlockStatement("q")
                    )
            );
            InterfaceStatement compoundStatement12_14 = new CompoundStatement(compoundStatement12_1_14, compoundStatement12_2_14);
            InterfaceStatement compoundStatement11_14 = new CompoundStatement(noOperationStatements_14, compoundStatement12_14);
            InterfaceStatement compoundStatement10_14 = new CompoundStatement(forkStatement2_14, compoundStatement11_14);
            InterfaceStatement compoundStatement9_14 = new CompoundStatement(qNewLockStatement_14, compoundStatement10_14);
            InterfaceStatement compoundStatement8_14 = new CompoundStatement(forkStatement_14, compoundStatement9_14);
            InterfaceStatement compoundStatement7_14 = new CompoundStatement(xNewLockStatement_14, compoundStatement8_14);
            InterfaceStatement compoundStatement6_14 = new CompoundStatement(v2HeapAllocationStatement_14, compoundStatement7_14);
            InterfaceStatement compoundStatement5_14 = new CompoundStatement(v1HeapAllocationStatement_14, compoundStatement6_14);
            InterfaceStatement compoundStatement4_14 = new CompoundStatement(qDeclarationStatement_14, compoundStatement5_14);
            InterfaceStatement compoundStatement3_14 = new CompoundStatement(xDeclarationStatement_14, compoundStatement4_14);
            InterfaceStatement compoundStatement2_14 = new CompoundStatement(v2DeclarationStatement_14, compoundStatement3_14);
            InterfaceStatement compoundStatement_14 = new CompoundStatement(v1DeclarationStatement_14, compoundStatement2_14);
            ProgramState programState_14 = new ProgramState(compoundStatement_14);
            InterfaceRepository repository_14 = new DefaultRepository(programState_14, "src/Files/log14.txt");
            Controller controller_14 = new Controller(repository_14, true);
            controllers.add(controller_14);
            repositories.add(repository_14);
            exampleListView.getItems().add(compoundStatement_14.toString());



            // EXAMPLE 15: Ref int a; new(a,20); int v;
            //             (for(v=0;v<3;v=v+1) fork(print(v);v=v*rh(a)));
            //             print(rh(a))
            InterfaceStatement aDeclarationStatement_15 = new VariableDeclarationStatement("a", new ReferenceType(new IntegerType()));
            InterfaceStatement aHeapAllocationStatement_15 = new HeapAllocationStatement("a", new ValueExpression(new IntegerValue(20)));
            InterfaceStatement vDeclarationStatement_15 = new VariableDeclarationStatement("v", new IntegerType());
            InterfaceStatement forStatement_15 = new ForStatement("v",
                    new ValueExpression(new IntegerValue(0)),
                    new ValueExpression(new IntegerValue(3)),
                    new ArithmeticExpression("+", new VariableExpression("v"), new ValueExpression(new IntegerValue(1))),
                    new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                                            new AssignmentStatement("v", new ArithmeticExpression("*", new VariableExpression("v"), new HeapReadingExpression(new VariableExpression("a"))))))
            );
            InterfaceStatement aPrintStatement_15 = new PrintStatement(new HeapReadingExpression(new VariableExpression("a")));
            InterfaceStatement compoundStatement4_15 = new CompoundStatement(forStatement_15, aPrintStatement_15);
            InterfaceStatement compoundStatement3_15 = new CompoundStatement(vDeclarationStatement_15, compoundStatement4_15);
            InterfaceStatement compoundStatement2_15 = new CompoundStatement(aHeapAllocationStatement_15, compoundStatement3_15);
            InterfaceStatement compoundStatement_15 = new CompoundStatement(aDeclarationStatement_15, compoundStatement2_15);
            ProgramState programState_15 = new ProgramState(compoundStatement_15);
            InterfaceRepository repository_15 = new DefaultRepository(programState_15, "src/Files/log14.txt");
            Controller controller_15 = new Controller(repository_15, true);
            controllers.add(controller_15);
            repositories.add(repository_15);
            exampleListView.getItems().add(compoundStatement_15.toString());

        }
        catch (MyException exception) {
            exception.printStackTrace();
        }
    }

    public void setupLogFiles() {
        try {
            PrintWriter writer;
            for (int nrOfLogFile = 1; nrOfLogFile <= nrOfExamples; nrOfLogFile++) {
                writer = new PrintWriter("src/Files/log" + Integer.toString(nrOfLogFile) + ".txt");
                writer.write("");
                writer.close();
            }
        }
        catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public void setupTypeChecking() {

        for (int index = 0; index < nrOfExamples; index++) {
            System.out.println("TYPE CHECKING " + Integer.toString(index + 1));

            try {
                ProgramState programState = repositories.get(index)
                        .getProgramStateList().get(0);
                controllers.get(index).typeCheckingForProgramState(programState);
                typeCheckingResults.add(true);

                // log type environment
                PrintWriter writer = new PrintWriter("src/Files/log" + Integer.toString(index + 1) + ".txt");
                writer.write(programState.getTypeEnvironment().toString());
                writer.close();
            }
            catch (MyException exception) {
                System.out.println("*failed*");

                exampleListView.getItems().set(index, "FAILED TYPE CHECK: " + exampleListView.getItems().get(index));
                typeCheckingResults.add(false);
            }
            catch (FileNotFoundException exception) {
                exception.printStackTrace();
            }
        }
    }
}
