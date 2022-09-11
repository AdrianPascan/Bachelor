package ro.ubb.bookstore.core.model.validators;

import org.springframework.stereotype.Component;
import ro.ubb.bookstore.core.model.CustomerBook;

import java.util.Optional;

@Component
public class CustomerBookValidator implements Validator<CustomerBook> {
    @Override
    public void validate(CustomerBook entity) throws ValidatorException {
        Optional.of(entity.getQuantity())
                .filter(quantity -> quantity > 0)
                .orElseThrow(() -> new ValidatorException("CustomerBookValidator Exception -> Invalid CustomerBook: quantity not greater than 0."));
    }
}
