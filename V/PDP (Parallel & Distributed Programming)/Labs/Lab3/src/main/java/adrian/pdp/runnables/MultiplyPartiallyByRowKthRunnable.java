package adrian.pdp.runnables;

import adrian.pdp.matrix.Matrix;
import adrian.pdp.matrix.MatrixMultiplication;

public class MultiplyPartiallyByRowKthRunnable implements Runnable {
    private Matrix A;
    private Matrix B;
    private Matrix C;
    private int k;
    private int orderNo;

    public MultiplyPartiallyByRowKthRunnable(Matrix A, Matrix B, Matrix C, int k, int orderNo) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.k = k;
        this.orderNo = orderNo;
    }

    @Override
    public void run() {
        MatrixMultiplication.multiplyByRowForEveryKthElement(A, B, C, k, orderNo);
    }
}
