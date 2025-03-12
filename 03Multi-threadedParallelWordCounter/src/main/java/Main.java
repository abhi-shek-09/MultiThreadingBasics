import java.io.BufferedWriter;
import java.io.File;
import java.util.Map;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ConcurrentHashMap;

public class Main{
    private static int NUM_THREADS = 4;
    private static String INPUT_FILEPATH = "../../../resources/wordfile.txt";
    private static String OUTPUT_FILEPATH = "../../../resources/mapped_words.txt";
    public static void main(String[] args) throws Exception{
        // List<String> content = Files.readAllLines(Path.of("../../../resources/wordfile.txt")); it works but it loads the full file in memory (bad for large files OutOfMemoryError )
        // the below one is more memory efficient since we put the string into a stream and later load it into memory
        ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
        FileProcessor.readFileChunks(Path.of(INPUT_FILEPATH), map, NUM_THREADS);

        File file = new File(OUTPUT_FILEPATH);
        BufferedWriter bf = null;

        try {
            bf = new BufferedWriter(new FileWriter(file));

            for (Map.Entry<String, Integer> entry : map.entrySet()){
                bf.write(entry.getKey() + " : " + entry.getValue());
                bf.newLine();
            }   
            bf.flush(); // to clear out the writer
        } catch (IOException e){
            e.printStackTrace();
        } finally{
            try{
                bf.close();
            } catch(Exception e){
                
            }
        }
    }
}