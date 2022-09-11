package ro.ubb.bookstore.core.model.validators;

import org.springframework.stereotype.Component;
import ro.ubb.bookstore.core.model.Transaction;

@Component
public class TransactionValidator implements Validator<Transaction> {
    @Override
    public void validate(Transaction entity) throws ValidatorException {
    }
}
