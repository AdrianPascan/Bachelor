package ro.ubb.bookstore.web.converter;

import ro.ubb.bookstore.core.model.BaseEntity;
import ro.ubb.bookstore.web.dto.BaseDto;

public interface ConverterBaseEntity<Model extends BaseEntity<Long>, Dto extends BaseDto>
        extends Converter<Model, Dto> {
}
