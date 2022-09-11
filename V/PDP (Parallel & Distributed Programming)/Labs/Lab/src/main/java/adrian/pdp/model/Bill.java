package adrian.pdp.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class Bill {
    private int billNo;
    private List<BillItem> billItems;
    private double total;
    private boolean processed;

    public Bill() {
        billItems = new ArrayList<>();
    }
}
