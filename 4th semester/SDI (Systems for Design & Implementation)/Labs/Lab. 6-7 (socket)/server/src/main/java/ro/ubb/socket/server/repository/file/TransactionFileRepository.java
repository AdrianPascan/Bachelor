package ro.ubb.socket.server.repository.file;

import ro.ubb.socket.common.domain.Transaction;
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

public class TransactionFileRepository extends InMemoryRepositoryLongId<Transaction> {
    String filePath;

    public TransactionFileRepository(Validator<Transaction> validator, String filePath) {
        super(validator);
        this.filePath = filePath;

        readFromFile();
    }

    @Override
    public Optional<Transaction> save(Transaction entity) throws ValidatorException {
        Optional<Transaction> optional = super.save(entity);
        appendToFile(entity);
        return Optional.empty();
    }

    @Override
    public Optional<Transaction> delete(Long id) {
        Optional<Transaction> optional = super.delete(id);
        writeToFile();
        return optional;
    }

    @Override
    public Optional<Transaction> update(Transaction entity) throws ValidatorException {
        Optional<Transaction> optional = super.update(entity);
        writeToFile();
        return optional;
    }

    private void readFromFile() {
        Path path = Paths.get(filePath);

        try {
            Files.lines(path).forEach(line -> {
                        List<String> tokens = Arrays.asList(line.split(","));

                        Long id = Long.valueOf(tokens.get(0));
                        Long customerId = Long.valueOf(tokens.get(1));
                        Long bookId = Long.valueOf(tokens.get(2));

                        Transaction transaction = new Transaction(customerId, bookId);
                        transaction.setId(id);

                        try {
                            super.save(transaction);
                        } catch (ValidatorException validatorException) {
                            validatorException.printStackTrace();
                        }
                    }
            );
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        nextId.set(1 + getMaxId());
    }

    private void appendToFile(Transaction transaction) {
        Path path = Paths.get(filePath);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(
                    transaction.getId() + "," +
                            transaction.getCustomerId() + "," +
                            transaction.getBookId()
            );
            bufferedWriter.newLine();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void writeToFile() {
        Path path = Paths.get(filePath);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING)) {
            entities.values().stream().forEach(transaction -> {
                try {
                    bufferedWriter.write(
                            transaction.getId() + "," +
                                    transaction.getCustomerId() + "," +
                                    transaction.getBookId()
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