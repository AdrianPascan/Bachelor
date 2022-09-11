package ro.ubb.domain;

import java.util.Objects;

public class Customer extends BaseEntity<Long> {
    private String firstName;
    private String lastName;
    int age;

    public Customer() {
    }

    public Customer(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object object) {
        Customer customer = (Customer) object;

        return (this == object) ||
                (object != null && getClass() == object.getClass() &&
                        age == customer.getAge() &&
                        Objects.equals(firstName, customer.firstName) &&
                        Objects.equals(lastName, customer.lastName)
                );
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName = '" + firstName + '\'' +
                ", lastName = '" + lastName + '\'' +
                ", age = " + Integer.toString(age) +
                "} " +
                super.toString();
    }
}
