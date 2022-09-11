package adrian.pdp.runnables;

import adrian.pdp.matrix.Matrix;
import adrian.pdp.matrix.MatrixMultiplication;

public class MultiplyPartiallyByRowRunnable implements Runnable {
    private Matrix A;
    private Matrix B;
    private Matrix C;
    private int elementsNo;
    private int startRowIndex;
    private int startColumnIndex;

    public MultiplyPartiallyByRowRunnable(Matrix A, Matrix B, Matrix C, int elementsNo, int startRowIndex, int startColumnIndex) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.elementsNo = elementsNo;
        this.startRowIndex = startRowIndex;
        this.startColumnIndex = startColumnIndex;
    }

    @Override
    public void run() {
        MatrixMultiplication.multiplyByRowForConsecutiveElements(A, B, C, elementsNo, startRowIndex, startColumnIndex);
    }
}
