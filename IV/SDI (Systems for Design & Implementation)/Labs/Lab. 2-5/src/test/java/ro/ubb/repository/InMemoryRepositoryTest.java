package ro.ubb.repository;

import junit.framework.TestCase;
import org.junit.Before;
import ro.ubb.domain.Customer;
import ro.ubb.domain.validators.CustomerValidator;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryRepositoryTest extends TestCase {
    private Repository<Long,Customer> customerMockRepository;
    @Before
    public void setUp() throws Exception {
        super.setUp();
        customerMockRepository = new InMemoryRepository<Long, Customer>(new CustomerValidator());
        Customer customer1 = new Customer("fName1", "lName1", 21);
        customer1.setId((long) 1);
        Customer customer2 = new Customer("fName2", "lName2", 22);
        customer2.setId((long) 2);
        customerMockRepository.save(customer1);
        customerMockRepository.save(customer2);
    }

    public void tearDown() throws Exception {
    }

    public void testFindOne() {
        Optional<Customer> customer = customerMockRepository.findOne(1L);
        assertEquals(21, customer.get().getAge());
    }

    public void testFindAll() {
        AtomicInteger counter = new AtomicInteger(0);
        customerMockRepository.findAll().forEach((customer) -> counter.addAndGet(1));
        assertEquals(2, counter.get());
    }

    public void testSave() {
        Customer customer3 = new Customer("fName3", "lName3", 23);
        customer3.setId(3L);
        customerMockRepository.save(customer3);

        AtomicInteger counter = new AtomicInteger(0);
        customerMockRepository.findAll().forEach((customer) -> counter.addAndGet(1));
        assertEquals(3, counter.get());

        counter.set(0);
        Customer customer4 = new Customer("fName4", "lName4", 24);
        customer4.setId(3L);
        customerMockRepository.save(customer4);
        customerMockRepository.findAll().forEach((customer) -> counter.addAndGet(1));
        assertEquals(3, counter.get());
    }

    public void testDelete() {
        customerMockRepository.delete(2L);
        AtomicInteger counter = new AtomicInteger(0);
        customerMockRepository.findAll().forEach((customer) -> counter.addAndGet(1));
        assertEquals(1, counter.get());
    }

    public void testUpdate() {
        Customer customer = new Customer("fName", "lName", 20);
        customer.setId(1L);
        customerMockRepository.update(customer);
        assertEquals(20, customerMockRepository.findOne(1L).get().getAge());
    }
}