package ro.ubb.domain;

import junit.framework.TestCase;

public class BookTest extends TestCase {

    public void testGetPrice() {
        Book book = new Book("01010", "BookTitle", "BookAuthor", "PublHouse", 2000, (float) 2.10);
        assertEquals((float) 2.10, book.getPrice());
    }

    public void testSetPrice() {
        Book book = new Book("01010", "BookTitle", "BookAuthor", "PublHouse", 2000, (float) 2.10);
        book.setPrice((float) 3.10);
        assertEquals((float) 3.10, book.getPrice());
    }

    public void testGetIsbn() {
        Book book = new Book("01010", "BookTitle", "BookAuthor", "PublHouse", 2000, (float) 2.10);
        assertEquals("01010", book.getIsbn());
    }

    public void testSetIsbn() {
        Book book = new Book("01010", "BookTitle", "BookAuthor", "PublHouse", 2000, (float) 2.10);
        book.setIsbn("10101");
        assertEquals("10101", book.getIsbn());
    }

    public void testGetTitle() {
        Book book = new Book("01010", "BookTitle", "BookAuthor", "PublHouse", 2000, (float) 2.10);
        assertEquals("BookTitle", book.getTitle());

    }

    public void testSetTitle() {
        Book book = new Book("01010", "BookTitle", "BookAuthor", "PublHouse", 2000, (float) 2.10);
        book.setTitle("NewBookTitle");
        assertEquals("NewBookTitle", book.getTitle());
    }

    public void testGetAuthor() {
        Book book = new Book("01010", "BookTitle", "BookAuthor", "PublHouse", 2000, (float) 2.10);
        assertEquals("BookAuthor", book.getAuthor());
    }

    public void testSetAuthor() {
        Book book = new Book("01010", "BookTitle", "BookAuthor", "PublHouse", 2000, (float) 2.10);
        book.setAuthor("NewAuthor");
        assertEquals("NewAuthor", book.getAuthor());
    }

    public void testGetPublishingHouse() {
        Book book = new Book("01010", "BookTitle", "BookAuthor", "PublHouse", 2000, (float) 2.10);
        assertEquals("PublHouse", book.getPublishingHouse());
    }

    public void testSetPublishingHouse() {
        Book book = new Book("01010", "BookTitle", "BookAuthor", "PublHouse", 2000, (float) 2.10);
        book.setPublishingHouse("NewPublHouse");
        assertEquals("NewPublHouse", book.getPublishingHouse());
    }

    public void testGetYear() {
        Book book = new Book("01010", "BookTitle", "BookAuthor", "PublHouse", 2000, (float) 2.10);
        assertEquals(2000, book.getYear());
    }

    public void testSetYear() {
        Book book = new Book("01010", "BookTitle", "BookAuthor", "PublHouse", 2000, (float) 2.10);
        book.setYear(2020);
        assertEquals(2020, book.getYear());
    }
}