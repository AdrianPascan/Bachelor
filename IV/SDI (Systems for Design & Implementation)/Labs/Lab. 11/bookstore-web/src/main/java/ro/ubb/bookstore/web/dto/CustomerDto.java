package ro.ubb.bookstore.web.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class CustomerDto extends BaseDto {
    private String firstName;
    private String lastName;
    private int age;
}
