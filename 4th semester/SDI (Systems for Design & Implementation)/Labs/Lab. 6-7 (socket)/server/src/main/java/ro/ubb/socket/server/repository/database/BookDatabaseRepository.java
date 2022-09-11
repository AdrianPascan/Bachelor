package ro.ubb.socket.server.repository.database;

import ro.ubb.socket.common.domain.Book;
import ro.ubb.socket.common.domain.validators.Validator;
import ro.ubb.socket.common.domain.validators.ValidatorException;
import ro.ubb.socket.server.repository.InMemoryRepositoryLongId;
import ro.ubb.socket.server.repository.sorting.Sort;
import ro.ubb.socket.server.repository.sorting.SortingRepository;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookDatabaseRepository extends InMemoryRepositoryLongId<Book>
        implements SortingRepository<Long, Book>
{
    private static final String URL = "jdbc:postgresql://localhost:5432/bookStore";
    private static final String USER = System.getProperty("username");
    private static final String PASSWORD = System.getProperty("password");

    public BookDatabaseRepository(Validator<Book> validator) {
        super(validator);
        readFromDatabase();
    }

    @Override
    public Iterable<Book> findAll(Sort sort) {
        try {
            Field field = Class.forName("ro.ubb.socket.common.domain.Book").getDeclaredField(sort.getFieldName());
            field.setAccessible(true);
            Iterable<Book> result = entities.values().stream()
                    .sorted((book, book2) -> {
                        try {
                            Comparable comparable = (Comparable) field.get(book);
                            Comparable comparable2 = (Comparable) field.get(book2);

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
    public Optional<Book> save(Book entity) throws ValidatorException {
        Optional<Book> optional = super.save(entity);
        addBookToDatabase(entity);
        return optional;
    }

    @Override
    public Optional<Book> delete(Long id) {
        Optional<Book> optional = super.delete(id);
        optional.ifPresent(book -> deleteBookFromDatabase(id));
        return optional;
    }

    @Override
    public Optional<Book> update(Book entity) throws ValidatorException {
        Optional<Book> optional =  super.update(entity);
        optional.ifPresent(book -> updateBookFromDatabase(entity));
        return optional;
    }

    private void readFromDatabase() {
        try {
            String sql = "select * from book";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String isbn = resultSet.getString("isbn");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String publishingHouse = resultSet.getString("publishinghouse");
                int year = resultSet.getInt("year");
                float price = resultSet.getFloat("price");

                Book book = new Book(isbn, title, author, publishingHouse, year, price);
                book.setId(id);
                super.save(book);
            }

            nextId.set(1 + getMaxId());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static void addBookToDatabase(Book book) {
        try {
            String sql = "insert into book (id, isbn, title, author, publishinghouse, year, price) values(?, ?, ?, ?, ?, ?, ?)";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setLong(1, book.getId());
            preparedStatement.setString(2, book.getIsbn());
            preparedStatement.setString(3, book.getTitle());
            preparedStatement.setString(4, book.getAuthor());
            preparedStatement.setString(5, book.getPublishingHouse());
            preparedStatement.setInt(6, book.getYear());
            preparedStatement.setFloat(7, book.getPrice());

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static void updateBookFromDatabase(Book book) {
        try {
            String sql = "update book set isbn=?, title=?, author=?, publishinghouse=?, year=?, price=? where id=?";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getPublishingHouse());
            preparedStatement.setInt(5, book.getYear());
            preparedStatement.setFloat(6, book.getPrice());
            preparedStatement.setLong(7, book.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private static void deleteBookFromDatabase(Long id) {
        try {
            String sql = "delete from book where id=?";
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
