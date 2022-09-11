package flcd.lab2.automaton;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class TransitionInput {
    private String state;
    private String symbol;
}
