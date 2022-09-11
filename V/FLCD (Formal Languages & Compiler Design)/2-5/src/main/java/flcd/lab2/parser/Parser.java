package flcd.lab2.parser;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
public class Parser {
    private Grammar grammar;
    private State state;
    private int sequenceIndex;
    private Stack<StackEntry> workingStack;
    private Stack<StackEntry> inputStack;

    public Parser() {
        grammar = new Grammar();
    }

    public Parser(String grammarFilePath) {
        this.grammar = new Grammar(grammarFilePath);
    }

    public List<List<String>> isAccepted(List<String> sequence) {
        sequence.forEach(term -> {
            if (! grammar.getTerminals().contains(term)) {
                state = State.ERROR;
            }
        });

        // init.
        state = State.NORMAL;
        sequenceIndex = 0;
        workingStack = new Stack<>();
        inputStack = new Stack<>();
        inputStack.push(new StackEntry(grammar.getStartSymbol()));

        // alg.
        while (state != State.FINAL && state != State.ERROR) {
            if (state == State.NORMAL) {
                if (inputStack.isEmpty() && sequenceIndex == sequence.size()) {
                    success();
                }
                else {
                    if (inputStack.isEmpty()) {
                        momentaryInsuccess();
                    }
                    else {
                        if (grammar.getNonterminals().contains(inputStack.peek().head)) {
                            expand();
                        }
                        else {
                            if (sequenceIndex == sequence.size()) {
                                momentaryInsuccess();
                            }
                            else {
                                if (inputStack.peek().head.equals(sequence.get(sequenceIndex))) {
                                    advance();
                                }
                                else {
                                    momentaryInsuccess();
                                }
                            }
                        }
                    }
                }
            } else {
                if (state == State.BACK) {
                    if (grammar.getTerminals().contains(workingStack.peek().head)) {
                        back();
                    }
                    else {
                        anotherTry();
                    }
                }
            }
        }

        if (state == State.ERROR) {
            return null;
        }

        List<List<String>> productionRules = new ArrayList<>();
        while (!workingStack.isEmpty()) {
            StackEntry entry = workingStack.pop();

            if (!entry.list.isEmpty()) {
                if (grammar.getProductions().get(entry.head) != null) {
                    if (grammar.getProductions().get(entry.head).contains(entry.list)) {
                        productionRules.add(entry.list);
                    }
                }
            }
        }
        return productionRules;
    }

    private void success() {
        state = State.FINAL;
    }

    private void expand() {
        String nonterminal = inputStack.peek().head;
        List<String> firstProduction = grammar.getProductions().get(nonterminal).get(0);

        workingStack.push(new StackEntry(nonterminal, new ArrayList<>(firstProduction)));
        inputStack.pop();
        inputStack.push(new StackEntry(null, new ArrayList<>(firstProduction)));
    }

    private void advance() {
        sequenceIndex++;
        workingStack.push(new StackEntry(inputStack.peek().list.get(0)));
        inputStack.peek().list.remove(0);
    }

    private void momentaryInsuccess() {
        state = State.BACK;
    }

    private void back() {
        sequenceIndex--;
        String terminal = workingStack.pop().head;
        inputStack.push(new StackEntry(terminal));
    }

    private void anotherTry() {
        StackEntry entry = workingStack.peek();
        String nonterminal = entry.head;

        // next prod.
        List<List<String>> productions = grammar.getProductions().get(nonterminal);
        List<String> nextProduction = null;
        for (int index = 0; index < productions.size() - 1; index++) {
            if (productions.get(index).equals(entry.list)) {
                nextProduction = productions.get(index + 1);
                break;
            }
        }

        if (nextProduction != null) {
            state= State.NORMAL;
            workingStack.pop();
            workingStack.push(new StackEntry(nonterminal, new ArrayList<>(nextProduction)));
            for (int i = 0; i < entry.list.size(); i++) {
                inputStack.pop();
            }
            nextProduction.forEach(term -> {
                inputStack.push(new StackEntry(term));
            });
        }
        else if (sequenceIndex == 0 && grammar.getStartSymbol().equals(nonterminal)) {
            state = State.ERROR;
        }
        else {
            workingStack.pop();
            for (int i = 0; i < entry.list.size(); i++) {
                inputStack.pop();
            }
            inputStack.push(new StackEntry(nonterminal));
        }
    }

    private void pushStrings(List<String> elements, Stack<String> stack) {
        for (int index = elements.size() - 1; index >= 0; index--) {
            stack.push(elements.get(index));
        }
    }

    private enum State {
        NORMAL, //q
        ERROR, //e
        BACK, //b
        FINAL  //f
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class StackEntry {
        private String head;
        private List<String> list;

        public StackEntry() {
            list = new ArrayList<>();
        }

        public StackEntry(String head) {
            list = new ArrayList<>();
        }
    }
}
