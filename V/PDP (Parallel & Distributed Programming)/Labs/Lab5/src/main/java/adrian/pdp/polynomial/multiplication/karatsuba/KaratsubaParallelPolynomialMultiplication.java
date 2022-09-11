package adrian.pdp.polynomial.multiplication.karatsuba;

import adrian.pdp.polynomial.Polynomial;
import adrian.pdp.polynomial.addition.PolynomialAddition;
import adrian.pdp.polynomial.concurrent.PolynomialThread;
import adrian.pdp.polynomial.multiplication.regular.ParallelPolynomialMultiplication;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Vector;

public class KaratsubaParallelPolynomialMultiplication {
    public static Polynomial multiply(Polynomial first, Polynomial second) throws InterruptedException {
        MultiplyThread thread = new MultiplyThread(first, second);

        thread.start();
        thread.join();

        return thread.getPolynomial();
    }

    private static Polynomial multiplyDivideAndConquer(Polynomial first, Polynomial second) throws InterruptedException {
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

        MultiplyThread thread = new MultiplyThread(firstUpperHalf, secondUpperHalf);
        MultiplyThread thread2 = new MultiplyThread(
                PolynomialAddition.add(firstLowerHalf, firstUpperHalf),
                PolynomialAddition.add(secondLowerHalf, secondUpperHalf)
        );
        MultiplyThread thread3 = new MultiplyThread(firstLowerHalf, secondLowerHalf);

        thread.start();
        thread2.start();
        thread3.start();

        thread.join();
        thread2.join();
        thread3.join();

        Polynomial term2 =
                PolynomialAddition.subtract(
                        PolynomialAddition.subtract(thread2.getPolynomial(), thread.getPolynomial()),
                        thread3.getPolynomial()
                )
                        .increaseOrder((first.getOrder() + 1) / 2);

        Polynomial term = thread.getPolynomial()
                .increaseOrder(first.getOrder() + 1);

        Polynomial term3 = thread3.getPolynomial();

        return PolynomialAddition.add(
                term,
                PolynomialAddition.add(term2, term3)
        );
    }

    @Getter
    @Setter
    private static class MultiplyThread extends PolynomialThread {
        private Polynomial first;
        private Polynomial second;

        public MultiplyThread(Polynomial first, Polynomial second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public void run() {
            super.run();
            try {
                polynomial = multiplyDivideAndConquer(first, second);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
