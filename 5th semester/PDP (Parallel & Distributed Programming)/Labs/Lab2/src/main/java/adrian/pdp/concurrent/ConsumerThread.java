package adrian.pdp.concurrent;

import lombok.*;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ConsumerThread extends Thread {
    private List<Integer> products;
    private Lock mutex;
    private Condition condition;
    private long startNanoTime;

    @Override
    public void run() {
        System.out.println("CONSUMER: started...");

        super.run();

        try {
            int sum = 0;

            mutex.lock();

            while(true) {
                if (products.isEmpty()) {
                    condition.await();
                }
                Integer product = products.remove(0);
                System.out.println("CONSUMER: received product=" + product +
                                " at time=" + (System.nanoTime() - startNanoTime));

                if (product != null) {
                    sum += product;
                } else {
                    System.out.println("CONSUMER: sum=" + sum);
                    break;
                }

                condition.signal();
            }

        } catch (InterruptedException exception) {
            exception.printStackTrace();
            this.interrupt();
        }
        finally {
            mutex.unlock();
        }

        System.out.println("CONSUMER: stopped.");
    }
}
