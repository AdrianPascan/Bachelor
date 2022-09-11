package ro.ubb.repository.database;

import ro.ubb.domain.Transaction;
import ro.ubb.domain.validators.Validator;
import ro.ubb.domain.validators.ValidatorException;
import ro.ubb.repository.InMemoryRepository;
import ro.ubb.repository.InMemoryRepositoryLongId;
import ro.ubb.repository.sorting.Sort;
import ro.ubb.repository.sorting.SortingRepository;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Optional;
import java.util.stream.Collectors;

public class TransactionDatabaseRepository extends InMemoryRepositoryLongId<Transaction>
        implements SortingRepository<Long, Transaction>
{
    private static final String URL = "jdbc:postgresql://localhost:5432/bookStore";
    private static final String USER = System.getProperty("username");
    private static final String PASSWORD = System.getProperty("password");

    public TransactionDatabaseRepository(Validator<Transaction> validator) {
        super(validator);
        readFromDatabase();
    }

    @Override
    public Iterable<Transaction> findAll(Sort sort) {
        try {
            Field field = Class.forName("ro.ubb.domain.Transaction").getDeclaredField(sort.getFieldName());
            field.setAccessible(true);
            Iterable<Transaction> result = entities.values().stream()
                    .sorted((transaction, transaction2) -> {
                        try {
                            Comparable comparable = (Comparable) field.get(transaction);
                            Comparable comparable2 = (Comparable) field.get(transaction2);

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
    public Optional<Transaction> save(Transaction entity) throws ValidatorException {
        Optional<Transaction> optional = super.save(entity);
        addTransactionToDatabase(entity);
        return optional;
    }

    @Override
    public Optional<Transaction> delete(Long id) {
        Optional<Transaction> optional = super.delete(id);
        optional.ifPresent(transaction -> deleteTransactionFromDatabase(id));
        return optional;
    }

    @Override
    public Optional<Transaction> update(Transaction entity) throws ValidatorException {
        Optional<Transaction> optional =  super.update(entity);
        optional.ifPresent(transaction -> updateTransactionFromDatabase(entity));
        return optional;
    }

    private void readFromDatabase() {
        try {
            String sql = "select * from transaction";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long customerId = resultSet.getLong("customerid");
                Long bookId = resultSet.getLong("bookid");

                Transaction transaction = new Transaction(customerId, bookId);
                transaction.setId(id);
                super.save(transaction);
            }

            nextId.set(1 + getMaxId());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static void addTransactionToDatabase(Transaction transaction) {
        try {
            String sql = "insert into transaction (id, customerid, bookid) values(?, ?, ?)";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, transaction.getId());
            preparedStatement.setLong(2, transaction.getCustomerId());
            preparedStatement.setLong(3, transaction.getBookId());

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static void updateTransactionFromDatabase(Transaction transaction) {
        try {
            String sql = "update transaction set customerid=?, bookid=? where id=?";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, transaction.getId());
            preparedStatement.setLong(2, transaction.getCustomerId());
            preparedStatement.setLong(3, transaction.getBookId());

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static void deleteTransactionFromDatabase(Long id) {
        try {
            String sql = "delete from transaction where id=?";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}