package adrian.pdp.threadPoolMatrix;

import adrian.pdp.matrix.Matrix;
import adrian.pdp.matrix.MatrixMultiplication;
import adrian.pdp.matrix.MatrixMultiplicationException;
import adrian.pdp.runnables.MultiplyPartiallyByRowRunnable;
import adrian.pdp.threads.MultiplyPartiallyByRowThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MultiplyByRowThreadPool implements Runnable {
    private Matrix A;
    private Matrix B;
    private Matrix C;
    private int tasksNo;
    private ExecutorService executorService;

    public MultiplyByRowThreadPool(Matrix A, Matrix B, int tasksNo, int maxThreadsNo) {
        C = MatrixMultiplication.emptyMatrixOfMultiply(A, B);
        if (tasksNo > C.getRowsNo() * C.getColumnsNo()) {
            throw new MatrixMultiplicationException("MatrixMultiplicationException: MultiplyByColumn");
        }

        this.A = A;
        this.B = B;
        this.tasksNo = tasksNo;

        executorService = Executors.newFixedThreadPool(maxThreadsNo);
    }

    @Override
    public void run() {
        List<Runnable> runnables = new ArrayList<>();
        int elementsNoPerTask = C.getElementsNo() / tasksNo;

        for (int taskIndex = 0; taskIndex < tasksNo - 1; taskIndex++) {
            int index = taskIndex * elementsNoPerTask;
            int startRowIndex = index / C.getColumnsNo();
            int startColumnIndex = index % C.getColumnsNo();

            Runnable runnable = new MultiplyPartiallyByRowRunnable(A, B, C, elementsNoPerTask, startRowIndex, startColumnIndex);
            runnables.add(runnable);
        }
        // last thread
        int index = (tasksNo - 1) * elementsNoPerTask;
        int startRowIndex = index / C.getColumnsNo();
        int startColumnIndex = index % C.getColumnsNo();
        elementsNoPerTask += C.getElementsNo() % tasksNo;
        Runnable runnable = new MultiplyPartiallyByRowRunnable(A, B, C, elementsNoPerTask, startRowIndex, startColumnIndex);
        runnables.add(runnable);

        List<Future<?>> futures = new ArrayList<>();
        runnables.forEach(r -> {
            Future<?> future = executorService.submit(r);
            futures.add(future);
        });
        futures.forEach(f -> {
            try {
                f.get();
            } catch (InterruptedException | ExecutionException exception) {
                exception.printStackTrace();
            }
        });

        System.out.println(C.toString());
    }
}
