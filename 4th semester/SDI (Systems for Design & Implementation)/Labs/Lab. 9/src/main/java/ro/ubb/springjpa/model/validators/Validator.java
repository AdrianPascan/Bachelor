package ro.ubb.springjpa.model.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}