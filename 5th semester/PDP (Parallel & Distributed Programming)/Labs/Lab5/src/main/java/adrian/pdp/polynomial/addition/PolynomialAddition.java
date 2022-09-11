package adrian.pdp.polynomial.addition;

import adrian.pdp.polynomial.Polynomial;

import java.util.Vector;

public class PolynomialAddition {

    public static Polynomial add(Polynomial first, Polynomial second) {
        Polynomial greaterOrderPolynomial = first;
        Polynomial smallerOrderPolynomial = second;
        if (first.getOrder() < second.getOrder()) {
            greaterOrderPolynomial = second;
            smallerOrderPolynomial = first;
        }

        Polynomial result = new Polynomial(new Vector<>(greaterOrderPolynomial.getCoefficients()));

        for (int index = 0; index <= smallerOrderPolynomial.getOrder(); index++) {
            result.getCoefficients().set(
                    index,
                    result.getCoefficients().get(index) + smallerOrderPolynomial.getCoefficients().get(index)
            );
        }

        return result.updateOrder();
    }

    public static Polynomial subtract(Polynomial first, Polynomial second) {
        boolean reversed = false;
        Polynomial greaterOrderPolynomial = first;
        Polynomial smallerOrderPolynomial = second;
        if (first.getOrder() < second.getOrder()) {
            reversed = true;
            greaterOrderPolynomial = second;
            smallerOrderPolynomial = first;
        }

        Polynomial result = new Polynomial(new Vector<>(greaterOrderPolynomial.getCoefficients()));

        for (int index = 0; index <= smallerOrderPolynomial.getOrder(); index++) {
            result.getCoefficients().set(
                    index,
                    reversed ?
                            - result.getCoefficients().get(index) + smallerOrderPolynomial.getCoefficients().get(index) :
                            result.getCoefficients().get(index) - smallerOrderPolynomial.getCoefficients().get(index)
            );
        }

        if (reversed) {
            for (int index = smallerOrderPolynomial.getOrder() + 1; index <= greaterOrderPolynomial.getOrder(); index++) {
                result.getCoefficients().set(
                        index,
                        - result.getCoefficients().get(index)
                );
            }
        }

        return result.updateOrder();
    }
}
