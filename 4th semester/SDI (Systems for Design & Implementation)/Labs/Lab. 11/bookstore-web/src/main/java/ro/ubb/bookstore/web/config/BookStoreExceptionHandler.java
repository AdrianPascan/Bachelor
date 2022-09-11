package ro.ubb.bookstore.web.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ro.ubb.bookstore.core.model.validators.BookStoreException;

@RestControllerAdvice
public class BookStoreExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({BookStoreException.class})
    public String handleBookStoreException(BookStoreException exception, WebRequest request) {
//        return new ResponseEntity<Object>(exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        return exception.getMessage();
    }
}
