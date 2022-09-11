package adrian.pdp.threads;

import adrian.pdp.matrix.Matrix;
import adrian.pdp.matrix.MatrixMultiplication;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MultiplyPartiallyByColumnThread extends Thread {
    private Matrix A;
    private Matrix B;
    private Matrix C;
    private int elementsNo;
    private int startRowIndex;
    private int startColumnIndex;

    public MultiplyPartiallyByColumnThread(Matrix A, Matrix B, Matrix C, int elementsNo, int startRowIndex, int startColumnIndex) {
        super();

        this.A = A;
        this.B = B;
        this.C = C;
        this.elementsNo = elementsNo;
        this.startRowIndex = startRowIndex;
        this.startColumnIndex = startColumnIndex;
    }

    @Override
    public void run() {
        super.run();

        MatrixMultiplication.multiplyByColumnForConsecutiveElements(A, B, C, elementsNo, startRowIndex, startColumnIndex);
    }
}
