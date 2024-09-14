import java.util.ArrayList;
import java.util.List;

// Person class
class Person {
    protected String name;
    protected String email;
    protected String phone_number;

    public Person(String name, String email, String phone_number) {
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
    }

    public String get_info() {
        return "Name: " + name + ", Email: " + email + ", Phone Number: " + phone_number;
    }
}

// Customer class
class Customer extends Person {
    protected String account_number;
    protected String bank_name;
    protected List<Account> accounts;

    public Customer(String name, String email, String phone_number, String account_number, String bank_name) {
        super(name, email, phone_number);
        this.account_number = account_number;
        this.bank_name = bank_name;
        this.accounts = new ArrayList<>();
    }

    // add an account
    public void add_account(Account account) {
        accounts.add(account);
    }

    // get customer's accounts
    public List<Account> get_accounts() {
        return accounts;
    }
}

// SinglePersonCustomer class
class SinglePersonCustomer extends Customer {
    protected String bin_number;

    public SinglePersonCustomer(String name, String email, String phone_number, String account_number, String bank_name, String bin_number) {
        super(name, email, phone_number, account_number, bank_name);
        this.bin_number = bin_number;
    }
}

// OrganizationCustomer class
class OrganizationCustomer extends Customer {
    protected String tin_number;

    public OrganizationCustomer(String name, String email, String phone_number, String account_number, String bank_name, String tin_number) {
        super(name, email, phone_number, account_number, bank_name);
        this.tin_number = tin_number;
    }
}

// Employee class
class Employee extends Person {
    protected String employee_id;
    protected String position;
    protected Bank bank;

    public Employee(String name, String email, String phone_number, String employee_id, String position, Bank bank) {
        super(name, email, phone_number);
        this.employee_id = employee_id;
        this.position = position;
        this.bank = bank;
    }

    // Method to get the bank the employee works for
    public Bank get_bank() {
        return bank;
    }
}
// Account class
class Account {
    protected String account_id;
    protected Customer customer;
    protected double balance;
    protected double interest_rate;
    protected String tax_scheme;

    public Account() {
        // Default constructor 
    }

    public Account(String account_id, Customer customer, double balance, double interest_rate, String tax_scheme) {
        this.account_id = account_id;
        this.customer = customer;
        this.balance = balance;
        this.interest_rate = interest_rate;
        this.tax_scheme = tax_scheme;
    }

    // deposit funds
    public void deposit(double amount) {
        balance += amount;
    }

    // withdraw funds
    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient balance.");
        }
    }

    //calculate interest
    public void calculate_interest(double time) {
        balance += balance * interest_rate * time;
    }

    //get current balance
    public double get_balance() {
        return balance;
    }
}


// SavingsAccount class
class SavingsAccount extends Account {
    public SavingsAccount(String account_id, Customer customer, double balance, double interest_rate, String tax_scheme) {
        super(account_id, customer, balance, interest_rate, tax_scheme);
    }
}


// SalaryAccount class
class SalaryAccount extends Account {
    // No additional attributes or methods needed
}

// Bank class
class Bank {
    protected String name;
    protected List<Customer> customers;
    protected List<Employee> employees;

    public Bank(String name) {
        this.name = name;
        this.customers = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    //  add a customer
    public void add_customer(Customer customer) {
        customers.add(customer);
    }

    //  add an employee
    public void add_employee(Employee employee) {
        employees.add(employee);
    }

    //  find a customer by account number
    public Customer find_customer(String account_number) {
        for (Customer customer : customers) {
            if (customer.account_number.equals(account_number)) {
                return customer;
            }
        }
        return null;
    }
}

// MoneySendToAccount abstract class
abstract class MoneySendToAccount {
    protected String sender;
    protected Account receiver_account;
    protected double amount;

    // Abstract method to send money
    public abstract void send();
}

// BkashMobileApp class
class BkashMobileApp extends MoneySendToAccount {
    protected String bkash_wallet_number;

    public BkashMobileApp(String sender, Account receiver_account, double amount, String bkash_wallet_number) {
        this.sender = sender;
        this.receiver_account = receiver_account;
        this.amount = amount;
        this.bkash_wallet_number = bkash_wallet_number;
    }

    // Method to send money using Bkash wallet
    public void send() {

    }
}

// EFTMobileApp class
class EFTMobileApp extends MoneySendToAccount {
    protected Account sender_account;

    public EFTMobileApp(String sender, Account receiver_account, double amount, Account sender_account) {
        this.sender = sender;
        this.receiver_account = receiver_account;
        this.amount = amount;
        this.sender_account = sender_account;
    }

    // Method to send money between accounts
    public void send() {

    }
}

// BankReceipt class
class BankReceipt extends MoneySendToAccount {
    public BankReceipt(String sender, Account receiver_account, double amount) {
        this.sender = sender;
        this.receiver_account = receiver_account;
        this.amount = amount;
    }

    public void send() {
        // Placeholder implementation for sending bank receipt
        System.out.println("Bank receipt sent successfully.");
    }
}


// Withdraw class
class Withdraw {
    protected Account account;
    protected double amount;

    public Withdraw(Account account, double amount) {
        this.account = account;
        this.amount = amount;
    }

    // Method to withdraw money
    public void withdraw() {
        account.withdraw(amount);
    }
}

// DirectChequeWithdraw class
class DirectChequeWithdraw extends Withdraw {
    protected String cheque_number;

    public DirectChequeWithdraw(Account account, double amount, String cheque_number) {
        super(account, amount);
        this.cheque_number = cheque_number;
    }
}

// BkashWithdraw class
class BkashWithdraw extends Withdraw {
    protected String bkash_wallet_number;

    public BkashWithdraw(Account account, double amount, String bkash_wallet_number) {
        super(account, amount);
        this.bkash_wallet_number = bkash_wallet_number;
    }
}

// CreditCardWithdraw class
class CreditCardWithdraw extends Withdraw {
    protected String credit_card_number;

    public CreditCardWithdraw(Account account, double amount, String credit_card_number) {
        super(account, amount);
        this.credit_card_number = credit_card_number;
    }
}


// Main class to demonstrate banking system
public class bankingSystem {
    public static void main(String[] args) {
        // Create Bank
        Bank bank = new Bank("MyBank");

        // Create Customer
        Customer customer1 = new SinglePersonCustomer("walid", "walid@example.com", "1234567890", "123456", "MyBank", "123");
        bank.add_customer(customer1);

        // Create Account for the customer
        Account account1 = new SavingsAccount("S123", customer1, 1000.0, 0.05, "Scheme A");
        customer1.add_account(account1);

        // Deposit money
        account1.deposit(500.0);

        // Withdraw money
        account1.withdraw(200.0);

        // Send money using Bkash Mobile App
        MoneySendToAccount bkashTransaction = new BkashMobileApp("walid", account1, 100.0, "0123456789");
        bkashTransaction.send();

        // Send money using EFT Mobile App
        MoneySendToAccount eftTransaction = new EFTMobileApp("walid", account1, 200.0, account1);
        eftTransaction.send();

        // Find customer by account number
        Customer foundCustomer = bank.find_customer("123456");
        if (foundCustomer != null) {
            System.out.println("Customer found: " + foundCustomer.get_info());
        } else {
            System.out.println("Customer not found.");
        }
    }
}
