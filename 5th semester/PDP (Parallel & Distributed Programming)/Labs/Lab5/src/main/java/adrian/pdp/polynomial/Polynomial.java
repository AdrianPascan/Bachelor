package adrian.pdp.polynomial;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.Vector;
import java.util.stream.IntStream;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Polynomial {
    private int order;
    private Vector<Integer> coefficients;

    public Polynomial() {
        order = 0;
        this.coefficients = new Vector<>(Collections.nCopies(1, 0));
    }

    public Polynomial(int order) {
        this.order = order;
        this.coefficients = new Vector<>(Collections.nCopies(order + 1, 0));
    }

    public Polynomial(Vector<Integer> coefficients) {
        this.order = coefficients.size() - 1;
        this.coefficients = coefficients;
    }

    public Polynomial increaseOrder(int additionalOrder) {
        coefficients.addAll(0, Collections.nCopies(additionalOrder, 0));
        order += additionalOrder;

        return this;
    }

    public Polynomial updateOrder() {
        while (order > 0 && coefficients.get(order) == 0) {
            coefficients.remove(order--);
        }

        return this;
    }

    public String toPrintable() {
        StringBuilder builder = new StringBuilder();

        for (int index = order; index >= 0; index--) {
            builder.append(coefficients.get(index));
            builder.append("*x^");
            builder.append(index);
            builder.append("+");
        }

        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }
}
