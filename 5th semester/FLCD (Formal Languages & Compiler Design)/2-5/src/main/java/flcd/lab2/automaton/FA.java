package flcd.lab2.automaton;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class FA {
    public static final String SEPARATOR = "\\|";
    public static final String TRANSITION_SEPARATOR = "~";

    private Set<String> states;
    private Set<String> alphabet;
    private Map<TransitionInput, TransitionOutput> transitions;
    private String initialState;
    private Set<String> finalStates;
    private boolean deterministic;

    public FA() {
        states = new HashSet<>();
        alphabet = new HashSet<>();
        transitions = new HashMap<>();
        initialState = null;
        finalStates = new HashSet<>();
        deterministic = false;
    }

    public FA(String filePath) {
        states = new HashSet<>();
        alphabet = new HashSet<>();
        transitions = new HashMap<>();
        initialState = null;
        finalStates = new HashSet<>();

        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);

            // states
            if (!scanner.hasNextLine()) {
                throw new FAException("FAException: no data for states, alphabet, transitions, initial state and final states..");
            }
            List<String> states = Arrays.asList(scanner.nextLine().split(SEPARATOR));
            this.states.addAll(states);

            // alphabet
            if (!scanner.hasNextLine()) {
                throw new FAException("FAException: no data for alphabet, transitions, initial state and final states..");
            }
            List<String> symbols = Arrays.asList(scanner.nextLine().split(SEPARATOR));
            this.alphabet.addAll(symbols);

            // transitions
            if (!scanner.hasNextLine()) {
                throw new FAException("FAException: no data for transitions, initial state and final states..");
            }
            List<String> transitions = Arrays.asList(scanner.nextLine().split(SEPARATOR));
            transitions.forEach(transition -> {
                String[] parameters = transition.split(TRANSITION_SEPARATOR);
                if (parameters.length != 3) {
                    throw new FAException("FAException: invalid transition '" + transition + "'");
                }
                if (! (this.states.containsAll(Arrays.asList(parameters[0], parameters[2])) && this.alphabet.contains(parameters[1])) ){
                    System.out.println(Arrays.toString(parameters));
                    throw new FAException("FAException: invalid transition '" + transition + "' as one of the states is not contained by the states or the symbol is not contained by the alphabet");
                }

                TransitionInput input = new TransitionInput(parameters[0], parameters[1]);
                TransitionOutput output = this.transitions.get(input);
                if (output == null) {
                    output = new TransitionOutput();
                    this.transitions.put(input, output);
                }
                output.getStates().add(parameters[2]);
            });

            // initial state
            if (!scanner.hasNextLine()) {
                throw new FAException("FAException: no data for initial state and final states..");
            }
            this.initialState = scanner.nextLine();
            if (!states.contains(this.initialState)) {
                throw new FAException("FAException: initial state must be contained by states");
            }

            // final states
            if (!scanner.hasNextLine()) {
                throw new FAException("FAException: no data for states, alphabet, transitions, initial state and final states..");
            }
            List<String> finalStates = Arrays.asList(scanner.nextLine().split(SEPARATOR));
            this.finalStates.addAll(finalStates);
            if (!states.containsAll(finalStates)) {
                throw new FAException("FAException: final states must be a subset of states");
            }

            deterministic = isDeterministic();
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    private boolean isDeterministic() {
        for (TransitionOutput output:
             transitions.values()) {
            if (output != null && output.getStates().size() > 1) {
                return false;
            }
        }
        return true;
    }

    public boolean isAccepted(String sequence) throws FAException {
        if (!deterministic) {
            throw new FAException("FAException: non-deterministic FA");
        }

        return isAcceptedRec(initialState, sequence);
    }

    private boolean isAcceptedRec(String state, String sequence) {
        if (sequence == null || sequence.isEmpty()) {
            return finalStates.contains(state);
        }

        String symbol = sequence.substring(0,1);
        String subsequence = sequence.substring(1);

        TransitionInput input = new TransitionInput(state, symbol);
        TransitionOutput output = transitions.get(input);
        if (output == null) {
            return false;
        }

        for (String outputState :
                output.getStates()) {
            if (isAcceptedRec(outputState, subsequence)) {
                return true;
            }
        }
        return false;
    }
}
