package ro.ubb.server.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ScannerService {
    private Integer countyId;
    private String name;
    private Integer candidateA;
    private Integer candidateB;
    private Integer candidateC;
}
