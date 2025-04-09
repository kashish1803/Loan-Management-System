CREATE DATABASE LoanManagementDB;
USE LoanManagementDB;


-- Customers table
CREATE TABLE Customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone_number VARCHAR(15) NOT NULL,
    address VARCHAR(255) NOT NULL,
    credit_score INT NOT NULL
);

-- Base Loan table
CREATE TABLE Loans (
    loan_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    principal_amount DOUBLE NOT NULL,
    interest_rate DOUBLE NOT NULL,
    loan_term INT NOT NULL,
    loan_type ENUM('HomeLoan', 'CarLoan') NOT NULL,
    loan_status ENUM('Pending', 'Approved', 'Rejected') NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES Customers(customer_id)
);

-- HomeLoan table (specific to HomeLoan)
CREATE TABLE HomeLoan (
    loan_id INT PRIMARY KEY,
    property_address VARCHAR(255) NOT NULL,
    property_value DOUBLE NOT NULL,
    FOREIGN KEY (loan_id) REFERENCES Loans(loan_id) ON DELETE CASCADE
);

-- CarLoan table (specific to CarLoan)
CREATE TABLE CarLoan (
    loan_id INT PRIMARY KEY,
    car_model VARCHAR(100) NOT NULL,
    car_value DOUBLE NOT NULL,
    FOREIGN KEY (loan_id) REFERENCES Loans(loan_id) ON DELETE CASCADE
);


INSERT INTO Customers (customer_name, email, phone_number, address, credit_score) VALUES
('Arjun Mehta', 'arjun.mehta@gmail.com', '9876543210', 'MG Road, Bengaluru', 710),
('Neha Reddy', 'neha.reddy@gmail.com', '8765432109', 'Banjara Hills, Hyderabad', 640),
('Rakesh Patel', 'rakesh.patel@gmail.com', '7654321098', 'Navrangpura, Ahmedabad', 685),
('Divya Sharma', 'divya.sharma@gmail.com', '9543210876', 'Sector 22, Chandigarh', 760),
('Ankit Desai', 'ankit.desai@gmail.com', '9123456789', 'Kothrud, Pune', 600);

INSERT INTO Customers (customer_name, email, phone_number, address, credit_score)
VALUES ('Karan Verma', 'karan.verma@gmail.com', '9876543211', 'Andheri East, Mumbai', 780);

INSERT INTO Customers (customer_name, email, phone_number, address, credit_score)
VALUES ('Sneha Kulkarni', 'sneha.kulkarni@gmail.com', '9876543210', 'Koregaon Park, Pune', 780);

INSERT INTO Loans (customer_id, principal_amount, interest_rate, loan_term, loan_type, loan_status) VALUES
(1, 800000, 8.5, 60, 'HomeLoan', 'Pending'),
(2, 450000, 9.0, 36, 'CarLoan', 'Pending'),
(3, 320000, 10.0, 24, 'CarLoan', 'Pending'),
(4, 950000, 7.9, 84, 'HomeLoan', 'Pending'),
(5, 280000, 9.5, 18, 'CarLoan', 'Pending');


INSERT INTO HomeLoan (loan_id, property_address, property_value) VALUES
(1, '12 Indiranagar, Bengaluru', 850000),
(4, '9 Sector 17, Chandigarh', 1000000);


INSERT INTO CarLoan (loan_id, car_model, car_value) VALUES
(2, 'Maruti Baleno', 470000),
(3, 'Hyundai Venue', 350000),
(5, 'Tata Tiago', 290000);


select * from Customers;
select * from Loans;
select * from Carloan;
select * from Homeloan;


ALTER TABLE Loans MODIFY loan_type ENUM('HOMELOAN', 'CARLOAN');
ALTER TABLE Loans 
MODIFY loan_status ENUM('Pending', 'Approved', 'Rejected', 'Paid') NOT NULL;


