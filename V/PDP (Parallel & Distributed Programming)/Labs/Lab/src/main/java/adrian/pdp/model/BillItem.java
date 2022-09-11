package adrian.pdp.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BillItem {
    private Product product;
    private int quantity;
}
