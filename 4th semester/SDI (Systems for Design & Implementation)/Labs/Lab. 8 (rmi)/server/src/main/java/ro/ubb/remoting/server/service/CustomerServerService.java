package ro.ubb.remoting.server.service;

import ro.ubb.remoting.common.CustomerService;
import ro.ubb.remoting.common.domain.BaseEntity;
import ro.ubb.remoting.common.domain.Customer;
import ro.ubb.remoting.common.domain.Transaction;
import ro.ubb.remoting.common.domain.validators.ValidatorException;
import ro.ubb.remoting.server.repository.CustomerRepository;
import ro.ubb.remoting.server.repository.Repository;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerServerService implements CustomerService {
    private Repository<Long, Customer> customerRepo;
    private Repository<Long, Transaction> transactionRepo;

    public CustomerServerService(Repository<Long, Customer> customerRepo, Repository<Long, Transaction> transactionRepo) {
        this.customerRepo = customerRepo;
        this.transactionRepo = transactionRepo;
    }

    public void addCustomer(Customer customer) throws ValidatorException {
        customerRepo.save(customer);
    }

    public void deleteCustomer(Long ID) throws IllegalArgumentException {
        deleteTransactionsByCustomerID(ID);
        customerRepo.deleteById(ID);
    }

    public void updateCustomer(Customer customer){
        customerRepo.update(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    public List<Customer> getCustomersByAge(int age) {
        return customerRepo.findAll().stream()
                .filter(customer -> age == customer.getAge())
                .collect(Collectors.toList());
    }

    private void deleteTransactionsByCustomerID(Long customerID) {
        List<Long> transactionIDs = transactionRepo.findAll().stream()
                .filter(transaction -> transaction.getCustomerId() == customerID)
                .map(BaseEntity::getId).collect(Collectors.toList());

        transactionIDs.forEach(ID -> transactionRepo.deleteById(ID));
    }
}
