import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class FileProcessor {
    public static void readFileChunks(Path filePath, ConcurrentHashMap<String, Integer> map, int NUM_THREADS) throws Exception{
        long totalLines = Files.lines(filePath, StandardCharsets.UTF_8).count(); 
        int chunkSize = (int) Math.ceil((double) totalLines / NUM_THREADS);

        //streams allow us to process a file line by line wo loading into memory all at once
        // only keeps the current line in memory at a time
        try (Stream<String> lines = Files.lines(filePath, StandardCharsets.UTF_8)) {
            // gives us all the lines of the file in memory so we can split it into chunks, so this method was discarded
            // List<String> allLines = lines.collect(Collectors.toList());
            // int chunkSize = (int) Math.ceil((double) allLines.size() / NUM_THREADS);

            // List<String> chunks = new ArrayList<>();
            // for (int i = 0; i < allLines.size(); i += chunkSize) {
            //     chunks.add(String.join(" ", allLines.subList(i, Math.min(i+chunkSize, allLines.size()))));
            // }
            // return chunks;
            
            List<String> currentChunk = new ArrayList<>();
            lines.forEach( line -> {
                currentChunk.add(line);
                if(currentChunk.size() == chunkSize){
                    WordCounterManager.ManageWordMapping(currentChunk, map, NUM_THREADS);
                    currentChunk.clear();
                }
            });

            if (!currentChunk.isEmpty()) {
                WordCounterManager.ManageWordMapping(currentChunk, map, NUM_THREADS); // process remaining lines
            }
        }
    }
}
