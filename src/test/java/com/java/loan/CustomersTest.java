package com.java.loan;

import static org.junit.Assert.*;

import org.junit.Test;

import com.java.loan.model.Customers;

public class CustomersTest {

	@Test
    public void testToString() {
        Customers customer = new Customers(1, "Sneha Kulkarni", "sneha.kulkarni@gmail.com", "9876543210", "Pune", 780);
        String expected = "Customer [customerId=1, customerName=Sneha Kulkarni, email=sneha.kulkarni@gmail.com, phoneNumber=9876543210, address=Pune, creditScore=780]";
        assertEquals(expected, customer.toString());
    }

    @Test
    public void testGettersAndSetters() {
        Customers customer = new Customers();
        customer.setCustomerId(2);
        customer.setCustomerName("Rohan Sharma");
        customer.setEmail("rohan.sharma@gmail.com");
        customer.setPhoneNumber("9123456780");
        customer.setAddress("Mumbai");
        customer.setCreditScore(720);

        assertEquals(2, customer.getCustomerId());
        assertEquals("Rohan Sharma", customer.getCustomerName());
        assertEquals("rohan.sharma@gmail.com", customer.getEmail());
        assertEquals("9123456780", customer.getPhoneNumber());
        assertEquals("Mumbai", customer.getAddress());
        assertEquals(720, customer.getCreditScore());
    }

    @Test
    public void testConstructors() {
        Customers customer = new Customers();
        assertNotNull(customer);

        Customers customer2 = new Customers(3, "Anjali Mehta", "anjali.mehta@gmail.com", "9012345678", "Delhi", 800);
        assertEquals(3, customer2.getCustomerId());
        assertEquals("Anjali Mehta", customer2.getCustomerName());
        assertEquals("anjali.mehta@gmail.com", customer2.getEmail());
        assertEquals("9012345678", customer2.getPhoneNumber());
        assertEquals("Delhi", customer2.getAddress());
        assertEquals(800, customer2.getCreditScore());
    }

}
