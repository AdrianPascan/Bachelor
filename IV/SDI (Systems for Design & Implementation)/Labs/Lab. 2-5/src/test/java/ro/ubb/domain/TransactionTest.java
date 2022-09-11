package ro.ubb.domain;

import junit.framework.TestCase;

public class TransactionTest extends TestCase {

    public void testGetCustomerId() {
        Transaction transaction = new Transaction((long)1,(long)2);
        assertEquals(Long.valueOf(1), transaction.getCustomerId());
    }

    public void testSetCustomerId() {
        Transaction transaction = new Transaction((long)1,(long)2);
        transaction.setCustomerId((long)3);
        assertEquals(Long.valueOf(3), transaction.getCustomerId());
    }

    public void testGetBookId() {
        Transaction transaction = new Transaction((long)1,(long)2);
        assertEquals(Long.valueOf(2), transaction.getBookId());
    }

    public void testSetBookId() {
        Transaction transaction = new Transaction((long)1,(long)2);
        transaction.setBookId((long)3);
        assertEquals(Long.valueOf(3), transaction.getBookId());
    }
}