package ro.ubb.bookstore.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.bookstore.core.model.Customer;
import ro.ubb.bookstore.web.dto.CustomerDto;

@Component
public class CustomerConverter extends AbstractBaseEntityConverter<Customer, CustomerDto> {
    @Override
    public Customer convertDtoToModel(CustomerDto dto) {
        Customer customer = Customer.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .age(dto.getAge())
                .build();
        customer.setId(dto.getId());
        return customer;
    }

    @Override
    public CustomerDto convertModelToDto(Customer customer) {
        CustomerDto dto = CustomerDto.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .age(customer.getAge())
                .build();
        dto.setId(customer.getId());
        return dto;
    }
}
