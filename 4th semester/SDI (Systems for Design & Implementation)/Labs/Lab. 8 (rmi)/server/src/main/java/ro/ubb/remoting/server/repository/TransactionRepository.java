package ro.ubb.remoting.server.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import ro.ubb.remoting.common.domain.Book;
import ro.ubb.remoting.common.domain.Customer;
import ro.ubb.remoting.common.domain.Transaction;

import java.util.List;

public class TransactionRepository implements Repository<Long, Transaction> {
    @Autowired
    private JdbcOperations jdbcOperations;

    @Override
    public void save(Transaction transaction) {
        String sql = "insert into transaction (customerid, bookid) values (?, ?)";
        jdbcOperations.update(sql, transaction.getCustomerId(), transaction.getBookId());
    }

    @Override
    public void update(Transaction transaction) {
        String sql = "update transaction set customerid=?, bookid=? where id=?";
        jdbcOperations.update(sql, transaction.getCustomerId(), transaction.getBookId(), transaction.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from transaction where id=?";
        jdbcOperations.update(sql, id);
    }

    @Override
    public List<Transaction> findAll() {
        String sql = "select * from transaction";
        return jdbcOperations.query(sql, (resultSet, rowNo) -> {
            Long id = resultSet.getLong("id");
            Long customerId = resultSet.getLong("customerid");
            Long bookId = resultSet.getLong("bookid");

            Transaction transaction = new Transaction(customerId, bookId);
            transaction.setId(id);

            return transaction;
        });
    }

    @Override
    public Transaction findOne(Long id) {
        String sql = "select * from transaction where id=?";
        return jdbcOperations.queryForObject(sql, new Object[] {id}, Transaction.class);
    }
}
