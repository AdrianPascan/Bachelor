package ro.ubb.scanner.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Builder
public class ScannerClient {
    private String name;
    private Integer countyId;
}
