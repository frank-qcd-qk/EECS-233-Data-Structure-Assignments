package week4.customDataStructure;

public class Group1_Queue<T> {
    private static final boolean DEBUG = false;
    private int length;
    private int elements = 0;
    private int head = 0;
    private int tail = 0;
    private Object[] queue;

    public boolean Empty() {
        return ((this.elements == 0) ? true : false);
    }

    public boolean Full() {
        return ((this.elements == this.length) ? true : false);
    }

    public void init(int user_queueLength) {
        this.length = user_queueLength;
        this.queue = new Object[user_queueLength];
    }

    public void Enqueue(Object something) {
        if (Full()) {
            throw new IllegalAccessError("[Fatal Error!]The current queue is full!");
        } else {
            this.queue[this.tail] = something;
            this.tail = (this.tail + 1) % this.length;
            this.elements++;
            if (DEBUG) {
                System.out.println("[Queue Debug]Currently, head is: " + this.head + " Total Element count: "
                        + this.elements + " Tail is: " + this.tail);
            }
        }
    }

    public Object Dequeue() {
        if (Empty()) {
            throw new IllegalAccessError("[Fatal Error!]The current queue is empty!");
        } else {
            Object returnner = this.queue[this.head];
            this.head = (this.head + 1) % this.length;
            this.elements--;
            if (DEBUG) {
                System.out.println("[Queue Debug]Currently, head is: " + this.head + " Total Element count: "
                        + this.elements + " Tail is: " + this.tail);
            }
            return returnner;
        }
    }

    public Object Peek() {
        if (Empty()) {
            throw new IllegalAccessError("[Fatal Error!]The current queue is empty!");
        } else {
            Object returnner = this.queue[this.head];
            System.out.println("[Queue Debug]Current Value: " + returnner.toString());
            return returnner;
        }
    }

    @Override
    public String toString() {
        String returnner = "";
        for (int i = 0; i < this.queue.length; i++) {
            returnner += this.queue[i].toString();
        }
        return returnner;
    }

    public Group1_Queue() {
    };
}