package ro.ubb.bookstore.core.model.validators;

import org.springframework.stereotype.Component;
import ro.ubb.bookstore.core.model.Book;

import java.util.Optional;

@Component
public class BookValidator implements Validator<Book> {
    @Override
    public void validate(Book entity) throws ValidatorException {
        Optional.of(entity.getPrice())
                .filter(price -> price > 0)
                .orElseThrow(() -> new ValidatorException("BookValidator Exception -> Invalid Book: price must be greater than 0"));
    }
}
