package ro.ubb.remoting.common.domain;

import java.io.Serializable;
import java.util.Objects;

public class Book extends BaseEntity<Long> implements Serializable {
    private String isbn;
    private String title;
    private String author;
    private String publishingHouse;
    private int year;
    private float price;

    public Book() {
    }

    public Book(String isbn, String title, String author, String publishingHouse, int year, float price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publishingHouse = publishingHouse;
        this.year = year;
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return year == book.year &&
                Float.compare(book.price, price) == 0 &&
                isbn.equals(book.isbn) &&
                title.equals(book.title) &&
                author.equals(book.author) &&
                publishingHouse.equals(book.publishingHouse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, author, publishingHouse, year, price);
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishingHouse='" + publishingHouse + '\'' +
                ", year=" + year +
                ", price=" + price +
                "} " +
                super.toString();
    }
}
