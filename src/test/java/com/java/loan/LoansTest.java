package com.java.loan;

import static org.junit.Assert.*;

import org.junit.Test;

import com.java.loan.model.Customers;
import com.java.loan.model.LoanStatus;
import com.java.loan.model.LoanType;
import com.java.loan.model.Loans;

public class LoansTest {

	@Test
    public void testToString() {
        Customers customer = new Customers(1, "Sneha Kulkarni", "sneha.kulkarni@gmail.com", "9876543210", "Pune", 780);
        Loans loan = new Loans(101, customer, 100000, 10.0, 12, LoanType.CARLOAN, LoanStatus.Approved);

        String expected = "Loan [loanId=101, customer=" + customer.toString()
                + ", principalAmount=100000.0, interestRate=10.0, loanTerm=12, loanType=CARLOAN, loanStatus=Approved]";
        
        assertEquals(expected, loan.toString());
    }

    @Test
    public void testGettersAndSetters() {
        Customers customer = new Customers(2, "Rohan Mehta", "rohan@gmail.com", "9123456789", "Delhi", 750);
        Loans loan = new Loans();

        loan.setLoanId(102);
        loan.setCustomer(customer);
        loan.setPrincipalAmount(150000);
        loan.setInterestRate(9.5);
        loan.setLoanTerm(24);
        loan.setLoanType(LoanType.HOMELOAN);
        loan.setLoanStatus(LoanStatus.Pending);

        assertEquals(102, loan.getLoanId());
        assertEquals(customer, loan.getCustomer());
        assertEquals(150000, loan.getPrincipalAmount(), 0.01);
        assertEquals(9.5, loan.getInterestRate(), 0.01);
        assertEquals(24, loan.getLoanTerm());
        assertEquals(LoanType.HOMELOAN, loan.getLoanType());
        assertEquals(LoanStatus.Pending, loan.getLoanStatus());
    }

    @Test
    public void testConstructors() {
        Customers customer = new Customers(3, "Divya Sharma", "divya@gmail.com", "9012345678", "Bengaluru", 800);
        Loans loan = new Loans(103, customer, 200000, 8.0, 36, LoanType.CARLOAN, LoanStatus.Rejected);

        assertEquals(103, loan.getLoanId());
        assertEquals(customer, loan.getCustomer());
        assertEquals(200000, loan.getPrincipalAmount(), 0.01);
        assertEquals(8.0, loan.getInterestRate(), 0.01);
        assertEquals(36, loan.getLoanTerm());
        assertEquals(LoanType.CARLOAN, loan.getLoanType());
        assertEquals(LoanStatus.Rejected, loan.getLoanStatus());
    }

}
