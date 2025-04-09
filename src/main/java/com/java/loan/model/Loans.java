package com.java.loan.model;

public class Loans {
	private int loanId;
    private Customers customer;
    private double principalAmount;
    private double interestRate;
    private int loanTerm;
    private LoanType loanType;
    private LoanStatus loanStatus;

    public Loans() {
    	
    }

    public Loans(int loanId, Customers customer, double principalAmount, double interestRate,
                 int loanTerm, LoanType loanType, LoanStatus loanStatus) {
        this.loanId = loanId;
        this.customer = customer;
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.loanTerm = loanTerm;
        this.loanType = loanType;
        this.loanStatus = loanStatus;
    }
    

    public int getLoanId() {
		return loanId;
	}

	public void setLoanId(int loanId) {
		this.loanId = loanId;
	}

	public Customers getCustomer() {
		return customer;
	}

	public void setCustomer(Customers customer) {
		this.customer = customer;
	}

	public double getPrincipalAmount() {
		return principalAmount;
	}

	public void setPrincipalAmount(double principalAmount) {
		this.principalAmount = principalAmount;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public int getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(int loanTerm) {
		this.loanTerm = loanTerm;
	}

	public LoanType getLoanType() {
		return loanType;
	}

	public void setLoanType(LoanType loanType) {
		this.loanType = loanType;
	}

	public LoanStatus getLoanStatus() {
		return loanStatus;
	}

	public void setLoanStatus(LoanStatus loanStatus) {
		this.loanStatus = loanStatus;
	}

	@Override
    public String toString() {
        return "Loan [loanId=" + loanId + ", customer=" + customer + ", principalAmount=" + principalAmount
                + ", interestRate=" + interestRate + ", loanTerm=" + loanTerm + ", loanType=" + loanType
                + ", loanStatus=" + loanStatus + "]";
    }
}
