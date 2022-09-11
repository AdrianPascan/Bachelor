package ro.ubb.bookstore.core.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"customerBooks"})
@Builder
public class Book extends BaseEntity<Long> {
    @Column(name = "isbn", nullable = false)
    @Getter
    @Setter
    private String isbn;

    @Column(name = "title", nullable = false)
    @Getter
    @Setter
    private String title;

    @Column(name = "author", nullable = false)
    @Getter
    @Setter
    private String author;

    @Column(name = "price", nullable = false)
    @Getter
    @Setter
    @Positive(message = "BookStore Exception -> Book's price must be positive.")
    private Float price;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Setter
    private Set<CustomerBook> customerBooks = new HashSet<>();

    public Set<CustomerBook> getCustomerBooks() {
        return customerBooks == null ? new HashSet<>() : customerBooks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
