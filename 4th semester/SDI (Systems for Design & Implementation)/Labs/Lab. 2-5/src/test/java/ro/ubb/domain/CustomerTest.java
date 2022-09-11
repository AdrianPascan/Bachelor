package ro.ubb.domain;

import junit.framework.TestCase;

public class CustomerTest extends TestCase {

    public void testGetFirstName() {
        Customer customer = new Customer("fName", "lName", 20);
        assertEquals("fName", customer.getFirstName());
    }

    public void testSetFirstName() {
        Customer customer = new Customer("fName", "lName", 20);
        customer.setFirstName("newName");
        assertEquals("newName", customer.getFirstName());
    }

    public void testGetLastName() {
        Customer customer = new Customer("fName", "lName", 20);
        assertEquals("lName", customer.getLastName());
    }

    public void testSetLastName() {
        Customer customer = new Customer("fName", "lName", 20);
        customer.setLastName("newName");
        assertEquals("newName", customer.getLastName());
    }

    public void testGetAge() {
        Customer customer = new Customer("fName", "lName", 20);
        assertEquals(20, customer.getAge());
    }

    public void testSetAge() {
        Customer customer = new Customer("fName", "lName", 20);
        customer.setAge(30);
        assertEquals(30, customer.getAge());
    }
}