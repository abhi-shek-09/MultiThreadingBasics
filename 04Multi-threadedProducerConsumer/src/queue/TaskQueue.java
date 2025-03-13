package queue;
import java.util.concurrent.PriorityBlockingQueue;

public class TaskQueue {
    private PriorityBlockingQueue<Message> pq;

    public TaskQueue(){
        pq = new PriorityBlockingQueue<>();
    }

    public void addMessage(Message message){
        pq.add(message);
    }

    // public Message takeMessage(){
    //     Message highPriorityMessage = pq.poll(); returns null immediately if the queue is empty, better if you want to check for a message and immediately move on
    //     return highPriorityMessage;
    // }

    // the above message would have been fine if we werent implementing parallel processing
    // here we have to use pq.take()

    // take() in PriorityBlockingQueue is blocking waits indefinitely for an ele if the queue is empty
    // also throws an InterruptedException if the thread calling take() is interrupted while it's waiting for an item to be available in the queue.
    public Message takeMessage() throws InterruptedException {
        return pq.take();
    }
}
