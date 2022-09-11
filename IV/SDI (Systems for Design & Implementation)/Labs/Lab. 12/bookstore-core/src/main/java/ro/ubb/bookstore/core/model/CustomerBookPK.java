package ro.ubb.bookstore.core.model;

import lombok.*;
import ro.ubb.bookstore.core.model.Book;
import ro.ubb.bookstore.core.model.Customer;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CustomerBookPK implements Serializable {
    private Customer customer;
    private Book book;
}
