import java.util.Arrays;

public class SortWorker implements Runnable{
    int[] chunk;
    
    SortWorker(int[] chunk){
        this.chunk = chunk;
    }

    @Override
    public void run(){
        Arrays.sort(chunk);
    }
}