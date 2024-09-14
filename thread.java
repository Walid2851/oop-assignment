import java.util.ArrayList;
import java.util.List;
import java.util.Random;


class Account{
        private String  AccountHolderName;
        private String AccountNumber;
        private double MaximumTransactionLimit;

        private double CurrentBalance=0.0;

        Account(String AccountHolderName, String AccountNumber, double MaximumTransactionLimit){
            this.AccountHolderName = AccountHolderName;
            this.AccountNumber = AccountNumber;
            this.MaximumTransactionLimit = MaximumTransactionLimit;
        }

        public String getAccountHolderName(){
            return AccountHolderName;
        }
        public String getAccountNumber(){
            return AccountNumber;
        }
        public double getMaximumTransactionLimit(){
            return MaximumTransactionLimit;
        }
        public double getCurrentBalance(){
            return CurrentBalance;
        }


        public void setCurrentBalance(double CurrentBalance){
            this.CurrentBalance = CurrentBalance;
        }
        public void setAccountHolderName(String AccountHolderName){
            this.AccountHolderName = AccountHolderName;
        }
        public void setAccountNumber(String AccountNumber){
            this.AccountNumber = AccountNumber;
        }
        public void setMaximumTransactionLimit(double MaximumTransactionLimit){
            this.MaximumTransactionLimit = MaximumTransactionLimit;
        }

}
class Transaction {
    private String accountNumber;
    private int amount;
 
    public Transaction(String accountNumber, int amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }
 
    public String getAccountNumber() {
        return accountNumber;
    }
 
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
 
    public int getAmount() {
        return amount;
    }
 
    public void setAmount(int amount) {
        this.amount = amount;
    }
}

class AccountGenerationThread extends Thread {
    private List<Account> accounts;

    public AccountGenerationThread() {
        accounts = new ArrayList<>();
    }

    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i <30; i++) {
            System.out.println("Account "+(i+1)+" is generating");
            String accountHolderName = "@user" + i;
            String accountNumber = generateAccountNumber();
            int maximumTransactionLimit = random.nextInt(1000) + 1;
            Account account = new Account(accountHolderName, accountNumber, maximumTransactionLimit);
            accounts.add(account);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println("Account Generation Thread completed.");
    }

    private String generateAccountNumber() {
        return generateRandomChar(2) + generateRandomDigits(10);
    }

    private String generateRandomChar(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char c = (char) (random.nextInt(26) + 'a');
            sb.append(c);
        }
        return sb.toString();
    }

    private String generateRandomDigits(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public List<Account> getAccounts() {
        return accounts;
    }

}

class DepositGenerationThread extends Thread{
    private List<Account> accounts;
    private List<Transaction> depositTransactions;
 
    public DepositGenerationThread(List<Account> accounts, List<Transaction> depositTransactions) {
        this.accounts = accounts;
        this.depositTransactions = depositTransactions;
    }
 
    @Override
    public void run() {
        Random random = new Random();
        for (int i = 0; i <500; i++) {
        System.out.println("Deposit Generation "+(i+1)+" is running.");
            String accountNumber = accounts.get(random.nextInt(accounts.size())).getAccountNumber();
            int depositAmount = random.nextInt(50000) + 1;
            Transaction transaction = new Transaction(accountNumber, depositAmount);
            depositTransactions.add(transaction);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println("Deposit Generation Thread completed.");
    }
    public List<Transaction> getDepositTransactions() {
        return depositTransactions;
    }
}

class WithdrawGenerationThread extends Thread {
    private List<Account> accounts;
    private List<Transaction> withdrawTransactions;
 
    public WithdrawGenerationThread(List<Account> accounts, List<Transaction> withdrawTransactions) {
        this.accounts = accounts;
        this.withdrawTransactions = withdrawTransactions;
    }
 
    @Override
    public void run() {
        Random random = new Random();
        for (int i =0; i <500; i++) {
            System.out.println("Withdraw Generation "+(i+1)+" is running.");
            String accountNumber = accounts.get(random.nextInt(accounts.size())).getAccountNumber();
            int withdrawAmount = random.nextInt(100000) + 1;
            Transaction transaction = new Transaction(accountNumber, withdrawAmount);
            withdrawTransactions.add(transaction);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Withdraw Generation Thread completed.");
    }
    public List<Transaction> getWithdrawTransactions() {
        return withdrawTransactions;
    }
}


class DepositProcessingThread1 extends Thread {
    private List<Transaction> depositTransactions;
    private List<Account> accounts;

