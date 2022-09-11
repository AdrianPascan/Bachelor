package ro.ubb.socket.server.repository;

import ro.ubb.socket.common.domain.BaseEntity;
import ro.ubb.socket.common.domain.validators.BookStoreException;
import ro.ubb.socket.common.domain.validators.Validator;
import ro.ubb.socket.common.domain.validators.ValidatorException;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryRepositoryLongId<T extends BaseEntity<Long>> extends InMemoryRepository<Long, T> {
    protected AtomicLong nextId;

    public InMemoryRepositoryLongId(Validator<T> validator) {
        super(validator);
        nextId = new AtomicLong(1);
    }

    @Override
    public Optional<T> save(T entity) throws ValidatorException {
        entity.setId(nextId.getAndIncrement());
        try {
            return super.save(entity);
        }
        catch (ValidatorException exception) {
            nextId.getAndDecrement();
            throw exception;
        }
    }

    protected long getMaxId() {
        try{
            return Long.max(0, entities.values().stream()
                    .mapToLong(entity -> entity.getId())
                    .max()
                    .getAsLong());
        }
        catch (NoSuchElementException e){
            return 1L;
        }
    }
}
