package consumer;

import queue.Message;
import queue.TaskQueue;

public class Consumer implements Runnable{
    private TaskQueue taskQueue;
    
    public Consumer(TaskQueue taskQueue){
        this.taskQueue = taskQueue;
    }

    @Override
    public void run(){
        while(true){
            try{
                Message message = taskQueue.takeMessage();
                System.out.println("Consuming: "+ message);
                Thread.sleep(1000);
            } catch (InterruptedException e){
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}