    public DepositProcessingThread1(List<Transaction> depositTransactions, List<Account> accounts) {
        this.depositTransactions = depositTransactions;
        this.accounts = accounts;
    }

    @Override
    public void run() {
       for(int i=0;i<250;i++){
            try{
                System.out.println((i+1)+" Deposit Processing is running.(Thread1)");
                Thread.sleep(1000); 
                processDepositTransaction(depositTransactions.get(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (IndexOutOfBoundsException e){
                System.out.println("Index out of bound exception");
       }
    }
        System.out.println("Deposit Processing Thread1 completed.");
    }
    private synchronized void  processDepositTransaction(Transaction transaction) {
        try {
            // Retrieve account details based on transaction's account number
            Account account = getAccountByAccountNumber(transaction.getAccountNumber());

            // Check if deposit amount exceeds maximum transaction limit
            if (transaction.getAmount() > account.getMaximumTransactionLimit()) {
                throw new IllegalArgumentException("Maximum DepositTransaction Limit Violated for account: " + transaction.getAccountNumber());
            }

            // Update account balance with deposit amount
            double newBalance = account.getCurrentBalance() + transaction.getAmount();
            account.setCurrentBalance(newBalance);

            System.out.println("Processed deposit transaction for account: " + transaction.getAccountNumber());

        } catch (IllegalArgumentException e) {
            System.out.println("Error processing deposit transaction: " + e.getMessage());
        }
    }

    private Account getAccountByAccountNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        throw new IllegalArgumentException("Account not found for account number: " + accountNumber);
    }
}


class DepositProcessingThread2 extends Thread {
    private List<Transaction> depositTransactions;
    private List<Account> accounts;

    public DepositProcessingThread2(List<Transaction> depositTransactions, List<Account> accounts) {
        this.depositTransactions = depositTransactions;
        this.accounts = accounts;
    }

    @Override
    public void run() {
        for(int i=250;i<500;i++){
            try {
                System.out.println((i+1)+" Deposit Processing is running.(Thread2)");
                Thread.sleep(800); // Simulate processing time
                processDepositTransaction(depositTransactions.get(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (IndexOutOfBoundsException e){
                System.out.println("Index out of bound exception");
            }
        }
        System.out.println("Deposit Processing Thread2 completed.");
    }

    private synchronized void  processDepositTransaction(Transaction transaction) {
        try {
            Account account = getAccountByAccountNumber(transaction.getAccountNumber());

            if (transaction.getAmount() > account.getMaximumTransactionLimit()) {
                throw new IllegalArgumentException("Maximum DepositTransaction Limit Violated for account : " + transaction.getAccountNumber());
            }

            double newBalance = account.getCurrentBalance() + transaction.getAmount();
            account.setCurrentBalance(newBalance);

            System.out.println("Processed deposit transaction for account: " + transaction.getAccountNumber());

        } catch (IllegalArgumentException e) {
            System.out.println("Error processing deposit transaction: " + e.getMessage());
        }
    }

    private Account getAccountByAccountNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        throw new IllegalArgumentException("Account not found for account number: " + accountNumber);
    }
}

class withdrawProcessingThread1 extends Thread{
    private List<Transaction> withdrawTransactions;
    private List<Account> accounts;

    public withdrawProcessingThread1(List<Transaction> withdrawTransactions, List<Account> accounts) {
        this.withdrawTransactions = withdrawTransactions;
        this.accounts = accounts;
    }

    @Override
    public void run() {
        for(int i=0;i<250;i++){
            try {
                System.out.println((i+1)+" Withdraw Processing is running.(Thread1)");
                Thread.sleep(1000); 
                processWithdrawTransaction(withdrawTransactions.get(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (IndexOutOfBoundsException e){
                System.out.println("Index out of bound exception");
            }
        }
        System.out.println("Withdraw Processing Thread1 completed.");
    }

    private synchronized  void processWithdrawTransaction(Transaction transaction) {
        try {
            Account account = getAccountByAccountNumber(transaction.getAccountNumber());

            if (transaction.getAmount() > account.getMaximumTransactionLimit()) {
                throw new IllegalArgumentException("Maximum WithdrawTransaction Limit Violated for account :" + transaction.getAccountNumber());
            }

            // Update account balance with withdraw amount
            double newBalance = account.getCurrentBalance() - transaction.getAmount();
            account.setCurrentBalance(newBalance);

            System.out.println("Processed withdraw transaction for account: " + transaction.getAccountNumber());

        } catch (IllegalArgumentException e) {
            System.out.println("Error processing withdraw transaction: " + e.getMessage());
        }
    }

    private Account getAccountByAccountNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        throw new IllegalArgumentException("Account not found for account number: " + accountNumber);
    }

}
class withdrawProcessingThread2 extends Thread{
    private List<Transaction> withdrawTransactions;
    private List<Account> accounts;

    public withdrawProcessingThread2(List<Transaction> withdrawTransactions, List<Account> accounts) {
        this.withdrawTransactions = withdrawTransactions;
        this.accounts = accounts;
    }

    @Override
    public void run() {
        for(int i=250;i<500;i++){
            System.out.println((i+1)+" Withdraw Processing is running.(Thread2)");
            try {
                Thread.sleep(800); // Simulate processing time
                processWithdrawTransaction(withdrawTransactions.get(i));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (IndexOutOfBoundsException e){
                System.out.println("Index out of bound exception");
            }
        }
        System.out.println("Withdraw Processing Thread2 completed.");
    }

    private synchronized void processWithdrawTransaction(Transaction transaction) {
        try {
            Account account = getAccountByAccountNumber(transaction.getAccountNumber());

            if (transaction.getAmount() > account.getMaximumTransactionLimit()) {
                throw new IllegalArgumentException("Maximum WithdrawTransaction Limit Violated for account:" + transaction.getAccountNumber());
            }

            double newBalance = account.getCurrentBalance() - transaction.getAmount();
            account.setCurrentBalance(newBalance);

            System.out.println("Processed withdraw transaction for account: " + transaction.getAccountNumber());

        } catch (IllegalArgumentException e) {
            System.out.println("Error processing withdraw transaction: " + e.getMessage());
        }
    }

    private Account getAccountByAccountNumber(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        throw new IllegalArgumentException("Account not found for account number: " + accountNumber);
    }

}

public class thread {
    public static void main(String args[]){

         List<Account> accounts = new ArrayList<>();
         List<Transaction> depositTransactions = new ArrayList<>();
         List<Transaction> withdrawTransactions = new ArrayList<>();

        AccountGenerationThread accountGenerationThread = new AccountGenerationThread();
        accountGenerationThread.start();
        try {
            accountGenerationThread.join();
        } catch (Exception e) {
            System.out.println(e);
        }

        accounts = accountGenerationThread.getAccounts();
        for (Account account : accounts) {
            System.out.println("Account Holder Name: " + account.getAccountHolderName());
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Maximum Transaction Limit: " + account.getMaximumTransactionLimit());
            System.out.println();
        }

        DepositGenerationThread depositGenerationThread = new DepositGenerationThread(accounts, depositTransactions);
        depositGenerationThread.start();
        try {
            depositGenerationThread.join();
        } catch (Exception e) {
            System.out.println(e);
        }
        depositTransactions = depositGenerationThread.getDepositTransactions();
        for (Transaction transaction : depositTransactions) {
            System.out.println("Account Number: " + transaction.getAccountNumber());
            System.out.println("Deposit Amount: " + transaction.getAmount());
            System.out.println();
        }

        WithdrawGenerationThread withdrawGenerationThread = new WithdrawGenerationThread(accounts, withdrawTransactions);
        withdrawGenerationThread.start();
        try {
            withdrawGenerationThread.join();
        } catch (Exception e) {
            System.out.println(e);
        }
        withdrawTransactions = withdrawGenerationThread.getWithdrawTransactions();
        for (Transaction transaction : withdrawTransactions) {
            System.out.println("Account Number: " + transaction.getAccountNumber());
            System.out.println("Withdraw Amount: " + transaction.getAmount());
            System.out.println();
        }

        DepositProcessingThread1 depositProcessingThread1 = new DepositProcessingThread1(depositTransactions, accounts);
        DepositProcessingThread2 depositProcessingThread2 = new DepositProcessingThread2(depositTransactions, accounts);
        withdrawProcessingThread1 withdrawProcessingThread1 = new withdrawProcessingThread1(withdrawTransactions, accounts);
        withdrawProcessingThread2 withdrawProcessingThread2 = new withdrawProcessingThread2(withdrawTransactions, accounts);

        depositProcessingThread1.start();
        depositProcessingThread2.start();
        withdrawProcessingThread1.start();
        withdrawProcessingThread2.start();   
        
        try{
            depositProcessingThread1.join();
            depositProcessingThread2.join();
            withdrawProcessingThread1.join();
            withdrawProcessingThread2.join();
        } catch (Exception e){
            System.out.println(e);
        }

    }
}