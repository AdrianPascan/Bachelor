package adrian.pdp;

import adrian.pdp.matrix.Matrix;
import adrian.pdp.threadMatrix.MultiplyByColumnThread;
import adrian.pdp.threadMatrix.MultiplyByRowKthThread;
import adrian.pdp.threadMatrix.MultiplyByRowThread;
import adrian.pdp.threadPoolMatrix.MultiplyByColumnThreadPool;
import adrian.pdp.threadPoolMatrix.MultiplyByRowKthThreadPool;
import adrian.pdp.threadPoolMatrix.MultiplyByRowThreadPool;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static final int MAX_TASKS_NO = 12;

    public static void main(String[] args) {
        System.out.println("APP STARTED..\n");

        Matrix A = new Matrix(3, 2, Arrays.asList(1,2,3,4,5,6));
        Matrix B = new Matrix(2, 4, Arrays.asList(1,2,3,4,5,6,7,8));

//    Result matrix:
//        Matrix C = new Matrix(3, 4, Arrays.asList(11,14,17,20,23,30,37,44,35,46,57,68));

        System.out.println(A);
        System.out.println(B);

        try(BufferedWriter writer = new BufferedWriter((new FileWriter("documentation.csv")))) {
            // header
            writer.write("No. of Tasks,Thread Row,Thread Column,Thread Row Kth," +
                    "Thread Pool(2) Row,Thread Pool(2) Column,Thread Pool(2) Row Kth," +
                    "Thread Pool(5) Row,Thread Pool(5) Column,Thread Pool(5) Row Kth," +
                    "Thread Pool(10) Row,Thread Pool(10) Column,Thread Pool(10) Row Kth");

            long tic = 0;
            long tac = 0;

            for (int tasksNo = 1; tasksNo <= MAX_TASKS_NO; tasksNo++) {
                writer.newLine();
                writer.write(tasksNo + ",");

                Thread thread = new MultiplyByRowThread(A, B, tasksNo);
                tic = System.nanoTime();
                thread.start();
                thread.join();
                tac = System.nanoTime();
                writer.write(Long.toString(tac - tic) + ",");

                thread = new MultiplyByColumnThread(A, B, tasksNo);
                tic = System.nanoTime();
                thread.start();
                thread.join();
                tac = System.nanoTime();
                writer.write(Long.toString(tac - tic) + ",");

                thread = new MultiplyByRowKthThread(A, B, tasksNo);
                tic = System.nanoTime();
                thread.start();
                thread.join();
                tac = System.nanoTime();
                writer.write(Long.toString(tac - tic) + ",");

                // thread pool(2)
                Runnable runnable = new MultiplyByRowThreadPool(A, B, tasksNo, 2);
                tic = System.nanoTime();
                runnable.run();
                tac = System.nanoTime();
                writer.write(Long.toString(tac - tic) + ",");

                runnable = new MultiplyByColumnThreadPool(A, B, tasksNo, 2);
                tic = System.nanoTime();
                runnable.run();
                tac = System.nanoTime();
                writer.write(Long.toString(tac - tic) + ",");

                runnable = new MultiplyByRowKthThreadPool(A, B, tasksNo, 2);
                tic = System.nanoTime();
                runnable.run();
                tac = System.nanoTime();
                writer.write(Long.toString(tac - tic) + ",");

                // thread pool(5)
                runnable = new MultiplyByRowThreadPool(A, B, tasksNo, 5);
                tic = System.nanoTime();
                runnable.run();
                tac = System.nanoTime();
                writer.write(Long.toString(tac - tic) + ",");

                runnable = new MultiplyByColumnThreadPool(A, B, tasksNo, 5);
                tic = System.nanoTime();
                runnable.run();
                tac = System.nanoTime();
                writer.write(Long.toString(tac - tic) + ",");

                runnable = new MultiplyByRowKthThreadPool(A, B, tasksNo, 5);
                tic = System.nanoTime();
                runnable.run();
                tac = System.nanoTime();
                writer.write(Long.toString(tac - tic) + ",");

                // thread pool(10)
                runnable = new MultiplyByRowThreadPool(A, B, tasksNo, 10);
                tic = System.nanoTime();
                runnable.run();
                tac = System.nanoTime();
                writer.write(Long.toString(tac - tic) + ",");

                runnable = new MultiplyByColumnThreadPool(A, B, tasksNo, 10);
                tic = System.nanoTime();
                runnable.run();
                tac = System.nanoTime();
                writer.write(Long.toString(tac - tic) + ",");

                runnable = new MultiplyByRowKthThreadPool(A, B, tasksNo, 10);
                tic = System.nanoTime();
                runnable.run();
                tac = System.nanoTime();
                writer.write(Long.toString(tac - tic) + ",");
            }


        } catch (IOException | InterruptedException exception) {
            exception.printStackTrace();
        }

        Runnable runnable = new MultiplyByRowKthThreadPool(A, B, 5, 2);
        runnable.run();

        System.out.println("\nAPP STOPPED.");
    }
}
