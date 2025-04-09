package com.java.loan;

import static org.junit.Assert.*;

import org.junit.Test;

import com.java.loan.model.Customers;
import com.java.loan.model.HomeLoan;
import com.java.loan.model.LoanStatus;
import com.java.loan.model.LoanType;

public class HomeLoanTest {

	@Test
    public void testToString() {
        Customers customer = new Customers(1, "Neha Patil", "neha@gmail.com", "9876543210", "Nashik", 790);
        HomeLoan homeLoan = new HomeLoan(301, customer, 800000, 9.0, 120, LoanStatus.Approved, "Kothrud, Pune", 1200000);

        String expected = "Loan [loanId=301, customer=" + customer.toString()
                + ", principalAmount=800000.0, interestRate=9.0, loanTerm=120, loanType=HOMELOAN, loanStatus=Approved]"
                + ", HomeLoan [propertyAddress=Kothrud, Pune, propertyValue=1200000.0]";

        assertEquals(expected, homeLoan.toString());
    }

    @Test
    public void testGettersAndSetters() {
        Customers customer = new Customers(2, "Aakash Reddy", "aakash@gmail.com", "9123456789", "Hyderabad", 720);
        HomeLoan homeLoan = new HomeLoan();

        homeLoan.setLoanId(302);
        homeLoan.setCustomer(customer);
        homeLoan.setPrincipalAmount(950000);
        homeLoan.setInterestRate(8.75);
        homeLoan.setLoanTerm(180);
        homeLoan.setLoanType(LoanType.HOMELOAN);
        homeLoan.setLoanStatus(LoanStatus.Pending);
        homeLoan.setPropertyAddress("Whitefield, Bengaluru");
        homeLoan.setPropertyValue(1500000);

        assertEquals(302, homeLoan.getLoanId());
        assertEquals(customer, homeLoan.getCustomer());
        assertEquals(950000, homeLoan.getPrincipalAmount(), 0.01);
        assertEquals(8.75, homeLoan.getInterestRate(), 0.01);
        assertEquals(180, homeLoan.getLoanTerm());
        assertEquals(LoanType.HOMELOAN, homeLoan.getLoanType());
        assertEquals(LoanStatus.Pending, homeLoan.getLoanStatus());
        assertEquals("Whitefield, Bengaluru", homeLoan.getPropertyAddress());
        assertEquals(1500000, homeLoan.getPropertyValue(), 0.01);
    }

    @Test
    public void testConstructor() {
        Customers customer = new Customers(3, "Ravi Sharma", "ravi@gmail.com", "9988776655", "Delhi", 800);
        HomeLoan homeLoan = new HomeLoan(303, customer, 1000000, 8.25, 240, LoanStatus.Rejected, "Baner, Pune", 1800000);

        assertEquals(303, homeLoan.getLoanId());
        assertEquals(customer, homeLoan.getCustomer());
        assertEquals(1000000, homeLoan.getPrincipalAmount(), 0.01);
        assertEquals(8.25, homeLoan.getInterestRate(), 0.01);
        assertEquals(240, homeLoan.getLoanTerm());
        assertEquals(LoanType.HOMELOAN, homeLoan.getLoanType());
        assertEquals(LoanStatus.Rejected, homeLoan.getLoanStatus());
        assertEquals("Baner, Pune", homeLoan.getPropertyAddress());
        assertEquals(1800000, homeLoan.getPropertyValue(), 0.01);
    }

}
