package week4.customDataStructure;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class frankDS {

    private Object[] prioritizedQueue = new Object[10]; // The orderized priority Queue
    private Group1_Queue<Integer> pidPool = new Group1_Queue<Integer>();
    private Group1_Queue<Integer> resourcePool = new Group1_Queue<Integer>();
    private int maxSize;
    private int currentSize;
    private int maxResource;
    private int procecssedCount;
    private int leftOverSpace;

    public frankDS(int maximumSize, int maxResource) {

        // ! This populates the PID pool so that we don't need to let the generator to
        // ! worry which PID ID is in use or not
        pidPool.init(1000);
        for (int i = 0; i < 1000; i++) {
            pidPool.Enqueue(i);
        }

        // ! THis populates the resource pool so that the scheduler can grasp the value
        // easily
        resourcePool.init(maxResource);
        for (int i = 1; i <= maxResource; i++) {
            resourcePool.Enqueue(i);
        }

        // ! Set initialization for statistics within the datastructure
        this.maxSize = maximumSize;
        this.maxResource = maxResource;
        this.currentSize = 0;
        this.procecssedCount = 0;

        // ! This partpopulates the priotized queue
        for (int i = 0; i < 10; i++) {
            prioritizedQueue[i] = new Group1_Queue<Object>(this.maxSize);

        }
    }

    // ***************** Scheduler Functions **********************/
    /**
     * Provide the system with an available resource ID
     * @return -1 if the system has no available resource
     * @return a resource ID
     */
    public synchronized int getNextAvailableResourceID(){
        return (resourcePool.Empty()?-1 : (int) resourcePool.Dequeue())
    }

    /**
     * Once a resource is processed, it returns that specific resource ID
     * @param resourceID
     * @return adds back a resouce ID to a resource Pool
     */
    public synchronized void returnResource(int resourceID) {
        resourcePool.Enqueue(resourceID);
        return;
    }

    /**
     * Given by a specific Resource ID, returns back a queue.
     * @param RID
     * @return proper queue
     * @return release PID
     */
    public synchronized Object[] updateNextInLine(int RID) {
        Object[] returnner = new Object[5];
        // ! Loop through resources, pick highest priority and wait time for each
        // specific resource
        boolean found = false;
        int i = 0;
        while (i < 10 && !found) {

            Group1_Queue local = (Group1_Queue) this.prioritizedQueue[i];
            // System.out.println("[DS update Next]Currently looking from Priority "+(i+1));
            Group1_Queue parseThrough = new Group1_Queue<Object>(this.maxSize);

            while (local.Peek() != null) {
                Object[] tempHold = (Object[]) local.Dequeue();
                // System.out.println("Looking at PID: "+tempHold[0]+"RID: "+tempHold[1]);
                if ((int) tempHold[1] == RID) {

                    // System.out.println("RID Matches! PID: "+tempHold[0]+"RID: "+tempHold[1]);
                    found = true;

                    returnner = tempHold; // put this in the next to process
                    pidPool.Enqueue((int) tempHold[0]); // Return the PID value once selected to be next procecssedCount

                    while (local.Peek() != null) {
                        // System.out.println("Parse Through Rest Potential");
                        parseThrough.Enqueue(local.Dequeue());
                    }

                } else {
                    // System.out.println("RID Not! PID: "+tempHold[0]+" RID: "+tempHold[1]);
                    parseThrough.Enqueue(tempHold);
                }
            }
            this.prioritizedQueue[i] = parseThrough;
            i++;
        }

        return returnner;
    }

    /**
     * Statistically add the counters.
     */
    public synchronized void completeOne() {
        this.currentSize--;
        this.procecssedCount++;
        this.leftOverSpace = this.maxSize - this.currentSize;
    }

    // ***************** Generator Functions **********************/
    /**
     * Aprocess_id which has submitted a request for any resource is blocked, i.e.
     * waiting for its request to be procecssedCount by the Scheduler. Until this
     * happens, no new requests with this process_id can be submitted for any
     * resource.
     * 
     * @return a validated PID
     */
    public synchronized int[] getNextAvailablePID(int count) {
        int[] returnner = new int[count];
        // System.out.println("Current PID Pool size" + pidPool.getSize());
        if (pidPool.getSize() < count) {
            for (int i = 0; i < count; i++) {
                returnner[i] = -1;
            }
            return returnner;
        } else {
            for (int i = 0; i < count; i++) {
                returnner[i] = (int) pidPool.Dequeue();
            }
            return returnner;
        }
        // Return -1 if the list is currently empyt, or return the next available PID
    }

    /**
     * *Add an object/item into the data structure.
     * @param priority takes in the priority ID
     * @param interestingQueue takes in the actual queue.
     */
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
        this.procecssedCount++;
        // System.out.println("DS report: Current size is: "+this.currentSize);
    }


    //*******************Useful Statistics************************/
    /**
     * 
     * @return processed_count a value that stores how many the system has processed
     */
    public synchronized int procecssedCount() {
        return this.procecssedCount;
    }

    /**
     * 
     * @return the current size of the data structure
     */
    public synchronized int getCurrentSize() {
        return this.currentSize;
    }

    /**
     * 
     * @return the avilable space in the system
     */
    public synchronized int getCurrentAvailable() {
        this.leftOverSpace = this.maxSize - this.currentSize
        return this.leftOverSpace;
    }

    // *****************Decrepted Functions for Testing***************************/
    /**
     * @deprecated Used for quick and dirty debug to clear the queue as well as see
     *             what is in side the queue
     */
    public void debugShowDS() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 10; i++) {
            // System.out.println("=====Current Priority: " + i + " =====");
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
}