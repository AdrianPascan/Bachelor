package ro.ubb.bookstore.web.converter;

import org.springframework.stereotype.Component;
import ro.ubb.bookstore.core.model.BaseEntity;
import ro.ubb.bookstore.core.model.Book;
import ro.ubb.bookstore.web.dto.BookDto;

import java.util.stream.Collectors;

@Component
public class BookConverter extends AbstractBaseEntityConverter<Book, BookDto> {
    @Override
    public Book convertDtoToModel(BookDto dto) {
        Book book = Book.builder()
                .isbn(dto.getIsbn())
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .price(dto.getPrice())
                .build();
        book.setId(dto.getId());
        return book;
    }

    @Override
    public BookDto convertModelToDto(Book book) {
        BookDto dto = BookDto.builder()
                .isbn(book.getIsbn())
                .title(book.getTitle())
                .author(book.getAuthor())
                .price(book.getPrice())
                .build();
        dto.setId(book.getId());
        return dto;
    }
}
