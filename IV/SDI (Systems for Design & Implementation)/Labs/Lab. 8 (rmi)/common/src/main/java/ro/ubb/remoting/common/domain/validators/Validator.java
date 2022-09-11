package ro.ubb.remoting.common.domain.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidatorException;
}