package week4.linkedList;

import java.util.LinkedList;

public class Group1_Queue<T> {
    private static final boolean DEBUG = false;
    private int length;
    private int elements = 0;
    private LinkedList<Object> linkedListQueue;

    public boolean Empty() {
        return ((this.elements == 0) ? true : false);
    }

    public boolean Full() {
        return ((this.elements == this.length) ? true : false);
    }

    public void init(int user_queueLength) {
        this.length = user_queueLength;
        this.linkedListQueue = new LinkedList<Object>();
    }

    public void Enqueue(Object something) {
        if (Full()) {
            throw new IllegalAccessError("[Fatal Error!]The current queue is full!");
        } else {
            this.linkedListQueue.addLast(something);
            this.elements++;
        }
    }

    public Object Dequeue() {
        if (Empty()) {
            throw new IllegalAccessError("[Fatal Error!]The current queue is empty!");
        } else {
            Object returnner = this.linkedListQueue.removeFirst();
            this.elements--;
            return returnner;
        }
    }

    public Object Peek() {
        if (Empty()) {
            return null;
        } else {
            Object returnner = this.linkedListQueue.peekFirst();
            if(DEBUG){
                System.out.println("[Queue Debug]Current Value: " + returnner.toString());
            }
            return returnner;
        }
    }

    public int getSize(){
        return this.elements;
    }

    @Override
    public String toString() {
        String returnner = "";
        for (int i = 0; i < this.linkedListQueue.size(); i++) {
            returnner += this.linkedListQueue.get(i).toString();
        }
        return returnner;
    }

    public Group1_Queue() {
    };
    public Group1_Queue(int size){
        init(size);
        this.length = size;
    }
}