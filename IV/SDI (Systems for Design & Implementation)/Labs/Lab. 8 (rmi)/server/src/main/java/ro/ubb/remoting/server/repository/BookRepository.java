package ro.ubb.remoting.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import ro.ubb.remoting.common.domain.Book;

import java.util.List;

public class BookRepository implements Repository<Long, Book> {
    @Autowired
    private JdbcOperations jdbcOperations;

//    BookRepository() {
//        jdbcOperations.
//    }

    @Override
    public void save(Book book) {
        String sql = "insert into book (isbn, title, author, publishinghouse, year, price) values (?, ?, ?, ?, ?, ?)";
        jdbcOperations.update(sql, book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPublishingHouse(), book.getYear(), book.getPrice());
    }

    @Override
    public void update(Book book) {
        String sql = "update book set isbn=?, title=?, author=?, publishinghouse=?, year=?, price=? where id=?";
        jdbcOperations.update(sql, book.getIsbn(), book.getTitle(), book.getAuthor(), book.getPublishingHouse(), book.getYear(), book.getPrice(), book.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from book where id=?";
        jdbcOperations.update(sql, id);
    }

    @Override
    public List<Book> findAll() {
        String sql = "select * from book";
        return jdbcOperations.query(sql, (resultSet, rowNo) -> {
            Long id = resultSet.getLong("id");
            String isbn = resultSet.getString("isbn");
            String title = resultSet.getString("title");
            String author = resultSet.getString("author");
            String publishingHouse = resultSet.getString("publishinghouse");
            int year = resultSet.getInt("year");
            float price = resultSet.getFloat("price");

            Book book = new Book(isbn, title, author, publishingHouse, year, price);
            book.setId(id);

            return book;
        });
    }

    @Override
    public Book findOne(Long id) {
        String sql = "select * from book where id=?";
        return jdbcOperations.queryForObject(sql, new Object[] {id}, Book.class);
    }
}
