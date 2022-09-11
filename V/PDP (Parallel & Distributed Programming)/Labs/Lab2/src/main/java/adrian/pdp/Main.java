package adrian.pdp;

import adrian.pdp.concurrent.ConsumerThread;
import adrian.pdp.concurrent.ProducerThread;
import adrian.pdp.exception.MyException;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) {
        System.out.println("APP STARTED...\n");

        Vector<Integer> firstVector =
                new Vector<>(Arrays.asList(1,2,3));
//                new Vector<>(Arrays.asList(1,1,1,1,1));
//                new Vector<>(Arrays.asList(0,0,0,0,0,0));
        Vector<Integer> secondVector =
                new Vector<>(Arrays.asList(4,5,6));
//                new Vector<>(Arrays.asList(1,2,3,4,5));
//                new Vector<>(Arrays.asList(0,0,0,0,0,0));
        List<Integer> products = new LinkedList<>();
        Boolean sent = false;

        Lock mutex = new ReentrantLock();
        Condition condition = mutex.newCondition();
        int capacity = 2;
        long startNanoTime = System.nanoTime();

        ProducerThread producerThread = ProducerThread.builder()
                .firstVector(firstVector)
                .secondVector(secondVector)
                .products(products)
                .mutex(mutex)
                .condition(condition)
                .startNanoTime(startNanoTime)
                .capacity(capacity)
                .build();

        ConsumerThread consumerThread = ConsumerThread.builder()
                .products(products)
                .mutex(mutex)
                .condition(condition)
                .startNanoTime(startNanoTime)
                .build();

        try {
            producerThread.start();
            consumerThread.start();

            producerThread.join();
            consumerThread.join();
        } catch (MyException exception) {
            System.out.println(exception.getMessage());
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }

        System.out.println("APP STOPPED.");
    }
}
