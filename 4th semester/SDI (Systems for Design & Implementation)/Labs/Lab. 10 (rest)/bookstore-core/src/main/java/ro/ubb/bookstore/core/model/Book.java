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
public class Book extends BaseEntity<Long> {
    private String isbn;
    private String title;
    private String author;
    private String publishingHouse;
    private int year;
    private float price;
}
