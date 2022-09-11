package adrian.pdp.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Product {
    private String name;
    private double pricePerUnit;
    private int units;
}
