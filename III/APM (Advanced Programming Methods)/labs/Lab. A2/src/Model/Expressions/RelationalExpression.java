package Model.Expressions;

import Exceptions.MyException;
import Model.DataStructures.MyDictionaryInterface;
import Model.ProgramState.Heap;
import Model.ProgramState.SymbolTable;
import Model.Types.BoolType;
import Model.Types.IntegerType;
import Model.Types.Type;
import Model.Values.BoolValue;
import Model.Values.IntegerValue;
import Model.Values.Value;

public class RelationalExpression implements InterfaceExpression {
    private InterfaceExpression expression1;
    private InterfaceExpression expression2;
    private String relation;

    public RelationalExpression(String relation, InterfaceExpression expression1, InterfaceExpression expression2) throws MyException {
        if (relation.equals("<") || relation.equals("<=") || relation.equals("==")
                || relation.equals("!=") || relation.equals(">") || relation.equals(">=")) {
            this.relation = relation;
        }
        else{
            throw new MyException("Invalid relation for relational expression: " + relation + " .");
        }
        this.expression1 = expression1;
        this.expression2 = expression2;
    }

    @Override
    public Value evaluate(SymbolTable symbolTable, Heap heap) throws MyException {
        Value value1 = expression1.evaluate(symbolTable, heap);
        if (value1.getType().equals(new IntegerType())){
            Value value2 = expression2.evaluate(symbolTable, heap);
            if (value2.getType().equals(new IntegerType())){
                int integer1 = ((IntegerValue) value1).getValue();
                int integer2 = ((IntegerValue) value2).getValue();
                switch (relation){
                    case "<":
                        return new BoolValue( integer1 < integer2 );
                    case "<=":
                        return new BoolValue( integer1 <= integer2 );
                    case "==":
                        return new BoolValue( integer1 == integer2 );
                    case "!=":
                        return new BoolValue( integer1 != integer2 );
                    case ">":
                        return new BoolValue( integer1 > integer2);
                    case ">=":
                        return new BoolValue( integer1 >= integer2 );
                    default:
                        throw new MyException("Invalid relation for relational expression: " + relation + " .");
                }
            }
            else{
                throw new MyException("Second operand is not an integer in relational expression.");
            }
        }
        else{
            throw new MyException("First operand is not an integer in relational expression.");
        }
    }

    @Override
    public Type typeCheck(MyDictionaryInterface<String, Type> typeEnvironment) throws MyException {
        Type type1 = expression1.typeCheck(typeEnvironment);
        Type type2 = expression2.typeCheck(typeEnvironment);

        if (type1.equals(new IntegerType())){
            if (type2.equals(new IntegerType())){
                return new BoolType();
            }
            else{
                throw new MyException("Second operand of relational expression is not an integer.");
            }
        }
        else {
            throw new MyException("First operand of relational expression is not an integer.");
        }
    }

    @Override
    public String toString() {
        return expression1.toString() + relation + expression2.toString();
    }
}
