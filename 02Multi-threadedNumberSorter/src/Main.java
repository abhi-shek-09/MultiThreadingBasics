import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        int ARRAY_SIZE = 1_000_000;
        int[] numbers = generateRandomArray(ARRAY_SIZE);
        for (int i = 0; i < 10; i++) {
            System.out.print(numbers[i] + " ");
        }

        System.out.println("\n");

        List<int[]> chunkList = new ArrayList<>();
        chunkList = splitArray(numbers);

        List<Thread> threads = new ArrayList<>();
        for (int[] chunk : chunkList) {
            SortWorker sortWorker = new SortWorker(chunk);
            Thread thread = new Thread(sortWorker);
            threads.add(thread);
            thread.start();
        }

        try {
            for (Thread thread : threads) {
                thread.join(); // blocks the main thread until the thread it's called on has finished executing
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Merger merger = new Merger();
        int[] finalArr = merger.merge(chunkList);

        System.out.println("Sorting completed.\n");
        for (int i = 0; i < 10; i++) {
            System.out.print(finalArr[i] + " ");
        }
    }

    private static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        // ThreadLocalRandom for better performance
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(0, size);
        }
        return array;
    }

    private static List<int[]> splitArray(int[] numbers) {
        List<int[]> chunkList = new ArrayList<>();
        int numCores = Runtime.getRuntime().availableProcessors();
        int chunkSize = numbers.length / numCores;
        for (int i = 0; i < numCores; i++) {
            int start = i * chunkSize;
            int end = (i == numCores - 1) ? numbers.length : start + chunkSize;
            int[] chunk = new int[end - start];
            System.arraycopy(numbers, start, chunk, 0, chunk.length);
            chunkList.add(chunk);
        }
        return chunkList;
    }
}