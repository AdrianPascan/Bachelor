package adrian.pdp;

import adrian.pdp.polynomial.Polynomial;
import adrian.pdp.polynomial.addition.PolynomialAddition;
import adrian.pdp.polynomial.multiplication.karatsuba.KaratsubaParallelPolynomialMultiplication;
import adrian.pdp.polynomial.multiplication.karatsuba.KaratsubaSequentialPolynomialMultiplication;
import adrian.pdp.polynomial.multiplication.regular.ParallelPolynomialMultiplication;
import adrian.pdp.polynomial.multiplication.regular.SequentialPolynomialMultiplication;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("APP STARTED...");

        // first polynomials
        List<Polynomial> firsts = Arrays.asList(
                new Polynomial(new Vector<>(Arrays.asList(1))),
                new Polynomial(new Vector<>(Arrays.asList(2,1))),
                new Polynomial(new Vector<>(Arrays.asList(4,3,2,1))),
                new Polynomial(new Vector<>(Arrays.asList(8,7,6,5,4,3,2,1)))
        );

        // second polynomials
        List<Polynomial> seconds = Arrays.asList(
                new Polynomial(new Vector<>(Arrays.asList(2))),
                new Polynomial(new Vector<>(Arrays.asList(4,3))),
                new Polynomial(new Vector<>(Arrays.asList(8,7,6,5))),
                new Polynomial(new Vector<>(Arrays.asList(16,15,14,13,12,11,10,9)))
        );

        // result polynomials
        List<Polynomial> results = Arrays.asList(
                new Polynomial(new Vector<>(Arrays.asList(2))),
                new Polynomial(new Vector<>(Arrays.asList(8,10,3))),
                new Polynomial(new Vector<>(Arrays.asList(32,52,61,60,34,16,5))),
                new Polynomial(new Vector<>(Arrays.asList(128,232,313,372,410,428,427,408,308,224,155,100,58,28,9)))
        );

        try(BufferedWriter writer = new BufferedWriter((new FileWriter("output/documentation.csv")))) {
            // header
            writer.write("First Pol.,Second Pol.,Result Pol.,Sequential,Parallel,Karatsuba Seq.,Karatsuba Par.");

            long tic = 0;
            long tac = 0;

            for (int index = 0; index < results.size(); index++) {
                writer.newLine();
                writer.write(firsts.get(index).toPrintable() + ",");
                writer.write(seconds.get(index).toPrintable() + ",");
                writer.write(results.get(index).toPrintable() + ",");

                System.out.println("First: " + firsts.get(index));
                System.out.println("Second: " + seconds.get(index));
                System.out.println("Result: " + results.get(index));

                tic = System.nanoTime();
                SequentialPolynomialMultiplication.multiply(firsts.get(index), seconds.get(index));
                tac = System.nanoTime();
                writer.write(Long.toString(tac - tic) + ",");
                System.out.println("Sequential: " + ((tac-tic) / 1000) + " ms" );

                tic = System.nanoTime();
                ParallelPolynomialMultiplication.multiply(firsts.get(index), seconds.get(index));
                tac = System.nanoTime();
                writer.write(Long.toString(tac - tic) + ",");
                System.out.println("Parallel: " + ((tac-tic) / 1000) + " ms" );

                tic = System.nanoTime();
                KaratsubaSequentialPolynomialMultiplication.multiply(firsts.get(index), seconds.get(index));
                tac = System.nanoTime();
                writer.write(Long.toString(tac - tic) + ",");
                System.out.println("Karatsuba Seq.: " + ((tac-tic) / 1000) + " ms" );

                tic = System.nanoTime();
                KaratsubaParallelPolynomialMultiplication.multiply(firsts.get(index), seconds.get(index));
                tac = System.nanoTime();
                writer.write(Long.toString(tac - tic));
                System.out.println("Karatsuba Par.: " + ((tac-tic) / 1000) + " ms" );

                System.out.println();
            }
        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }

        System.out.println("\nAPP STOPPED.");
    }
}
