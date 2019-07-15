package week4.priorityQueue;

import java.time.LocalDateTime;
import java.util.PriorityQueue;

public class frankDS {

    private PriorityQueue<task> priorityQueueTask;
    private Group1_Queue<Integer> pidPool;
    private Group1_Queue<Integer> resourcePool;
    private int maxSize;
    private int currentSize;
    private int maxResource;
    private int procecssedCount;
    private int leftOverSpace;

    public frankDS(int maximumSize, int maxResource) {
        // ! Set initialization for statistics within the datastructure
        this.maxSize = maximumSize;
        this.maxResource = maxResource;
        this.currentSize = 0;
        this.procecssedCount = 0;

        // ! This populates the PID pool so that we don't need to let the generator to
        // ! worry which PID ID is in use or not
        pidPool = new Group1_Queue<Integer>(this.maxSize);
        for (int i = 0; i < 1000; i++) {
            pidPool.Enqueue(i);
        }

        // ! THis populates the resource pool so that the scheduler can grasp the value
        // easily
        resourcePool = new Group1_Queue<Integer>(this.maxResource);
        for (int i = 1; i <= this.maxResource; i++) {
            System.out.println("ResourcePool Added " + i);
            resourcePool.Enqueue(i);
        }
        // ! This partpopulates the priotized queue
        this.priorityQueueTask = new PriorityQueue<task>(this.maxSize, new queueComparator());
    }

    // ***************** Scheduler Functions **********************/
    /**
     * Provide the system with an available resource ID
     * @return -1 if the system has no available resource
     * @return a resource ID
     */
    public synchronized int getNextAvailableResourceID(){
        return ((resourcePool.Empty())?-1 : (int) resourcePool.Dequeue());
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
        System.out.println("Current Queue: "+this.priorityQueueTask.size());
        PriorityQueue<task> clone = new PriorityQueue<task>(this.maxSize, new queueComparator());
        boolean firstTime = true;
        while (this.priorityQueueTask.peek()!=null){
            task tempTask = this.priorityQueueTask.remove();
            if(tempTask.RID == RID && firstTime){
                returnner[0] = tempTask.PID;
                returnner[1] = tempTask.RID;
                returnner[2] = tempTask.P_level;
                returnner[3] = tempTask.OP_Time;
                returnner[4] = tempTask.SysRequestTime;
                firstTime = false;
            }else{
                clone.add(tempTask);
            } 
        }
        if(returnner[0]!=null){
            pidPool.Enqueue(returnner[0]);
        }
        this.priorityQueueTask.clear();
        this.priorityQueueTask = clone;
        System.out.println("After Op: "+this.priorityQueueTask.size());
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
    public synchronized void addQueue(int priority, Object[] interestingQueue) {
        if (priority > 10) {
            throw new ArrayIndexOutOfBoundsException("Priority is out of bound of 10!");
        }

        if (this.currentSize >= this.maxSize) {
            throw new ArrayIndexOutOfBoundsException("Current Data Structure is full!");
        }

        task temp = new task();
        temp.PID = (int)interestingQueue[0];
        temp.RID = (int)interestingQueue[1];
        temp.P_level = (int)interestingQueue[2];
        temp.OP_Time = (int)interestingQueue[3];
        temp.SysRequestTime = (LocalDateTime)interestingQueue[4];
        this.priorityQueueTask.add(temp);
        this.currentSize++;
        // System.out.println("DS report: Current size is: "+this.currentSize);
    }


    //*******************Useful Statistics************************/
    /**
     * 
     * @return processed_count a value that stores how many the system has processed
     */
    public synchronized int getProcecssedCount() {
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
        this.leftOverSpace = this.maxSize - this.currentSize;
        return this.leftOverSpace;
    }
}