package ro.ubb.bookstore.core.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "customer_book")
@IdClass(CustomerBookPK.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerBook implements Serializable {
    @Id
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Id
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    private Book book;

    @Column(name = "quantity", nullable = false)
    @Positive(message = "BookStore Exception -> Transaction's quantity must be positive.")
    private Integer quantity;

    @Override
    public String toString() {
        return "CustomerBook{" +
                "customer=" + customer.toString() +
                ", book=" + book.toString() +
                ", quantity=" + Integer.toString(quantity) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerBook that = (CustomerBook) o;
        return customer.getId().equals(that.customer.getId()) &&
                book.getId().equals(that.book.getId()) &&
                quantity.equals(that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer.getId(), book.getId(), quantity);
    }
}
