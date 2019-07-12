package week4.customDataStructure;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class generator implements Runnable {
    private frankDS dataStructure;
    private int maxResource;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss");

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
    public generator(frankDS DataStructure, int maximumResource) {
        this.dataStructure = DataStructure;
        this.maxResource = maximumResource;
    }

    /**
     * For example, suppose a tuple is (5, 2, 10, 10, 12345). This means that
     * process_id 5, which has priority 10, is requesting access to resource 2 for
     * 10 milliseconds, and this request was submitted at system time 12345.
     * 
     * Note that all requests submitted by the Generator in a single batch should have the same insertion_time.
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
                returnner[1] = false;
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
        for (int i = 1; i<toBeAdded.length;i++){
            Object[] generatedTuple = (Object[]) toBeAdded[i];
            int priority = (int) generatedTuple[2];
            dataStructure.addQueue(priority, generatedTuple);
        }
    }

    public void run() {
        int currentTotalRequest = generateQuant();
        Object[] result = batchGeneration(currentTotalRequest);
        if ((boolean) result[0] == true) {
            System.out.println("Current PID resource exausted... Waiting!");
        } else {
            populator(result);
        }
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
     * @return int a random integer within the limitation
     */
    private int randomGenerator(int limitation) {
        Random randomGen = new Random();
        return randomGen.nextInt(limitation);
    }
}