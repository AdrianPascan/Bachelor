package ro.ubb.repository.sorting;

public class Sort {
    private String fieldName;

    public Sort() {
    }

    public Sort(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
