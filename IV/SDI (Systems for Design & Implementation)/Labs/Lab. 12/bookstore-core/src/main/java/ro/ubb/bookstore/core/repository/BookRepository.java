package ro.ubb.bookstore.core.repository;

import org.springframework.stereotype.Repository;
import ro.ubb.bookstore.core.model.Book;

public interface BookRepository extends BookStoreRepository<Book, Long> {
}
