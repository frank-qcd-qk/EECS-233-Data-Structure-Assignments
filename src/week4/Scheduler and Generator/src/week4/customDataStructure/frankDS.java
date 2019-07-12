package week4.customDataStructure;

import java.util.PriorityQueue;
import java.util.Queue;

public class frankDS{

    private Object[] prioritizedQueue = new Object[10]; //The orderized priority Queue
    private Queue pidPool = new PriorityQueue<Integer>(1000);

    public frankDS(int maximumSize){
        for (int i  = 0; i<1000;i++){
            pidPool.add(i);
        }
        for (Object frankQueue : prioritizedQueue) {
            frankQueue = new PriorityQueue<>();
        }
    }

    public int getNextAvailablePID(){
        return pidPool.remove();
    }
}