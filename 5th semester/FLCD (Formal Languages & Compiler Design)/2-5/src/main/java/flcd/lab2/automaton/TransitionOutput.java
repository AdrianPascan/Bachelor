package flcd.lab2.automaton;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TransitionOutput {
    private Set<String> states;

    public TransitionOutput() {
        states = new HashSet<>();
    }
}
