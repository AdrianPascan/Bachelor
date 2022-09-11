package adrian.pdp.threadMatrix;

import adrian.pdp.matrix.Matrix;
import adrian.pdp.matrix.MatrixMultiplication;
import adrian.pdp.matrix.MatrixMultiplicationException;
import adrian.pdp.threads.MultiplyPartiallyByColumnThread;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MultiplyByColumnThread extends Thread {
    private Matrix A;
    private Matrix B;
    private Matrix C;
    private int tasksNo;

    public MultiplyByColumnThread(Matrix A, Matrix B, int tasksNo) {
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
        int elementsNoPerTask = C.getElementsNo() / tasksNo;

        for (int taskIndex = 0; taskIndex < tasksNo - 1; taskIndex++) {
            int index = taskIndex * elementsNoPerTask;
            int startRowIndex = index % C.getRowsNo();
            int startColumnIndex = index / C.getRowsNo();

            Thread thread = new MultiplyPartiallyByColumnThread(A, B, C, elementsNoPerTask, startRowIndex, startColumnIndex);
            threads.add(thread);
        }
        // last thread
        int index = (tasksNo - 1) * elementsNoPerTask;
        int startRowIndex = index % C.getRowsNo();
        int startColumnIndex = index / C.getRowsNo();
        elementsNoPerTask += C.getElementsNo() % tasksNo;
        Thread thread = new MultiplyPartiallyByColumnThread(A, B, C, elementsNoPerTask, startRowIndex, startColumnIndex);
        threads.add(thread);

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
