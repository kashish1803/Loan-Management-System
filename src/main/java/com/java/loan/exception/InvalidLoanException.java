package com.java.loan.exception;

public class InvalidLoanException extends Exception {

    public InvalidLoanException() {
        super("Loan not found or invalid loan ID.");
    }

    public InvalidLoanException(String message) {
        super(message);
    }
}
