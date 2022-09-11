package adrian.pdp.polynomial.multiplication.regular;

import adrian.pdp.polynomial.Polynomial;
import adrian.pdp.polynomial.addition.PolynomialAddition;

import java.util.Arrays;
import java.util.Vector;

public class SequentialPolynomialMultiplication {
    public static Polynomial multiply(Polynomial first, Polynomial second) {
        return multiplyDivideAndConquer(first, second);
    }

    private static Polynomial multiplyDivideAndConquer(Polynomial first, Polynomial second) {
        if (first.getOrder() == 0) {
            Vector<Integer> coefficients = new Vector<>(Arrays.asList(
                    first.getCoefficients().firstElement() * second.getCoefficients().firstElement()
            ));
            return new Polynomial(coefficients);
        }

        Vector<Integer> firstLowerHalfCoefficients = new Vector<>(
                first.getCoefficients().subList(0, (first.getOrder() + 1) / 2)
        );
        Vector<Integer> firstUpperHalfCoefficients = new Vector<>(
                first.getCoefficients().subList((first.getOrder() + 1) / 2, first.getOrder() + 1)
        );
        Vector<Integer> secondLowerHalfCoefficients = new Vector<>(
                second.getCoefficients().subList(0, (second.getOrder() + 1) / 2)
        );
        Vector<Integer> secondUpperHalfCoefficients = new Vector<>(
                second.getCoefficients().subList((second.getOrder() + 1) / 2, second.getOrder() + 1)
        );

        Polynomial firstLowerHalf = new Polynomial(firstLowerHalfCoefficients);
        Polynomial firstUpperHalf = new Polynomial(firstUpperHalfCoefficients);
        Polynomial secondLowerHalf = new Polynomial(secondLowerHalfCoefficients);
        Polynomial secondUpperHalf = new Polynomial(secondUpperHalfCoefficients);

        Polynomial multiplication = multiplyDivideAndConquer(firstUpperHalf, secondUpperHalf);
        Polynomial multiplication2 = multiplyDivideAndConquer(firstUpperHalf, secondLowerHalf);
        Polynomial multiplication3 = multiplyDivideAndConquer(firstLowerHalf, secondUpperHalf);
        Polynomial multiplication4 = multiplyDivideAndConquer(firstLowerHalf, secondLowerHalf);

        Polynomial term = multiplication.increaseOrder(first.getOrder() + 1);

        Polynomial term2 = PolynomialAddition.add(multiplication2, multiplication3)
                .increaseOrder((first.getOrder() + 1) / 2);

        Polynomial term3 = multiplication4;

        return PolynomialAddition.add(
                term,
                PolynomialAddition.add(term2, term3)
        );
    }
}
