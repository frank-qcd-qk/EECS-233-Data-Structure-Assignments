package week4.customDataStructure;

import java.util.PriorityQueue;
import java.util.Queue;

public class frankDS {

    private Object[] prioritizedQueue = new Object[10]; // The orderized priority Queue
    private Group1_Queue<Integer> pidPool = new Group1_Queue<Integer>();

    public frankDS(int maximumSize) {
        // This populates the PID pool so that we don't need to let the generator to
        // worry which PID ID is in use or not
        pidPool.init(1000);
        for (int i = 0; i < 1000; i++) {
            pidPool.Enqueue(i);
        }

        // This part
        for (Object frankQueue : prioritizedQueue) {
            frankQueue = new Group1_Queue<Object>();
        }
    }

    public synchronized void addQueue(int priority,Object interestingQueue){
        if (priority>=10){
            throw new ArrayIndexOutOfBoundsException("Priority is out of bound of 10!");
        }
        Group1_Queue extraction = (Group1_Queue) this.prioritizedQueue[priority];
        extraction.Enqueue(interestingQueue);
    }


    /**
     * Aprocess_id which has submitted a request for any resource is blocked, i.e.
     * waiting for its request to be processed by the Scheduler. Until this happens,
     * no new requests with this process_id can be submitted for any resource.
     * 
     * @return a validated PID
     */
    public synchronized int getNextAvailablePID() {
        return ((pidPool.Empty()) ? -1 : (int) pidPool.Dequeue()); // Return -1 if the list is currently empyt, or
                                                                   // return the next available PID
    }
}