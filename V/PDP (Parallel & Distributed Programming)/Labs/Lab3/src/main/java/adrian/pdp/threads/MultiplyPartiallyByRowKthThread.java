package adrian.pdp.threads;

import adrian.pdp.matrix.Matrix;
import adrian.pdp.matrix.MatrixMultiplication;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MultiplyPartiallyByRowKthThread extends Thread {
    private Matrix A;
    private Matrix B;
    private Matrix C;
    private int k;
    private int orderNo;

    public MultiplyPartiallyByRowKthThread(Matrix A, Matrix B, Matrix C, int k, int orderNo) {
        super();

        this.A = A;
        this.B = B;
        this.C = C;
        this.k = k;
        this.orderNo = orderNo;
    }

    @Override
    public void run() {
        super.run();

        MatrixMultiplication.multiplyByRowForEveryKthElement(A, B, C, k, orderNo);
    }
}
