package week4.linkedList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class frankDS {

    private Object[] prioritizedQueue = new Object[10]; // The orderized priority Queue
    private Group1_Queue<Integer> pidPool = new Group1_Queue<Integer>();
    private int maxSize;
    private int currentSize;
    private int maxResource;

    public frankDS(int maximumSize, int maxResource) {
        // This populates the PID pool so that we don't need to let the generator to
        // worry which PID ID is in use or not
        pidPool.init(1000);
        for (int i = 0; i < 1000; i++) {
            pidPool.Enqueue(i);
        }

        this.maxSize = maximumSize;
        this.maxResource = maxResource;
        this.currentSize = 0;
        // This part
        for (int i = 0; i < 10; i++) {
            prioritizedQueue[i] = new Group1_Queue<Object>(this.maxSize);

        }
    }

    public synchronized void completeOne(){
        this.currentSize --;
    }

    public synchronized void addQueue(int priority, Object interestingQueue) {
        if (priority >= 10) {
            throw new ArrayIndexOutOfBoundsException("Priority is out of bound of 10!");
        }

        if (this.currentSize >= this.maxSize) {
            throw new ArrayIndexOutOfBoundsException("Current Data Structure is full!");
        }

        priority = priority - 1; // Prioirty does not start from 0. Boooooooo!
        Group1_Queue extraction = (Group1_Queue) this.prioritizedQueue[priority]; // A wired pointer manip
        extraction.Enqueue(interestingQueue);
        this.currentSize++;
        //System.out.println("DS report: Current size is: "+this.currentSize);
    }

    public synchronized Object[] updateNextInLine(int RID) {
        Object[] returnner = new Object[5];
        // ! Loop through resources, pick highest priority and wait time for each
        // specific resource
        boolean found = false;
        int i = 0;
        while(i < 10 && !found){

            Group1_Queue local = (Group1_Queue) this.prioritizedQueue[i];
            //System.out.println("[DS update Next]Currently looking from Priority "+(i+1));
            Group1_Queue parseThrough = new Group1_Queue<Object>(this.maxSize);

            while (local.Peek() != null) {
                Object[] tempHold = (Object[]) local.Dequeue();
                //System.out.println("Looking at PID: "+tempHold[0]+"RID: "+tempHold[1]);
                if ((int) tempHold[1] == RID) {
                    
                    //System.out.println("RID Matches! PID: "+tempHold[0]+"RID: "+tempHold[1]);
                    found = true;

                    returnner = tempHold; // put this in the next to process
                    pidPool.Enqueue((int) tempHold[0]); // Return the PID value once selected to be next processed
                    
                    while(local.Peek() != null){
                        //System.out.println("Parse Through Rest Potential");
                        parseThrough.Enqueue(local.Dequeue());
                    }

                } else {
                    //System.out.println("RID Not! PID: "+tempHold[0]+" RID: "+tempHold[1]);
                    parseThrough.Enqueue(tempHold);
                }
            }
            this.prioritizedQueue[i] = parseThrough;
            i++;
        }

        return returnner;
    }

    public synchronized int getCurrentSize(){
        return this.currentSize;
    }

    public int getCurrentAvailable() {
        return this.maxSize - this.currentSize;
    }

    public void debugShowDS() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 10; i++) {
            //System.out.println("=====Current Priority: " + i + " =====");
            Group1_Queue local = (Group1_Queue) this.prioritizedQueue[i];
            try {
                while (local.Peek() != null) {
                    Object[] queueTuple = (Object[]) local.Dequeue();
                    System.out.println("Current PID: " + queueTuple[0] + " Current RID: " + queueTuple[1]
                            + "Current P Level: " + queueTuple[2] + "Current OP time: " + queueTuple[3]
                            + " Current Insertion Time: " + dtf.format((LocalDateTime) queueTuple[4]));
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