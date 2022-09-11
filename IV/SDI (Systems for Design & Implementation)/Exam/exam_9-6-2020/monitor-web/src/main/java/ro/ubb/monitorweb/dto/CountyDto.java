package ro.ubb.monitorweb.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CountyDto {
    private String countyName;
    private int a;
    private int b;
    private int c;
    private int nr;
}
