import java.util.concurrent.ConcurrentHashMap;

public class WorkCounterTask implements Runnable{
    private String chunkString;
    private ConcurrentHashMap<String, Integer> map;

    public WorkCounterTask(String chunkString, ConcurrentHashMap<String, Integer> map){
        this.chunkString = chunkString;
        this.map = map;
    }

    @Override
    public void run(){
        // tokenize your words, conv to lowercase, remove everything but alph and spaces and split according to spaces
        String[] tokens = chunkString.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+");

        for(String token : tokens){
            if(!token.isEmpty()){
                map.compute(token, (key, value) -> {
                    if(value == null){
                        return 1;
                    } else{
                        return value + 1;
                    }
                });
            }
        }
    }
}
