package ro.ubb.bookstore.core.model;

import lombok.*;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Transaction extends BaseEntity<Long> {
    private Long customerId;
    private Long bookId;
}
