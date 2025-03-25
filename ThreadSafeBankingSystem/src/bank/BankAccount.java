import java.util.concurrent.locks.ReentrantLock;

public class BankAccount {
    private int accountNumber;
    private double balance;
    private final ReentrantLock lock = new ReentrantLock();
    
    public BankAccount(int accountNumber, double balance){
        this.accountNumber = accountNumber;
        this.balance = balance;
    }   

    public void deposit(double amount){
        this.lock.lock();
        try {
            this.balance += amount;
            System.out.println("Deposited " + amount + " to Account " + accountNumber + ". New Balance: " + balance);
        } finally{
            lock.unlock();
        }
    }

    public void withdraw(double amount){
        this.lock.lock();
        try {
            if(amount > this.balance) {
                System.out.println("Insufficient funds in Account " + accountNumber);
                return;
            }
            this.balance -= amount;
            System.out.println("Withdrew " + amount + " from Account " + accountNumber + ". New Balance: " + balance);
        } finally{
            lock.unlock();
        }
    }

    public void transfer(BankAccount target, double amount){
        // always lock in a fixed order (lower accno first)
        BankAccount first = this.accountNumber < target.accountNumber ? this : target;
        BankAccount second = this.accountNumber < target.accountNumber ? target : this;

        first.lock.lock();
        second.lock.lock();
        try{
            if(this.balance < amount){
                System.out.println("Transfer failed: Insufficient funds in Account " + accountNumber);
                return;
            }
            this.withdraw(amount);
            target.deposit(amount);
            System.out.println("Transferred " + amount + " from Account " + this.accountNumber + " to Account " + target.accountNumber);
        } finally{
            
            second.lock.unlock();
            first.lock.unlock(); // unlock in reverse order of locking
        }

    }

    // Since you are accessing as they change, best to keep it modularised
    // if i used AtomicReference, it exposes the internal state of the class and breaks encapsulation => unintended side effects.
    public double getBalance() {
        return balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }
}