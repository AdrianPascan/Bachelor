package ro.ubb.repository.database;

import ro.ubb.domain.Customer;
import ro.ubb.domain.validators.Validator;
import ro.ubb.domain.validators.ValidatorException;
import ro.ubb.repository.InMemoryRepository;
import ro.ubb.repository.InMemoryRepositoryLongId;
import ro.ubb.repository.sorting.Sort;
import ro.ubb.repository.sorting.SortingRepository;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CustomerDatabaseRepository extends InMemoryRepositoryLongId<Customer>
                                        implements SortingRepository<Long, Customer>
{
    private static final String URL = "jdbc:postgresql://localhost:5432/bookStore";
    private static final String USER = System.getProperty("username");
    private static final String PASSWORD = System.getProperty("password");

    public CustomerDatabaseRepository(Validator<Customer> validator) {
        super(validator);
        readFromDatabase();
    }

    @Override
    public Iterable<Customer> findAll(Sort sort) {
        try {
            Field field = Class.forName("ro.ubb.domain.Customer").getDeclaredField(sort.getFieldName());
            field.setAccessible(true);
            Iterable<Customer> result = entities.values().stream()
                    .sorted((customer, customer2) -> {
                        try {
                            Comparable comparable = (Comparable) field.get(customer);
                            Comparable comparable2 = (Comparable) field.get(customer2);

                            return comparable.compareTo(comparable2);
                        } catch (IllegalAccessException exception) {
                            exception.printStackTrace();
                        }
                        return 0;
                    })
                    .collect(Collectors.toList());
            field.setAccessible(false);
            return result;
        } catch (NoSuchFieldException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Customer> save(Customer entity) throws ValidatorException {
        Optional<Customer> optional = super.save(entity);
        addCustomerToDatabase(entity);
        return optional;
    }

    @Override
    public Optional<Customer> delete(Long id) {
        Optional<Customer> optional = super.delete(id);
        optional.ifPresent(customer -> deleteCustomerFromDatabase(id));
        return optional;
    }

    @Override
    public Optional<Customer> update(Customer entity) throws ValidatorException {
        Optional<Customer> optional =  super.update(entity);
        optional.ifPresent(customer -> updateCustomerFromDatabase(entity));
        return optional;
    }

    private void readFromDatabase() {
        try {
            String sql = "select * from customer";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                int age = resultSet.getInt("age");

                Customer customer = new Customer(firstName, lastName, age);
                customer.setId(id);
                super.save(customer);
            }

            nextId.set(1 + getMaxId());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static void addCustomerToDatabase(Customer customer) {
        try {
            String sql = "insert into customer (id, firstName, lastName, age) values(?, ?, ?, ?)";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, customer.getId());
            preparedStatement.setString(2, customer.getFirstName());
            preparedStatement.setString(3, customer.getLastName());
            preparedStatement.setInt(4, customer.getAge());

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static void updateCustomerFromDatabase(Customer customer) {
        try {
            String sql = "update customer set firstName=?, lastName=?, age=? where id=?";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setInt(3, customer.getAge());
            preparedStatement.setLong(4, customer.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static void deleteCustomerFromDatabase(Long id) {
        try {
            String sql = "delete from customer where id=?";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
