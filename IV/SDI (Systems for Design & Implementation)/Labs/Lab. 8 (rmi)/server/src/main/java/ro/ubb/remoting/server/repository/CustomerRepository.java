package ro.ubb.remoting.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import ro.ubb.remoting.common.domain.Book;
import ro.ubb.remoting.common.domain.Customer;

import java.util.List;

public class CustomerRepository implements Repository<Long, Customer> {
    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public void save(Customer customer) {
        String sql = "insert into customer (firstname, lastname, age) values (?, ?, ?)";
        jdbcOperations.update(sql, customer.getFirstName(), customer.getLastName(), customer.getAge());
    }

    @Override
    public void update(Customer customer) {
        String sql = "update customer set firstname=?, lastname=?, age=? where id=?";
        jdbcOperations.update(sql, customer.getFirstName(), customer.getLastName(), customer.getAge(), customer.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from customer where id=?";
        jdbcOperations.update(sql, id);
    }

    @Override
    public List<Customer> findAll() {
        String sql = "select * from customer";
        return jdbcOperations.query(sql, (resultSet, rowNo) -> {
            Long id = resultSet.getLong("id");
            String firstName = resultSet.getString("firstname");
            String lastName = resultSet.getString("lastname");
            int age = resultSet.getInt("age");

            Customer customer = new Customer(firstName, lastName, age);
            customer.setId(id);

            return customer;
        });
    }

    @Override
    public Customer findOne(Long id) {
        String sql = "select * from customer where id=?";
        return jdbcOperations.queryForObject(sql, new Object[] {id}, Customer.class);
    }
}
