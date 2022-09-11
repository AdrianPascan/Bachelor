package Model.Expressions;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.ProgramState.Heap;
import Model.ProgramState.SymbolTable;
import Model.Types.BoolType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.Value;

public class LogicExpression implements InterfaceExpression {
    private InterfaceExpression expression1;
    private InterfaceExpression expression2;
    private String operation;

    public LogicExpression(String operation, InterfaceExpression expression1, InterfaceExpression expression2) throws MyException {
        this.expression1 = expression1;
        this.expression2 = expression2;
        if (operation.equals("and") || operation.equals("or")){
            this.operation = operation;
        }
        else{
            throw new MyException("Invalid operand for logic expression.");
        }
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws MyException {
        Value value1 = expression1.evaluate(symbolTable, heap);
        if (value1.getType().equals(new BoolType())){
            Value value2 = expression2.evaluate(symbolTable, heap);
            if (value2.getType().equals(new BoolType())){
                boolean boolean1 = ((BoolValue) value1).getValue();
                boolean boolean2 = ((BoolValue) value2).getValue();
                switch (operation){
                    case "and":
                        return new BoolValue(boolean1 && boolean2);
                    case "or":
                        return new BoolValue(boolean1 || boolean2);
                    default:
                        throw new MyException("Invalid operand for logic expression.");
                }
            }
            else{
                throw new MyException("Second argument is not a boolean in logic expression.");
            }
        }
        else {
            throw new MyException("First operand is not a boolean in logic expression");
        }
    }

    @Override
    public Type typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        Type type1 = expression1.typeCheck(typeEnvironment);
        Type type2 = expression2.typeCheck(typeEnvironment);

        if (type1.equals(new BoolType())) {
            if (type2.equals(new BoolType())) {
                return new BoolType();
            }
            else {
                throw new MyException("Second operand of logic expression is not of boolean.");
            }
        }
        else {
            throw new MyException("First operand of logic expression is not of boolean.");
        }
    }

    @Override
    public String toString() {
        return expression1.toString() + " " + operation + " " + expression2.toString();
    }
}
