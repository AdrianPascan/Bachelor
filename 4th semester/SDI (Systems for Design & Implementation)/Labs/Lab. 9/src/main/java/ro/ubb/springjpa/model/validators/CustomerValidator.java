package ro.ubb.springjpa.model.validators;

import org.springframework.stereotype.Component;
import ro.ubb.springjpa.model.Customer;

import java.util.Optional;

@Component
public class CustomerValidator implements Validator<Customer> {
    @Override
    public void validate(Customer entity) throws ValidatorException {
        Optional.of(entity.getAge())
                .filter(age -> age >= 14 && age <= 100)
                .orElseThrow(() -> new ValidatorException("CustomerValidator Exception -> Invalid Customer: age not in range [14, 100]"));
    }
}
