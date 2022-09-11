package adrian.pdp.matrix;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
public class Matrix {
    private int rowsNo;
    private int columnsNo;
    private Vector<Vector<Integer>> elements; //thread-safe

    public Matrix(int order) {
        this.rowsNo = this.columnsNo =  order;
        initialiseElements();
    }

    public Matrix(int rowsNo, int columnsNo) {
        this.rowsNo = rowsNo;
        this.columnsNo = columnsNo;
        initialiseElements();
    }

    public Matrix(int rowsNo, int columsNo, List<Integer> elements) {
        if (elements.size() != rowsNo * columsNo) {
            throw new MatrixException("MatrixException: no. of elements is different than the capacity of the matrix");
        }

        this.rowsNo = rowsNo;
        this.columnsNo = columsNo;

        this.elements = new Vector<>(rowsNo);
        for (int rowIndex = 0; rowIndex < rowsNo; rowIndex++) {
            Vector<Integer> row = new Vector<>(elements.subList(rowIndex * columsNo, (rowIndex + 1) * columsNo));
            this.elements.add(row);
        }
    }

    public int getElementsNo() {
        return rowsNo * columnsNo;
    }

    private void initialiseElements() {
        elements = new Vector<>(rowsNo);
        for (int rowIndex = 0; rowIndex < rowsNo; rowIndex++) {
            Vector<Integer> row = new Vector<>(columnsNo);
            row.addAll(Collections.nCopies(columnsNo, 0));
            elements.add(row);
        }
    }
}
