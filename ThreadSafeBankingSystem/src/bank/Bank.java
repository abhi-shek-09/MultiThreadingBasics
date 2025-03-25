import java.util.concurrent.ConcurrentHashMap;

public class Bank {

    private ConcurrentHashMap<Integer, BankAccount> accounts = new ConcurrentHashMap<>();
    public void addAccount(int accountNumber, double initialBalance){
        if(accounts.containsKey(accountNumber)){
            System.out.println("Account with account number " + Integer.toString(accountNumber) + " already exists, failed to create an account");
            return;
        } 
        accounts.put(accountNumber, new BankAccount(accountNumber, initialBalance));
    }

    public BankAccount getAccount(int accountNumber) {
        return accounts.get(accountNumber);
    }

    public void deposit(int accountNumber, double amount){
        accounts.computeIfPresent(accountNumber, (k, v) -> {
            v.deposit(amount);
            return v;
        });
    }

    public void withdraw(int accountNumber, double amount){
        accounts.computeIfPresent(accountNumber, (k, v) -> {
            v.withdraw(amount);
            return v;
        });
    }

    public void transfer(int senderAccountNumber, int receiverAccountNumber, double amount){
        BankAccount sender = accounts.get(senderAccountNumber);
        BankAccount receiver = accounts.get(receiverAccountNumber);
        if (sender == null || receiver == null) {
            System.out.println("One or both accounts not found!");
            return;
        }

        sender.transfer(receiver, amount);
    }
}