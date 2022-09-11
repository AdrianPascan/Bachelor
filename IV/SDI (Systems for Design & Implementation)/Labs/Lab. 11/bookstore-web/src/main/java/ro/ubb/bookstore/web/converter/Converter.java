package ro.ubb.bookstore.web.converter;

import ro.ubb.bookstore.core.model.BaseEntity;
import ro.ubb.bookstore.web.dto.BaseDto;

public interface Converter<Model extends BaseEntity<Long>, Dto extends BaseDto> {
    Model convertDtoToModel(Dto dto);
    Dto convertModelToDto(Model model);
}