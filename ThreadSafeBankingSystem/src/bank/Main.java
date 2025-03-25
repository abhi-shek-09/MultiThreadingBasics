import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        // Create some accounts with initial balance
        bank.addAccount(101, 500);
        bank.addAccount(102, 600);
        bank.addAccount(103, 700);
        bank.addAccount(104, 800);

        int[] allAccountNumbers = {101, 102, 103, 104};
        ExecutorService executor = Executors.newFixedThreadPool(4);

        // Run multiple transactions concurrently
        for (int i = 0; i < 10; i++) {
            int accountNumber = allAccountNumbers[i % allAccountNumbers.length]; // Rotate through accounts
            executor.execute(new TransactionTask(bank, accountNumber, allAccountNumbers));
        }

        executor.shutdown();
         try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                System.out.println("Some tasks did not complete within the timeout.");
            }
        } catch (InterruptedException e) {
            System.out.println("Main thread was interrupted.");
        }
        System.out.println("All transactions completed.");
    }
}
