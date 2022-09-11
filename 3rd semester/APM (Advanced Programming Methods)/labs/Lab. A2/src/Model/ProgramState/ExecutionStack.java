package Model.ProgramState;

import Model.DataStructures.MyStackInterface;
import Model.Statements.InterfaceStatement;

import java.util.Stack;

public class ExecutionStack implements MyStackInterface<InterfaceStatement> {
    private Stack<InterfaceStatement> stack;

    public ExecutionStack(){
        stack = new Stack<InterfaceStatement>();
    }

    @Override
    public InterfaceStatement pop() {
        return stack.pop();
    }

    @Override
    public void push(InterfaceStatement value) {
        stack.push(value);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public Stack<InterfaceStatement> getStack(){
        return stack;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Execution stack:\n");
        Stack<InterfaceStatement> copy = (Stack<InterfaceStatement>) stack.clone();
        while (!copy.isEmpty()){
            result.append(copy.pop().toString());
            result.append("\n");
        }
        return result.toString();
    }
}
