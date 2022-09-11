package adrian.pdp.concurrent;

import adrian.pdp.exception.MyException;
import lombok.*;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.stream.IntStream;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProducerThread extends Thread {
    private Vector<Integer> firstVector;
    private Vector<Integer> secondVector;
    private List<Integer> products;
    private Lock mutex;
    private Condition condition;
    int capacity;
    private long startNanoTime;

    @Override
    public void run() {
        System.out.println("PRODUCER: started...");

        super.run();

        try {
            mutex.lock();

            if (firstVector.size() != secondVector.size()) {
                System.out.println("PRODUCER: error");
                throw new MyException(
                        String.format("Vector sizes are different: %d and %d", firstVector.size(), secondVector.size()));
            }

            IntStream.range(0, firstVector.size())
                    .forEach(index -> {
                        try {
                            if (products.size() == capacity) {
                                condition.await();
                            }

                            int product = firstVector.get(index) * secondVector.get(index);
                            products.add(product);
                            System.out.println("PRODUCER: sent new product=" + product
                                    + " at time=" + (System.nanoTime() - startNanoTime));

                            condition.signal();
                        } catch (InterruptedException exception) {
                            exception.printStackTrace();
                            this.interrupt();
                        }
                    });
        } finally {
            products.add(null);
            condition.signal();
            mutex.unlock();
        }

        System.out.println("PRODUCER: stopped.");
    }
}
