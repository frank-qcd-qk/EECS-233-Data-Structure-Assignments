package week4.priorityQueue;

import java.io.File;
import java.io.FileWriter;
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
    private String filePath;

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
    private int[] getPID(int count) {
        return dataStructure.getNextAvailablePID(count);
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
    public generator(frankDS DataStructure, int maximumResource, int maximumRequests, String filePath) {
        this.dataStructure = DataStructure;
        this.maxResource = maximumResource;
        this.maxRequests = maximumRequests;
        this.filePath = filePath;
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
        return returnner;
    }

    /**
     * This method is used to make sure batch generation as well as each generated batch has the same system time
     * @param totalNum
     * @return
     */
    private Object[] batchGeneration(int totalNum) {
        Object[] returnner = new Object[totalNum + 1];

        // ! A local guard for PID resource exaustion
        int[] temp = getPID(totalNum);
        if (temp[0]==-1){
            returnner[0] = true;

        }else{
            returnner[0] = false;
        }
        // * Uniform time Stamp generation
        LocalDateTime timeStamp = getSystemTime();

        for (int i = 1; i <= totalNum; i++) {
            returnner[i] = intermediateGeneration(temp[i-1], timeStamp);
        }
        return returnner;
    }

    /**
     * This is the sepcial and customed to different methods. This is responsible for polutaing the data structure
     */
    private void populator(Object[] toBeAdded) {
        for (int i = 1; i < toBeAdded.length; i++) {
            Object[] generatedTuple = (Object[]) toBeAdded[i];
            int priority = (int) generatedTuple[2];
            dataStructure.addQueue(priority, generatedTuple);
            String output = "[Generator] Generated request: PID: " + (int) generatedTuple[0] + " RID: " + (int) generatedTuple[1]
            + " Priority: " + (int) generatedTuple[2] + " OP time: " + (int) generatedTuple[3] + " at "
            + dtf.format((LocalDateTime) generatedTuple[4]);
            //System.out.println(output);
            writeLog(output);
        }
    }

    /**
     * Runnable function for the thread to run with. Aka the sequencer.
     */
    public void run() {

        while(this.currentRequests <= this.maxRequests){
            int currentTotalRequest = generateQuant();
            do {
                try {
                    long sleepTime = getSleepTime();
                    TimeUnit.MILLISECONDS.sleep(sleepTime);
                    System.out.println("[Generator] Max Resource Hit!");
                    System.out.println("[Generator] Sleep for: "+ sleepTime+" milliseconds");
                } catch (InterruptedException e) {
                    System.out.println("System sleep error! No clue how this happened!");
                    e.printStackTrace();
                }                 
            } while (dataStructure.getCurrentAvailable()<currentTotalRequest);

            //! Edge case handling where the last generation is larger than the maxRequests, we chose to just full fill all.
            if (this.currentRequests + currentTotalRequest >= this.maxRequests){
                currentTotalRequest = this.maxRequests - this.currentRequests;
            }
            System.out.println("=================================================");
            System.out.println("[Generator] Generated Current batch Quantity is: " + currentTotalRequest);
            Object[] result = batchGeneration(currentTotalRequest);
            if ((boolean) result[0] == true || dataStructure.getCurrentAvailable()<=0) {
                System.out.println("[Generator]Current PID resource exausted... Waiting!");
                System.out.println("[Generator]Need to generate "+(this.maxRequests-this.currentRequests)+" more requests!");
            } else {
                populator(result);
                this.currentRequests += currentTotalRequest;
                System.out.println("[Generator]Need to generate "+(this.maxRequests-this.currentRequests)+" more requests!");
            }
            if(this.maxRequests - this.currentRequests ==0){
                System.out.println("[Generator] Generation Complete!");
                break;
            }
            try {
                long sleepTime = getSleepTime();
                TimeUnit.MILLISECONDS.sleep(sleepTime);
                System.out.println("[Generator] Sleep for: "+ sleepTime+" milliseconds");
            } catch (InterruptedException e) {
                System.out.println("System sleep error! No clue how this happened!");
                e.printStackTrace();
            }
        }
    }
    /**
     * File writer
     */
    private void writeLog(String tobeWritten){
        try {
            File writeFile = new File(this.filePath);
            FileWriter writer = new FileWriter(writeFile,true);
            writer.write(tobeWritten+System.lineSeparator());
            writer.close();
        } catch (Exception e) {
            System.out.println("[FATAL] Failed to write file!");
        }
    }

    /**
     * Helper function to get the sleep time
     * @return
     */
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
        int inclusiveBound = limitation+1;
        Random randomGen = new Random();
        int validation = randomGen.nextInt(inclusiveBound);
        while(validation == 0){
            validation = randomGen.nextInt(inclusiveBound);
        }
        return validation;
    }
}