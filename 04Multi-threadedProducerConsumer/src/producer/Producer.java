package producer;

import queue.Message;
import queue.TaskQueue;

public class Producer implements Runnable{
    
    private TaskQueue taskQueue;
    
    public Producer(TaskQueue taskQueue){
        this.taskQueue = taskQueue;
    }

    // generate messages and add them to the queue
    @Override
    public void run(){
        for(int i = 0; i < 5; i++){
            try {
                Message message = new Message("Message " + (i+1), i+1);
                System.out.println("Producing: " + message);
                taskQueue.addMessage(message);
                Thread.sleep(1000);
            } catch(InterruptedException e){
                Thread.currentThread().interrupt(); // stop what it's doing and either stop execution or handle the interruption in a specific way
                break;
            }
            // If a thread is in the middle of a sleep() or a blocking operation, an interrupt signal is sent to it. 
            // The thread will throw an InterruptedException and enter the catch block. catching the InterruptedException isn’t enough
            // After handling the exception, you re-interrupt the thread to preserve the interrupt flag. 
            // This way, the interruption signal is passed along and can be detected by the thread if it wants to stop further processing.
            // If you catch an InterruptedException, and you want to propagate the interrupt signal to other parts of your program, you call Thread.currentThread().interrupt()

            // You could simply throw the exception to stop the thread, but the interrupt flag will be cleared when the exception is thrown. 
            // By calling Thread.currentThread().interrupt() inside the catch block, you’re explicitly re-setting the interrupt flag so that the thread remains in an interrupted state, 
            // which can be checked later or used by other parts of the program.
        }
    }
}
