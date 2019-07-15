package week4.hashTable;

import java.util.Hashtable;
import java.util.Map;

public class Group1_Queue<T> {
    private static final boolean DEBUG = false;
    private int length;
    private int elements = 0;
    private int head = 0;
    private int tail = 0;
    private Map<Integer, Object> HashQueue;

    public boolean Empty() {
        return ((this.elements == 0) ? true : false);
    }

    public boolean Full() {
        return ((this.elements == this.length) ? true : false);
    }

    public void init(int user_queueLength) {
        this.length = user_queueLength;
        this.HashQueue = new Hashtable<Integer,Object>();
    }

    public void Enqueue(Object something) {
        if (Full()) {
            throw new IllegalAccessError("[Fatal Error!]The current queue is full!");
        } else {

            this.HashQueue.put(this.tail, something);
            this.tail ++;
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
            Object returnner = this.HashQueue.remove(this.head);
            this.head ++;
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
            return null;
        } else {
            Object returnner = this.HashQueue.get(this.head);
            if (DEBUG) {
                System.out.println("[Queue Debug]Current Value: " + returnner.toString());
            }
            return returnner;
        }
    }

    public int getSize() {
        return this.elements;
    }

    @Override
    public String toString() {
        String returnner = "";
        for (int i = 0; i < this.HashQueue.size(); i++) {
            returnner += this.HashQueue.get(i).toString();
        }
        return returnner;
    }

    public Group1_Queue() {
    };

    public Group1_Queue(int size) {
        init(size);
        this.length = size;
    }
}