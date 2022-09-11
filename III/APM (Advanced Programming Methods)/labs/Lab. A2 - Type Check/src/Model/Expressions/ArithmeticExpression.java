package Model.Expressions;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.ProgramState.Heap;
import Model.ProgramState.SymbolTable;
import Model.Types.IntegerType;
import Model.Types.Type;
import Model.Values.IntegerValue;
import Model.Values.Value;

public class ArithmeticExpression implements InterfaceExpression {
    private InterfaceExpression expression1;
    private InterfaceExpression expression2;
    private String operation;

    public ArithmeticExpression(String operation, InterfaceExpression expression1, InterfaceExpression expression2) throws MyException{
        this.expression1 = expression1;
        this.expression2 = expression2;
        if (operation.equals("+") || operation.equals("-") || operation.equals("*") || operation.equals("/")) {
            this.operation = operation;
        }
        else {
            throw new MyException("Invalid operation for arithmetic expression.");
        }
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws MyException{
        Value value1 = expression1.evaluate(symbolTable, heap);
        if (value1.getType().equals(new IntegerType())){
            Value value2 = expression2.evaluate(symbolTable, heap);
            if (value2.getType().equals(new IntegerType())){
                int integer1 = ((IntegerValue) value1).getValue();
                int integer2 = ((IntegerValue) value2).getValue();
                switch(operation){
                    case "+":
                        return new IntegerValue(integer1 + integer2);
                    case "-":
                        return new IntegerValue(integer1 - integer2);
                    case "*":
                        return new IntegerValue(integer1 * integer2);
                    case "/":
                        if (integer2 == 0){
                            throw new MyException("Division by zero.");
                        }
                        return new IntegerValue(integer1 / integer2);
                    default:
                        throw new MyException("Invalid operand for arithmetic expression.");
                }
            }
            else{
                throw new MyException("Second operand is not an integer in arithmetic expression.");
            }
        }
        else {
            throw new MyException("First operand is not an integer in arithmetic expression.");
        }
    }

    @Override
    public Type typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        Type type1 = expression1.typeCheck(typeEnvironment);
        Type type2 = expression2.typeCheck(typeEnvironment);

        if (type1.equals(new IntegerType())){
            if (type2.equals(new IntegerType())) {
                return new IntegerType();
            }
            else{
                throw new MyException("Second operand of arithmetic expression is not an integer.");
            }
        }
        else {
            throw new MyException("First operand of arithmetic expression is not an integer.");
        }
    }

    @Override
    public String toString() {
        return expression1.toString() + operation + expression2.toString();
    }
}
