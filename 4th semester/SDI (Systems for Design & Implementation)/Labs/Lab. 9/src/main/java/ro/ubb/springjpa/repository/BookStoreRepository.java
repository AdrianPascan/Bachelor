package ro.ubb.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ro.ubb.springjpa.model.BaseEntity;

import java.io.Serializable;

@NoRepositoryBean
public interface BookStoreRepository<T extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<T, ID> {
}
