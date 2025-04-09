package com.java.loan.dao;


import com.java.loan.exception.InvalidLoanException;
import com.java.loan.model.*;
import com.java.loan.util.ConnectionHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ILoanRepositoryImpl implements ILoanRepository {

    private Connection connection;
    private PreparedStatement pst;

    @Override
    public boolean applyLoan(Loans loan, String extraInfo1, double extraInfo2) {
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "INSERT INTO Loans (customer_id, principal_amount, interest_rate, loan_term, loan_type, loan_status) VALUES (?, ?, ?, ?, ?, ?)";
            pst = connection.prepareStatement(cmd, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, loan.getCustomer().getCustomerId());
            pst.setDouble(2, loan.getPrincipalAmount());
            pst.setDouble(3, loan.getInterestRate());
            pst.setInt(4, loan.getLoanTerm());
            pst.setString(5, loan.getLoanType().name());
            pst.setString(6, LoanStatus.Pending.name());

            int rows = pst.executeUpdate();

            if (rows > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int loanId = rs.getInt(1);

                    if (loan.getLoanType() == LoanType.CARLOAN) {
                        String carSql = "INSERT INTO CarLoan (loan_id, car_model, car_value) VALUES (?, ?, ?)";
                        PreparedStatement carPst = connection.prepareStatement(carSql);
                        carPst.setInt(1, loanId);
                        carPst.setString(2, extraInfo1);
                        carPst.setDouble(3, extraInfo2);
                        carPst.executeUpdate();
                    } else if (loan.getLoanType() == LoanType.HOMELOAN) {
                        String homeSql = "INSERT INTO HomeLoan (loan_id, property_address, property_value) VALUES (?, ?, ?)";
                        PreparedStatement homePst = connection.prepareStatement(homeSql);
                        homePst.setInt(1, loanId);
                        homePst.setString(2, extraInfo1);
                        homePst.setDouble(3, extraInfo2);
                        homePst.executeUpdate();
                    }

                    System.out.println("✅ Loan application submitted successfully.");
                    System.out.println("🆔 Your Loan ID is: " + loanId);
                    return true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }




    @Override
    public List<Loans> viewAllLoans() {
        List<Loans> loanList = new ArrayList<>();
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "SELECT * FROM Loans";
            pst = connection.prepareStatement(cmd);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Loans loan = mapLoan(rs);
                loanList.add(loan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loanList;
    }

    @Override
    public Loans getLoanById(int loanId) throws InvalidLoanException {
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "SELECT * FROM Loans WHERE loan_id = ?";
            pst = connection.prepareStatement(cmd);
            pst.setInt(1, loanId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return mapLoan(rs);
            } else {
                throw new InvalidLoanException("Loan ID " + loanId + " not found.");
            }

        } catch (InvalidLoanException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidLoanException("Error fetching loan from database.");
        }
    }

    @Override
    public String checkLoanStatus(int loanId) throws InvalidLoanException {
        Loans loan = getLoanById(loanId);

        // If already marked as Paid, return it as-is
        if (loan.getLoanStatus() == LoanStatus.Paid) {
            return "Paid";
        }

        int creditScore = loan.getCustomer().getCreditScore();
        LoanStatus status = (creditScore > 650) ? LoanStatus.Approved : LoanStatus.Rejected;

        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "UPDATE Loans SET loan_status = ? WHERE loan_id = ?";
            pst = connection.prepareStatement(cmd);
            pst.setString(1, status.name());
            pst.setInt(2, loanId);
            pst.executeUpdate();
            return status.name();

        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidLoanException("Unable to update loan status.");
        }
    }


    @Override
    public double calculateInterest(int loanId) throws InvalidLoanException {
        Loans loan = getLoanById(loanId);
        return calculateInterest(loan.getPrincipalAmount(), loan.getInterestRate(), loan.getLoanTerm());
    }

    @Override
    public double calculateInterest(double principal, double rate, int term) {
        return (principal * rate * term) / 1200;
    }

    @Override
    public double calculateEMI(int loanId) throws InvalidLoanException {
        Loans loan = getLoanById(loanId);
        return calculateEMI(loan.getPrincipalAmount(), loan.getInterestRate(), loan.getLoanTerm());
    }

    @Override
    public double calculateEMI(double principal, double annualRate, int months) {
        double monthlyRate = annualRate / 12 / 100;
        return (principal * monthlyRate * Math.pow(1 + monthlyRate, months)) /
                (Math.pow(1 + monthlyRate, months) - 1);
    }

    @Override
    public boolean repayLoan(int loanId, double amount) throws InvalidLoanException {
        Loans loan = getLoanById(loanId);

        double emi = calculateEMI(loanId);
        if (amount < emi) {
            System.out.println("❌ Repayment amount is less than one EMI. Cannot process payment.");
            return false;
        }

        // Total due = principal + total interest
        double totalInterest = calculateInterest(loanId);
        double totalDue = loan.getPrincipalAmount() + totalInterest;

        if (loan.getLoanStatus() == LoanStatus.Paid) {
            System.out.println("✅ Loan is already fully paid.");
            return false;
        }

        int emisPaid = (int) (amount / emi);
        System.out.println("✅ " + emisPaid + " EMI(s) paid successfully for Loan ID " + loanId + ".");

        // Check if the user has fully repaid the loan
        if (amount >= totalDue) {
            try {
                connection = ConnectionHelper.getConnection();
                String updateStatus = "UPDATE Loans SET loan_status = 'PAID' WHERE loan_id = ?";
                PreparedStatement pst = connection.prepareStatement(updateStatus);
                pst.setInt(1, loanId);
                pst.executeUpdate();

                System.out.println("🎉 Loan ID " + loanId + " is now fully paid!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }



    // Helper method to map ResultSet to Loan object
    private Loans mapLoan(ResultSet rs) throws SQLException {
        Loans loan = new Loans();

        int customerId = rs.getInt("customer_id");
        Customers customer = fetchCustomerById(customerId); // Fetch full customer

        loan.setLoanId(rs.getInt("loan_id"));
        loan.setCustomer(customer);
        loan.setPrincipalAmount(rs.getDouble("principal_amount"));
        loan.setInterestRate(rs.getDouble("interest_rate"));
        loan.setLoanTerm(rs.getInt("loan_term"));
        loan.setLoanType(LoanType.valueOf(rs.getString("loan_type")));
        loan.setLoanStatus(LoanStatus.valueOf(rs.getString("loan_status")));

        return loan;
    }
    
    private Customers fetchCustomerById(int customerId) {
        Customers customer = new Customers();
        try {
            connection = ConnectionHelper.getConnection();
            String cmd = "SELECT * FROM Customers WHERE customer_id = ?";
            PreparedStatement pst = connection.prepareStatement(cmd);
            pst.setInt(1, customerId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                customer.setCustomerId(rs.getInt("customer_id"));
                customer.setCustomerName(rs.getString("customer_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setAddress(rs.getString("address"));
                customer.setCreditScore(rs.getInt("credit_score")); // important!
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }


}

