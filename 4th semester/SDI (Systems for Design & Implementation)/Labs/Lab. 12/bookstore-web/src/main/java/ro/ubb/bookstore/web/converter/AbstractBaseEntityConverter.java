package ro.ubb.bookstore.web.converter;

import ro.ubb.bookstore.core.model.BaseEntity;
import ro.ubb.bookstore.web.dto.BaseDto;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractBaseEntityConverter<Model extends BaseEntity<Long>, Dto extends BaseDto>
        extends AbstractConverter<Model, Dto> implements ConverterBaseEntity<Model, Dto> {

    public Set<Long> convertModelsToIds(Set<Model> models) {
        return models.stream()
                .map(BaseEntity::getId)
                .collect(Collectors.toSet());
    }

    public Set<Long> convertDtosToIds(Set<Dto> dtos) {
        return dtos.stream()
                .map(BaseDto::getId)
                .collect(Collectors.toSet());
    }

//    public Set<Dto> convertModelsToDtos(Collection<Model> models) {
//        return models.stream()
//                .map(model -> convertModelToDto(model))
//                .collect(Collectors.toSet());
//    }
//
//    public Set<Model> convertDtosToModels(Set<Dto> dtos) {
//        return dtos.stream()
//                .map(dto -> convertDtoToModel(dto))
//                .collect(Collectors.toSet());
//    }
}
