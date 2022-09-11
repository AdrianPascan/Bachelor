package ro.ubb.bookstore.core.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true, exclude = {"customerBooks"})
@EqualsAndHashCode(callSuper = false)
@Builder
public class Customer extends BaseEntity<Long> {

    @Column (name = "first_name", nullable = false)
    @Getter
    @Setter
    private String firstName;

    @Column (name = "last_name", nullable = false)
    @Getter
    @Setter
    private String lastName;

    @Column (name = "age", nullable = false)
    @Getter
    @Setter
    @Min(value = 14, message = "BookStore Exception -> Customer's age must be in range [14, 100].")
    @Max(value = 100, message = "BookStore Exception -> Customer's age must be in range [14, 100].")
    private Integer age;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @Setter
    private Set<CustomerBook> customerBooks = new HashSet<>();

    public Set<CustomerBook> getCustomerBooks() {
        return customerBooks == null ? new HashSet<>() : customerBooks;
    }
}
