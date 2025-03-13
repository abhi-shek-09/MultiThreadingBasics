import consumer.Consumer;
import producer.Producer;
import queue.TaskQueue;

public class Main{
    public static void main(String[] args) {
        TaskQueue taskQueue = new TaskQueue();

        Producer producer = new Producer(taskQueue);
        Consumer consumer1 = new Consumer(taskQueue);
        Consumer consumer2 = new Consumer(taskQueue);

        // Starting threads here

        Thread producerThread = new Thread(producer);
        Thread consumerThread1 = new Thread(consumer1);
        Thread consumerThread2 = new Thread(consumer2);

        producerThread.start();
        consumerThread1.start();
        consumerThread2.start();
    }
}