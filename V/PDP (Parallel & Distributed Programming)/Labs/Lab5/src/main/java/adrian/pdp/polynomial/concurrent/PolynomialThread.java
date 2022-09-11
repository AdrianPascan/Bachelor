package adrian.pdp.polynomial.concurrent;

import adrian.pdp.polynomial.Polynomial;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PolynomialThread extends Thread {
    protected Polynomial polynomial;

    public PolynomialThread() {
    }

    public PolynomialThread(Runnable target) {
        super(target);
    }
}
