package ro.ubb.repository.file;

import ro.ubb.domain.Customer;
import ro.ubb.domain.validators.Validator;
import ro.ubb.domain.validators.ValidatorException;
import ro.ubb.repository.InMemoryRepository;
import ro.ubb.repository.InMemoryRepositoryLongId;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class CustomerFileRepository extends InMemoryRepositoryLongId<Customer> {
    String filePath;

    public CustomerFileRepository(Validator<Customer> validator, String filePath) {
        super(validator);
        this.filePath = filePath;

        readFromFile();
    }

    @Override
    public Optional<Customer> save(Customer entity) throws ValidatorException {
        Optional<Customer> optional = super.save(entity);
        appendToFile(entity);
        return Optional.empty();
    }

    @Override
    public Optional<Customer> delete(Long id) {
        Optional<Customer> optional = super.delete(id);
        writeToFile();
        return optional;
    }

    @Override
    public Optional<Customer> update(Customer entity) throws ValidatorException {
        Optional<Customer> optional =  super.update(entity);
        writeToFile();
        return optional;
    }

    private void readFromFile() {
        Path path = Paths.get(filePath);

        try {
            Files.lines(path).forEach(line -> {
                List<String> tokens = Arrays.asList(line.split(","));

                Long id = Long.valueOf(tokens.get(0));
                String firstName = tokens.get(1);
                String lastName = tokens.get(2);
                int age = Integer.parseInt(tokens.get(3));

                Customer customer = new Customer(firstName, lastName, age);
                customer.setId(id);

                try{
                    super.save(customer);
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

    private void appendToFile(Customer customer) {
        Path path = Paths.get(filePath);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)){
            bufferedWriter.write(
                    customer.getId() + "," +
                        customer.getFirstName() + "," +
                        customer.getLastName() + "," +
                        customer.getAge()
            );
            bufferedWriter.newLine();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void writeToFile() {
        Path path = Paths.get(filePath);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING)) {
            entities.values().forEach(customer -> {
                try {
                    bufferedWriter.write(
                            customer.getId() + "," +
                                    customer.getFirstName() + "," +
                                    customer.getLastName() + "," +
                                    customer.getAge()
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
