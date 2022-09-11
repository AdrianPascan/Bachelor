package ro.ubb.bookstore.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomersDto {
    private Set<CustomerDto> customers;
}
