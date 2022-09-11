package Model.Statements;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.Expressions.InterfaceExpression;
import Model.Expressions.RelationalExpression;
import Model.Expressions.VariableExpression;
import Model.ProgramState.ExecutionStack;
import Model.ProgramState.ProgramState;
import Model.Types.IntegerType;
import Model.Types.Type;
import Model.Values.Value;

public class ForStatement implements InterfaceStatement {
    private String id;
    private InterfaceExpression expression;
    private InterfaceExpression expression2;
    private InterfaceExpression expression3;
    InterfaceStatement statement;

    public ForStatement(String id, InterfaceExpression expression, InterfaceExpression expression2, InterfaceExpression expression3, InterfaceStatement statement){
        this.id = id;
        this.expression = expression;
        this.expression2 = expression2;
        this.expression3 = expression3;
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState programState) throws MyException {
        ExecutionStack executionStack = programState.getExecutionStack();

        InterfaceStatement declarationStatement = new VariableDeclarationStatement(id, new IntegerType());
        InterfaceStatement assignmentStatement = new AssignmentStatement(id, expression);
        InterfaceStatement whileStatement = new WhileStatement(
                new RelationalExpression("<", new VariableExpression(id), expression2),
                new CompoundStatement(statement, new AssignmentStatement(id, expression3))
                );

        executionStack.push(whileStatement);
        executionStack.push(assignmentStatement);
        executionStack.push(declarationStatement);

        return null;
    }

    @Override
    public MyDictionaryInterface<String, Type> typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        Type type = expression.typeCheck(typeEnvironment);
        if (type.equals(new IntegerType())){
            Type type2 = expression2.typeCheck(typeEnvironment);
            if (type2.equals(new IntegerType())){
                Type type3 = expression3.typeCheck(typeEnvironment);
                if(type3.equals(new IntegerType())){
                    return typeEnvironment;
                }
                else {
                    throw new MyException("Reassignment expression of for statement does not evaluate to int.");
                }
            }
            else {
                throw new MyException("Conditional expression of for statement does not evaluate to int.");
            }
        }
        else {
            throw new MyException("Initialisation expression of for statement does not evaluate to int.");
        }
    }

    @Override
    public String toString() {
        return "(for(" + id + "=" + expression.toString() + "; " + id + "<" + expression2.toString() + "; " + id + "=" + expression3.toString() + ") " + statement.toString();
    }
}
