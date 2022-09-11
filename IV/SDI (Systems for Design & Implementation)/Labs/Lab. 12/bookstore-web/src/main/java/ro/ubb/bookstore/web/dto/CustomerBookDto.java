package ro.ubb.bookstore.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Builder
public class CustomerBookDto {
    private CustomerDto customer;
    private BookDto book;
    private int quantity;
}
