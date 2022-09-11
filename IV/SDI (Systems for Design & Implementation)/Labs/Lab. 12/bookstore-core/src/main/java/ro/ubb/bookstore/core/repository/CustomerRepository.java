package ro.ubb.bookstore.core.repository;

import org.springframework.stereotype.Repository;
import ro.ubb.bookstore.core.model.Customer;

public interface CustomerRepository extends BookStoreRepository<Customer, Long> {
}
