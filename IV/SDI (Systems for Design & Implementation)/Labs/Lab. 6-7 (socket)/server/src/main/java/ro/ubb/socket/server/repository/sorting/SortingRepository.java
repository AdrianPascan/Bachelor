package ro.ubb.socket.server.repository.sorting;

import ro.ubb.socket.common.domain.BaseEntity;
import ro.ubb.socket.server.repository.Repository;

import java.io.Serializable;

public interface SortingRepository<ID extends Serializable, T extends BaseEntity<ID>> extends Repository<ID, T> {
    Iterable<T> findAll(Sort sort);
}
