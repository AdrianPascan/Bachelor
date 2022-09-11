package View;

import Commands.ExitCommand;
import Commands.RunExampleCommand;
import Controller.Controller;
import Controller.InterfaceController;
import Exceptions.MyException;
import Menus.TextMenu;
import Model.Expressions.*;
import Model.ProgramState.ProgramState;
import Model.Statements.*;
import Model.Types.BoolType;
import Model.Types.IntegerType;
import Model.Types.ReferenceType;
import Model.Types.StringType;
import Model.Values.BoolValue;
import Model.Values.IntegerValue;
import Model.Values.StringValue;
import Repository.DefaultRepository;
import Repository.InterfaceRepository;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleUI implements InterfaceUI {
    @Override
    public void start() {
        try {
            // EXAMPLE 1: int v; v=2; Print(v)
            InterfaceStatement printStatement = new PrintStatement(new VariableExpression("v"));
            InterfaceStatement assignmentStatement = new AssignmentStatement("v", new ValueExpression(new IntegerValue(2)));
            InterfaceStatement variableDeclarationStatement = new VariableDeclarationStatement("v", new IntegerType());
            InterfaceStatement compoundStatement2 = new CompoundStatement(assignmentStatement, printStatement);
            InterfaceStatement compoundStatement = new CompoundStatement(variableDeclarationStatement, compoundStatement2);
            ProgramState programState = new ProgramState(compoundStatement);
            InterfaceRepository repository = new DefaultRepository(programState, "src/Files/log1.txt");
            InterfaceController controller = new Controller(repository, true);


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
            InterfaceController controller_2 = new Controller(repository_2, true);


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
            InterfaceController controller_3 = new Controller(repository_3, true);


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
            InterfaceController controller_4 = new Controller(repository_4, true);


            //EXAMPLE 5: bool a; a = 9 > 5; print(a)
            InterfaceStatement aDeclarationStatement_5 = new VariableDeclarationStatement("a", new BoolType());
            InterfaceStatement assignmentStatement_5 = new AssignmentStatement("a", new RelationalExpression(">",
                    new ValueExpression(new IntegerValue(9)), new ValueExpression(new IntegerValue(5))));
            InterfaceStatement printStatement_5 = new PrintStatement(new VariableExpression("a"));
            InterfaceStatement compoundStatement2_5 = new CompoundStatement(assignmentStatement_5, printStatement_5);
            InterfaceStatement compoundStatement_5 = new CompoundStatement(aDeclarationStatement_5, compoundStatement2_5);
            ProgramState programState_5 = new ProgramState(compoundStatement_5);
            InterfaceRepository repository_5 = new DefaultRepository(programState_5, "src/Files/log5.txt");
            InterfaceController controller_5 = new Controller(repository_5, true);


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
            InterfaceController controller_6 = new Controller(repository_6, true);


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
            InterfaceController controller_7 = new Controller(repository_7, true);


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
            InterfaceController controller_8 = new Controller(repository_8, true);


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
            InterfaceController controller_9 = new Controller(repository_9, true);


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
            InterfaceController controller_10 = new Controller(repository_10, true);


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
            InterfaceController controller_11 = new Controller(repository_11, true);


            // EXAMPLE 12: int a; (if (a) (a = 5) a = 10)
            InterfaceStatement variableDeclarationStatement_12 = new VariableDeclarationStatement("a", new IntegerType());
            InterfaceStatement ifStatement_12 = new IfStatement(new VariableExpression("a"),
                    new AssignmentStatement("a", new ValueExpression(new IntegerValue(5))),
                    new AssignmentStatement("a", new ValueExpression(new IntegerValue(10))));
            InterfaceStatement compoundStatement_12 = new CompoundStatement(variableDeclarationStatement_12, ifStatement_12);
            ProgramState programState_12 = new ProgramState(compoundStatement_12);
            InterfaceRepository repository_12 = new DefaultRepository(programState_12, "src/Files/log12.txt");
            InterfaceController controller_12 = new Controller(repository_12, true);


            PrintWriter writer;
            writer = new PrintWriter("src/Files/log1.txt");
            writer.write("");
            writer.close();
            writer = new PrintWriter("src/Files/log2.txt");
            writer.write("");
            writer.close();
            writer = new PrintWriter("src/Files/log3.txt");
            writer.write("");
            writer.close();
            writer = new PrintWriter("src/Files/log4.txt");
            writer.write("");
            writer.close();
            writer = new PrintWriter("src/Files/log5.txt");
            writer.write("");
            writer.close();
            writer = new PrintWriter("src/Files/log6.txt");
            writer.write("");
            writer.close();
            writer = new PrintWriter("src/Files/log7.txt");
            writer.write("");
            writer.close();
            writer = new PrintWriter("src/Files/log8.txt");
            writer.write("");
            writer.close();
            writer = new PrintWriter("src/Files/log9.txt");
            writer.write("");
            writer.close();
            writer = new PrintWriter("src/Files/log10.txt");
            writer.write("");
            writer.close();
            writer = new PrintWriter("src/Files/log11.txt");
            writer.write("");
            writer.close();
            writer = new PrintWriter("src/Files/log12.txt");
            writer.write("");
            writer.close();

            TextMenu menu = new TextMenu();
            menu.addCommand(new ExitCommand("0", "EXIT"));
            menu.addCommand(new RunExampleCommand("1", compoundStatement.toString(), controller));
            menu.addCommand(new RunExampleCommand("2", compoundStatement_2.toString(), controller_2));
            menu.addCommand(new RunExampleCommand("3", compoundStatement_3.toString(), controller_3));
            menu.addCommand(new RunExampleCommand("4", compoundStatement_4.toString(), controller_4));
            menu.addCommand(new RunExampleCommand("5", compoundStatement_5.toString(), controller_5));
            menu.addCommand(new RunExampleCommand("6", compoundStatement_6.toString(), controller_6));
            menu.addCommand(new RunExampleCommand("7", compoundStatement_7.toString(), controller_7));
            menu.addCommand(new RunExampleCommand("8", compoundStatement_8.toString(), controller_8));
            menu.addCommand(new RunExampleCommand("9", compoundStatement_9.toString(), controller_9));
            menu.addCommand(new RunExampleCommand("10", compoundStatement_10.toString(), controller_10));
            menu.addCommand(new RunExampleCommand("11", compoundStatement_11.toString(), controller_11));
            menu.addCommand(new RunExampleCommand("12", compoundStatement_12.toString(), controller_12));
            menu.show();
        } catch (MyException | FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }
    }
}
