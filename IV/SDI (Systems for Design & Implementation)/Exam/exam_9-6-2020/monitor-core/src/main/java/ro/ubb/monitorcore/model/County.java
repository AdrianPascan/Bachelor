package ro.ubb.monitorcore.model;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class County {
    private String countyName;
    private int candidateA;
    private int candidateB;
    private int candidateC;
    private int nr;
}
