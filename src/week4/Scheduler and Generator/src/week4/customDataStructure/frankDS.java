package week4.customDataStructure;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class frankDS {

    private Object[] prioritizedQueue = new Object[10]; // The orderized priority Queue
    private Group1_Queue<Integer> pidPool = new Group1_Queue<Integer>();
    private int maxSize;
    
    public frankDS(int maximumSize) {
        // This populates the PID pool so that we don't need to let the generator to
        // worry which PID ID is in use or not
        pidPool.init(1000);
        for (int i = 0; i < 1000; i++) {
            pidPool.Enqueue(i);
        }

        this.maxSize = maximumSize;
        // This part
        for (int i = 0; i<10;i++){
            prioritizedQueue[i] = new Group1_Queue<Object>(this.maxSize);

        }
    }

    public synchronized void addQueue(int priority,Object interestingQueue){
        if (priority>=10){
            throw new ArrayIndexOutOfBoundsException("Priority is out of bound of 10!");
        }
        priority = priority - 1;
        Group1_Queue extraction = (Group1_Queue) this.prioritizedQueue[priority];
        extraction.Enqueue(interestingQueue);
    }

    public void debugShowDS(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        for (int i = 0; i<10;i++){
            System.out.println("=====Current Priority: " + i+" =====");
            Group1_Queue local = (Group1_Queue) this.prioritizedQueue[i];
            try {
                while(local.Peek() != null){
                    Object[] queueTuple = (Object[]) local.Dequeue();
                    System.out.println("Current PID: "+queueTuple[0]+" Current RID: "+ queueTuple[1]+"Current P Level: "+queueTuple[2]+"Current OP time: "+queueTuple[3]+" Current Insertion Time: "+dtf.format((LocalDateTime) queueTuple[4]));
                }
            } catch (IllegalAccessError e) {
                System.out.println("Current Priority is Empty");
            }
            
        }
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