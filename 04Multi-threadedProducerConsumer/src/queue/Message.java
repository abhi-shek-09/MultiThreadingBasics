package queue;
public class Message implements Comparable<Message>{
    private String content;
    private int priority;

    public Message(String content, int priority){
        this.content = content;
        this.priority = priority;
    }

    public String getContent(){
        return content;
    }

    public int getPriority(){
        return priority;
    }

    @Override
    public int compareTo(Message other){
        return Integer.compare(this.priority, other.priority);
    }

    @Override
    public String toString(){
        return "[Priority: " + this.priority + " ] Task: " + this.content;
    }
}
