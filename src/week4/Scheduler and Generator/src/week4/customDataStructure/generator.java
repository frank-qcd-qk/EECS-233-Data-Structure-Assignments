package week4.customDataStructure;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class generator implements Runnable {
    private frankDS dataStructure;
    private int maxResource;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private int maxRequests;
    private int currentRequests;

    /**
     * The quantity controller The Generator thread will intermittently add 1 to 5
     * requests to the shared data structure
     * 
     * @return a selected amount of the number of request we need to generate
     */
    private int generateQuant() {
        return randomGenerator(5);
    }

    /**
     * Process_id is an integer with range 1-1000 ! Note that since we need to avoid
     * duplication of the PID, we create a pid resource pool so that we can avoid
     * the duplication of checking which PID value is available
     * 
     * @return obtained PID from the datastrcture *The returned value can be -1
     *         representing the PID pool is exausted
     */
    private int getPID() {
        return dataStructure.getNextAvailablePID();
    }

    /**
     * resource_id is an integer from 1 to the inputparameter,
     * 
     * @return
     */
    private int generateRID() {
        return randomGenerator(maxResource);
    }

    /**
     * priority is an integer with range 1-10
     * 
     * @return the randomly generated priority level
     */
    private int generatePriorityLevel() {
        return randomGenerator(10);
    }

    /**
     * time is an integer from 1 to 1000
     * 
     * @return the randomly generated operation time
     */
    private int generateOPtime() {
        return randomGenerator(1000);
    }

    /**
     * Get the system time
     * 
     * @return the system time
     */
    private LocalDateTime getSystemTime() {
        LocalDateTime report = LocalDateTime.now();
        return report;
    }

    /**
     * constructor
     * 
     * @param DataStructure   Pass in the datastructure that is used
     * @param maximumResource pass in the maximum resource option so that we are
     *                        bounded
     */
    public generator(frankDS DataStructure, int maximumResource, int maximumRequests) {
        this.dataStructure = DataStructure;
        this.maxResource = maximumResource;
        this.maxRequests = maximumRequests;
    }

    /**
     * For example, suppose a tuple is (5, 2, 10, 10, 12345). This means that
     * process_id 5, which has priority 10, is requesting access to resource 2 for
     * 10 milliseconds, and this request was submitted at system time 12345.
     * 
     * Note that all requests submitted by the Generator in a single batch should
     * have the same insertion_time.
     * 
     * @param pid       // The validated PID input
     * @param timeStamp // The uniform batch System Timestamp
     * @return
     */
    private Object[] intermediateGeneration(int pid, LocalDateTime timeStamp) {
        Object[] returnner = new Object[5];
        returnner[0] = pid;
        returnner[1] = generateRID();
        returnner[2] = generatePriorityLevel();
        returnner[3] = generateOPtime();
        returnner[4] = timeStamp;
        System.out.println("[Generator] Generated request: PID: " + (int) returnner[0] + " RID: " + (int) returnner[1]
                + " Priority: " + (int) returnner[2] + " OP time: " + (int) returnner[3] + " at "
                + dtf.format((LocalDateTime) returnner[4]));
        return returnner;
    }

    private Object[] batchGeneration(int totalNum) {
        Object[] returnner = new Object[totalNum + 1];

        // ! A local guard for PID resource exaustion
        int[] pidList = new int[totalNum];
        for (int i = 0; i < totalNum; i++) {
            int temp = getPID();
            pidList[i] = temp;
            if (temp == -1) {
                returnner[0] = true;
            } else {
                returnner[0] = false;
            }
        }
        // * Uniform time Stamp generation
        LocalDateTime timeStamp = getSystemTime();

        for (int i = 1; i <= totalNum; i++) {
            returnner[i] = intermediateGeneration(pidList[i - 1], timeStamp);
        }
        return returnner;
    }

    private void populator(Object[] toBeAdded) {
        for (int i = 1; i < toBeAdded.length; i++) {
            Object[] generatedTuple = (Object[]) toBeAdded[i];
            int priority = (int) generatedTuple[2];
            dataStructure.addQueue(priority, generatedTuple);
        }
    }

    public void run() {

        while(this.currentRequests <= this.maxRequests){
            int currentTotalRequest = generateQuant();
            //! Edge case handling where the last generation is larger than the maxRequests, we chose to just full fill all.
            if (this.currentRequests + currentTotalRequest >= this.maxRequests){
                currentTotalRequest = this.maxRequests - this.currentRequests;
            }
            System.out.println("=================================================");
            System.out.println("[Generator] Generated Current batch Quantity is: " + currentTotalRequest);
            Object[] result = batchGeneration(currentTotalRequest);
            if ((boolean) result[0] == true || dataStructure.getCurrentAvailable()<=0) {
                System.out.println("Current PID resource exausted... Waiting!");
                System.out.println("Need to generate "+(this.maxRequests-this.currentRequests)+" more requests!");
            } else {
                populator(result);
                this.currentRequests += currentTotalRequest;
                System.out.println("Need to generate "+(this.maxRequests-this.currentRequests)+" more requests!");
            }
    
            try {
                long sleepTime = getSleepTime();
                TimeUnit.MILLISECONDS.sleep(sleepTime);
                System.out.println("[Generator] Sleep for: "+ sleepTime+" milliseconds");
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }

    private Long getSleepTime(){
        return Long.valueOf(randomGenerator(100));
    }

    // !++++++++++++++++++++Helper Functions+++++++++++++++++++++
    /**
     * randomGenerator Method
     * <p>
     * This function generates a random number within the limitation provided
     * </p>
     * 
     * @since Week1 package
     * @author Chude Qian CXQ41
     * @param limitation
     * @return int a random integer within the limitation (Modified to be non zero)
     */
    private int randomGenerator(int limitation) {
        Random randomGen = new Random();
        int validation = randomGen.nextInt(limitation);
        while(validation == 0){
            validation = randomGen.nextInt(limitation);
        }
        return validation;
    }
}