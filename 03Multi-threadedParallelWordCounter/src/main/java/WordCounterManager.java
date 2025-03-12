import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class WordCounterManager {
    public static void ManageWordMapping(List<String> content, ConcurrentHashMap<String, Integer> map, int NUM_THREADS){
        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<?>> futures = new ArrayList<>();
        String chunk = String.join(" ", content);
        WorkCounterTask counter = new WorkCounterTask(chunk, map);
        futures.add(executor.submit(counter));

        try {
            for (Future<?> future : futures) {
                future.get();  // Wait for task completion
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Shutdown the executor gracefully
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }
    }
}
