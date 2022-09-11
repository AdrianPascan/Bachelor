package Model.Statements;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.Expressions.InterfaceExpression;
import Model.ProgramState.Output;
import Model.ProgramState.ProgramState;
import Model.Types.Type;

public class PrintStatement implements InterfaceStatement {
    private InterfaceExpression expression;

    public PrintStatement(InterfaceExpression expression){
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        Output outputList = programState.getOutput();
        outputList.add(expression.evaluate(programState.getSymbolTable(), programState.getHeap()));
        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        expression.typeCheck(typeEnvironment);
        return typeEnvironment;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }
}
