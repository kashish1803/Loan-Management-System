package com.java.loan.main;


import com.java.loan.dao.ILoanRepository;
import com.java.loan.dao.ILoanRepositoryImpl;
import com.java.loan.exception.InvalidLoanException;
import com.java.loan.model.*;
import java.util.List;
import java.util.Scanner;

public class LoanManagementApp {
	
	static Scanner sc;
	static ILoanRepository loanService;
	
	static {
		sc = new Scanner(System.in);
        loanService = new ILoanRepositoryImpl();
	}

    public static void main(String[] args) {

        int choice;

        do {
            System.out.println("\n=== Loan Management System ===");
            System.out.println("1. Apply for Loan");
            System.out.println("2. View All Loans");
            System.out.println("3. Get Loan by ID");
            System.out.println("4. Check Loan Status");
            System.out.println("5. Calculate Interest");
            System.out.println("6. Calculate EMI");
            System.out.println("7. Repay Loan");
            System.out.println("8. Exit");
            System.out.println("==================================");
            System.out.print("Enter your choice: ");

            choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1:
                    applyLoan();
                    break;

                case 2:
                    viewAllLoans();
                    break;

                case 3:
                    getLoanById();
                    break;

                case 4:
                    checkLoanStatus();
                    break;

                case 5:
                    calculateInterest();
                    break;

                case 6:
                    calculateEMI();
                    break;

                case 7:
                    repayLoan();
                    break;

                case 8:
                    System.out.println("Thank you for using the Loan Management System.");
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 8.");
            }

        } while (choice != 8);

        sc.close();
    }
    
    
    private static Loans validateLoanId() {
        System.out.print("Enter Loan ID: ");
        int loanId = Integer.parseInt(sc.nextLine());

        try {
            Loans loan = loanService.getLoanById(loanId);
            return loan;
        } catch (InvalidLoanException e) {
            System.out.println("❌ " + e.getMessage());
            return null;
        }
    }

    private static void applyLoan() {
        try {
            System.out.println("Enter Customer ID: ");
            int customerId = Integer.parseInt(sc.nextLine());

            System.out.println("Enter Principal Amount: ");
            double principal = Double.parseDouble(sc.nextLine());

            System.out.println("Enter Interest Rate (%): ");
            double rate = Double.parseDouble(sc.nextLine());

            System.out.println("Enter Loan Term (months): ");
            int term = Integer.parseInt(sc.nextLine());

            System.out.println("Enter Loan Type (HOMELOAN or CARLOAN): ");
            LoanType type;
            try {
                type = LoanType.valueOf(sc.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("❌ Invalid Loan Type! Please enter either HOMELOAN or CARLOAN.");
                return;
            }

            String extraInfo1;
            double extraInfo2;

            if (type == LoanType.CARLOAN) {
                System.out.print("Enter Car Model: ");
                extraInfo1 = sc.nextLine();
                System.out.print("Enter Car Value: ");
                extraInfo2 = Double.parseDouble(sc.nextLine());
            } else {
                System.out.print("Enter Property Address: ");
                extraInfo1 = sc.nextLine();
                System.out.print("Enter Property Value: ");
                extraInfo2 = Double.parseDouble(sc.nextLine());
            }

            Customers customer = new Customers();
            customer.setCustomerId(customerId);
            Loans loan = new Loans(0, customer, principal, rate, term, type, LoanStatus.Pending);

            boolean result = loanService.applyLoan(loan, extraInfo1, extraInfo2);

            if (result) {
                System.out.println("✅ Loan application completed.");
            } else {
                System.out.println("❌ Loan application failed.");
            }

        } catch (Exception e) {
            System.out.println("❌ Error while applying for loan: " + e.getMessage());
        }
    }


    private static void viewAllLoans() {
        List<Loans> loans = loanService.viewAllLoans();
        if (loans.isEmpty()) {
            System.out.println("No loans found.");
        } else {
            for (Loans loan : loans) {
                System.out.println(loan);
            }
        }
    }

    private static void getLoanById() {
        Loans loan = validateLoanId();
        if (loan != null) {
            System.out.println(loan);
        }
    }

    private static void checkLoanStatus() {
        Loans loan = validateLoanId();
        if (loan != null) {
            try {
                String status = loanService.checkLoanStatus(loan.getLoanId());
                System.out.println("Loan Status: " + status);
            } catch (InvalidLoanException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
    }

    private static void calculateInterest() {
        Loans loan = validateLoanId();
        if (loan != null) {
            try {
                double interest = loanService.calculateInterest(loan.getLoanId());
                System.out.printf("Calculated Interest: ₹%.3f%n" , interest);
            } catch (InvalidLoanException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
    }

    private static void calculateEMI() {
        Loans loan = validateLoanId();
        if (loan != null) {
            try {
                double emi = loanService.calculateEMI(loan.getLoanId());
                System.out.printf("Monthly EMI: ₹%.2f%n", emi);
            } catch (InvalidLoanException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
    }

    private static void repayLoan() {
        Loans loan = validateLoanId();
        if (loan != null) {
            try {
                System.out.println("Enter Amount to Repay: ");
                double amount = Double.parseDouble(sc.nextLine());

                boolean result = loanService.repayLoan(loan.getLoanId(), amount);
                if (!result) {
                    System.out.println("❌ Payment failed.");
                }

            } catch (InvalidLoanException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
    }
}

