package com.java.loan.dao;

import com.java.loan.model.Loans;
import com.java.loan.exception.InvalidLoanException;

import java.util.List;

public interface ILoanRepository {

    boolean applyLoan(Loans loan, String extraInfo1, double extraInfo2);

    List<Loans> viewAllLoans();

    Loans getLoanById(int loanId) throws InvalidLoanException;

    String checkLoanStatus(int loanId) throws InvalidLoanException;

    double calculateInterest(int loanId) throws InvalidLoanException;

    double calculateInterest(double principal, double rate, int tenure);

    double calculateEMI(int loanId) throws InvalidLoanException;

    double calculateEMI(double principal, double annualRate, int months);

    boolean repayLoan(int loanId, double amount) throws InvalidLoanException;
}
