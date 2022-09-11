package ro.ubb.springjpa.model.validators;

import org.springframework.stereotype.Component;
import ro.ubb.springjpa.model.Transaction;

@Component
public class TransactionValidator implements Validator<Transaction> {
    @Override
    public void validate(Transaction entity) throws ValidatorException {
    }
}
