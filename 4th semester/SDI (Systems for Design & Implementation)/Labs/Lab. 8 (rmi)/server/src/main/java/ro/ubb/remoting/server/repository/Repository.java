package ro.ubb.remoting.server.repository;

import ro.ubb.remoting.common.domain.BaseEntity;

import java.util.List;

public interface Repository<ID, T extends BaseEntity<ID>> {
    void save(T entity);
    void update(T entity);
    void deleteById(ID id);
    List<T> findAll();
    T findOne(ID id);
}
