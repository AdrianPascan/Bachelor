package ro.ubb.remoting.common.domain;

import java.io.Serializable;
import java.util.Objects;

public class Transaction extends BaseEntity<Long> implements Serializable {
    private Long customerId;
    private Long bookId;

    public Transaction(){
    }

    public Transaction(Long customerID, Long bookID){
        this.customerId = customerID;
        this.bookId = bookID;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerID) {
        this.customerId = customerID;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookID) {
        this.bookId = bookID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction transaction = (Transaction) o;
        return this.getId() == transaction.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, customerId);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "customerId=" + customerId +
                ", bookId=" + bookId +
                '}'
                + super.toString();
    }
}
