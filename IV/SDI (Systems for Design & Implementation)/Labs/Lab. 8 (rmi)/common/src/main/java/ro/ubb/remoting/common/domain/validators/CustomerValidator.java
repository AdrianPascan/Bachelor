package ro.ubb.remoting.common.domain.validators;

import ro.ubb.remoting.common.domain.Customer;

import java.util.Optional;

public class CustomerValidator implements Validator<Customer> {
    @Override
    public void validate(Customer entity) throws ValidatorException {
        Optional.of(entity.getAge())
                .filter(age -> age >= 14 && age <= 100)
                .orElseThrow(() -> new ValidatorException("Validator Exception -> Invalid Customer: age not in range [14, 100]"));
    }
}