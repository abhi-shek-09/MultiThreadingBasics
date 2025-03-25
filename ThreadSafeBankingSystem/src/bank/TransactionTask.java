import java.util.Random;

public class TransactionTask implements Runnable {
    private final Bank bank;
    private final int accountNumber;
    private final int[] allAccountNumbers;
    private final Random random = new Random();

    public TransactionTask(Bank bank, int accountNumber, int[] allAccountNumbers) {
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.allAccountNumbers = allAccountNumbers;
    }

    @Override
    public void run() {
        int transactionType = random.nextInt(3); // 0 deposit, 1 withdraw, 2 transfer
        double amount = Math.round((10 + (100 - 10) * random.nextDouble()) * 100) / 100.0; 

        switch (transactionType) {
            case 0:
                bank.deposit(accountNumber, amount);
                break;
            case 1:
                bank.withdraw(accountNumber, amount);
                break;
            case 2:
                if (allAccountNumbers.length < 2) {
                    System.out.println("Transfer failed: Not enough accounts in the system.");
                    return;
                }
                int targetAccount;
                do {
                    targetAccount = allAccountNumbers[random.nextInt(allAccountNumbers.length)];
                } while (targetAccount == accountNumber);
                bank.transfer(accountNumber, targetAccount, amount);
                break;
            default:
                System.out.println("Invalid transaction type: " + transactionType);
        }
    }
}