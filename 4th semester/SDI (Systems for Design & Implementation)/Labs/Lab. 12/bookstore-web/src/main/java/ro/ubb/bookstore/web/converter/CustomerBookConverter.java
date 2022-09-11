package ro.ubb.bookstore.web.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ro.ubb.bookstore.core.model.CustomerBook;
import ro.ubb.bookstore.core.service.BookService;
import ro.ubb.bookstore.core.service.CustomerService;
import ro.ubb.bookstore.web.dto.CustomerBookDto;

@Component
public class CustomerBookConverter extends AbstractConverter<CustomerBook, CustomerBookDto> {
    @Autowired
    private CustomerConverter customerConverter;

    @Autowired
    private BookConverter bookConverter;


    @Override
    public CustomerBook convertDtoToModel(CustomerBookDto customerBookDto) {
        return CustomerBook.builder()
                .customer(customerConverter.convertDtoToModel(customerBookDto.getCustomer()))
                .book(bookConverter.convertDtoToModel(customerBookDto.getBook()))
                .quantity(customerBookDto.getQuantity())
                .build();
    }

    @Override
    public CustomerBookDto convertModelToDto(CustomerBook customerBook) {
        return CustomerBookDto.builder()
                .customer(customerConverter.convertModelToDto(customerBook.getCustomer()))
                .book(bookConverter.convertModelToDto(customerBook.getBook()))
                .quantity(customerBook.getQuantity())
                .build();
    }
}
