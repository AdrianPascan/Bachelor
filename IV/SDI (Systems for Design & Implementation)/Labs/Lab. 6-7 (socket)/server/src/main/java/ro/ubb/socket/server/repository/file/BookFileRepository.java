package ro.ubb.socket.server.repository.file;

import ro.ubb.socket.common.domain.Book;
import ro.ubb.socket.common.domain.validators.Validator;
import ro.ubb.socket.common.domain.validators.ValidatorException;
import ro.ubb.socket.server.repository.InMemoryRepositoryLongId;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class BookFileRepository extends InMemoryRepositoryLongId<Book> {
    String filePath;

    public BookFileRepository(Validator<Book> validator, String filePath) {
        super(validator);
        this.filePath = filePath;

        readFromFile();
    }

    @Override
    public Optional<Book> save(Book entity) throws ValidatorException {
        Optional<Book> optional = super.save(entity);
        appendToFile(entity);
        return Optional.empty();
    }

    @Override
    public Optional<Book> delete(Long id) {
        Optional<Book> optional = super.delete(id);
        writeToFile();
        return optional;
    }

    @Override
    public Optional<Book> update(Book entity) throws ValidatorException {
        Optional<Book> optional =  super.update(entity);
        writeToFile();
        return optional;
    }


    private void readFromFile() {
        Path path = Paths.get(filePath);

        try {
            Files.lines(path).forEach(line -> {
                        List<String> tokens = Arrays.asList(line.split(","));

                        Long id = Long.valueOf(tokens.get(0));
                        String isbn = tokens.get(1);
                        String title = tokens.get(2);
                        String author = tokens.get(3);
                        String publishingHouse = tokens.get(4);
                        int year = Integer.parseInt(tokens.get(5));
                        float price = Float.parseFloat(tokens.get(6));

                        Book book = new Book(isbn, title, author, publishingHouse, year, price);
                        book.setId(id);

                        try{
                            super.save(book);
                        } catch (ValidatorException validatorException) {
                            validatorException.printStackTrace();
                        }
                    }
            );

            nextId.set(1 + getMaxId());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void appendToFile(Book book) {
        Path path = Paths.get(filePath);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)){
            bufferedWriter.write(
                    book.getId() + "," +
                            book.getIsbn() + "," +
                            book.getTitle() + "," +
                            book.getAuthor() + "," +
                            book.getPublishingHouse() + "," +
                            book.getYear() + "," +
                            book.getPrice()
            );
            bufferedWriter.newLine();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void writeToFile() {
        Path path = Paths.get(filePath);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING)) {
            entities.values().stream().forEach(book -> {
                try {
                    bufferedWriter.write(
                            book.getId() + "," +
                                    book.getIsbn() + "," +
                                    book.getTitle() + "," +
                                    book.getAuthor() + "," +
                                    book.getPublishingHouse() + "," +
                                    book.getYear() + "," +
                                    book.getPrice()
                    );
                    bufferedWriter.newLine();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
            });
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}