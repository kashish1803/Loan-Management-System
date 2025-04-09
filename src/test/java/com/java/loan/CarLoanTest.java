package com.java.loan;

import static org.junit.Assert.*;

import org.junit.Test;

import com.java.loan.model.CarLoan;
import com.java.loan.model.Customers;
import com.java.loan.model.LoanStatus;
import com.java.loan.model.LoanType;

public class CarLoanTest {

	@Test
    public void testToString() {
        Customers customer = new Customers(1, "Riya Sharma", "riya@gmail.com", "9876543210", "Chennai", 770);
        CarLoan carLoan = new CarLoan(201, customer, 300000, 9.0, 24, LoanStatus.Approved, "Honda Amaze", 700000);

        String expected = "Loan [loanId=201, customer=" + customer.toString()
                + ", principalAmount=300000.0, interestRate=9.0, loanTerm=24, loanType=CARLOAN, loanStatus=Approved]"
                + ", CarLoan [carModel=Honda Amaze, carValue=700000.0]";

        assertEquals(expected, carLoan.toString());
    }

    @Test
    public void testGettersAndSetters() {
        Customers customer = new Customers(2, "Amit Verma", "amit@gmail.com", "9123456789", "Mumbai", 750);
        CarLoan carLoan = new CarLoan();

        carLoan.setLoanId(202);
        carLoan.setCustomer(customer);
        carLoan.setPrincipalAmount(250000);
        carLoan.setInterestRate(10.5);
        carLoan.setLoanTerm(18);
        carLoan.setLoanType(LoanType.CARLOAN);
        carLoan.setLoanStatus(LoanStatus.Pending);
        carLoan.setCarModel("Maruti Swift");
        carLoan.setCarValue(600000);

        assertEquals(202, carLoan.getLoanId());
        assertEquals(customer, carLoan.getCustomer());
        assertEquals(250000, carLoan.getPrincipalAmount(), 0.01);
        assertEquals(10.5, carLoan.getInterestRate(), 0.01);
        assertEquals(18, carLoan.getLoanTerm());
        assertEquals(LoanType.CARLOAN, carLoan.getLoanType());
        assertEquals(LoanStatus.Pending, carLoan.getLoanStatus());
        assertEquals("Maruti Swift", carLoan.getCarModel());
        assertEquals(600000, carLoan.getCarValue(), 0.01);
    }

    @Test
    public void testConstructor() {
        Customers customer = new Customers(3, "Karan Mehta", "karan@gmail.com", "9988776655", "Delhi", 800);
        CarLoan carLoan = new CarLoan(203, customer, 350000, 8.5, 36, LoanStatus.Rejected, "Hyundai i20", 650000);

        assertEquals(203, carLoan.getLoanId());
        assertEquals(customer, carLoan.getCustomer());
        assertEquals(350000, carLoan.getPrincipalAmount(), 0.01);
        assertEquals(8.5, carLoan.getInterestRate(), 0.01);
        assertEquals(36, carLoan.getLoanTerm());
        assertEquals(LoanType.CARLOAN, carLoan.getLoanType());
        assertEquals(LoanStatus.Rejected, carLoan.getLoanStatus());
        assertEquals("Hyundai i20", carLoan.getCarModel());
        assertEquals(650000, carLoan.getCarValue(), 0.01);
    }

}
