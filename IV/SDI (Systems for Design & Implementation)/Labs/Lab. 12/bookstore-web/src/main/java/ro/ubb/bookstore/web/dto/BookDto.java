package ro.ubb.bookstore.web.dto;

import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@Builder
public class BookDto extends BaseDto {
    private String isbn;
    private String title;
    private String author;
    private float price;
}
