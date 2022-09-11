package adrian.pdp.threadMatrix;

import adrian.pdp.matrix.Matrix;
import adrian.pdp.matrix.MatrixMultiplication;
import adrian.pdp.matrix.MatrixMultiplicationException;
import adrian.pdp.threads.MultiplyPartiallyByRowKthThread;

import java.util.ArrayList;
import java.util.List;

public class MultiplyByRowKthThread extends Thread {
    private Matrix A;
    private Matrix B;
    private Matrix C;
    private int tasksNo;

    public MultiplyByRowKthThread(Matrix A, Matrix B, int tasksNo) {
        super();

        C = MatrixMultiplication.emptyMatrixOfMultiply(A, B);
        if (tasksNo > C.getRowsNo() * C.getColumnsNo()) {
            throw new MatrixMultiplicationException("MatrixMultiplicationException: MultiplyByColumn");
        }

        this.A = A;
        this.B = B;
        this.tasksNo = tasksNo;
    }

    @Override
    public void run() {
        List<Thread> threads = new ArrayList<>();

        for (int orderNo = 0; orderNo < tasksNo; orderNo++) {
            Thread thread = new MultiplyPartiallyByRowKthThread(A, B, C, tasksNo, orderNo);
            threads.add(thread);
        }

        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        });

        System.out.println(C);
    }
}
