package ro.ubb.socket.server.repository;

import ro.ubb.socket.common.domain.BaseEntity;
import ro.ubb.socket.common.domain.validators.BookStoreException;
import ro.ubb.socket.common.domain.validators.Validator;
import ro.ubb.socket.common.domain.validators.ValidatorException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryRepository<ID, T extends BaseEntity<ID>> implements Repository<ID, T> {

    protected Map<ID, T> entities;
    private Validator<T> validator;

    public InMemoryRepository(Validator<T> validator) {
        this.validator = validator;
        entities = new HashMap<>();
    }

    @Override
    public Optional<T> findOne(ID id) {
        Optional<ID> optionalID = Optional.ofNullable(id);
        optionalID.orElseThrow(() -> new BookStoreException("ID is null"));

        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public Iterable<T> findAll() {
        return entities.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        Optional<T> optionalID = Optional.ofNullable(entity);
        optionalID.orElseThrow(() -> new IllegalArgumentException("Entity is null"));

        validator.validate(entity);
        return Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));
    }

    @Override
    public Optional<T> delete(ID id) {
        Optional<ID> optionalID = Optional.ofNullable(id);
        optionalID.orElseThrow(() -> new IllegalArgumentException("ID is null"));

        return Optional.ofNullable(entities.remove(id));
    }

    @Override
    public Optional<T> update(T entity) throws ValidatorException {
        Optional<T> optionalID = Optional.ofNullable(entity);
        optionalID.orElseThrow(() -> new IllegalArgumentException("Entity is null"));

        validator.validate(entity);
        return Optional.ofNullable(entities.computeIfPresent(entity.getId(), (k, v) -> entity));
    }
}
