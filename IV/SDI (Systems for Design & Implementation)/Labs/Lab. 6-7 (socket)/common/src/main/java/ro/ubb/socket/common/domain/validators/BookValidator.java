package ro.ubb.socket.common.domain.validators;

import ro.ubb.socket.common.domain.Book;

import java.util.Optional;

public class BookValidator implements Validator<Book> {
    @Override
    public void validate(Book entity) throws ValidatorException {
        Optional.of(entity.getYear())
                .filter(year -> year >= 1900 && year <= 2020)
                .orElseThrow(() -> new ValidatorException("Validator Exception -> Invalid Book: year not in range [1900, 2020]"));

        Optional.of(entity.getPrice())
                .filter(price -> price > 0)
                .orElseThrow(() -> new ValidatorException("Validator Exception -> Invalid Book: price must be greater than 0"));
    }
}