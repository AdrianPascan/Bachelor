package ro.ubb.server.model;

import lombok.*;

import java.util.concurrent.atomic.AtomicInteger;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CountyServer {
    private String name;
    private Integer countyId;
    private AtomicInteger candidateA;
    private AtomicInteger candidateB;
    private AtomicInteger candidateC;
    private AtomicInteger nr;
}
