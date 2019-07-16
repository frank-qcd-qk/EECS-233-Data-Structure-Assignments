package week4.linkedList;

public class mainRunner {
    public static void main(String[] args) {

        /**
         * Your main application should take as input five parameters: (i) an integer
         * indicating the number of resources (range 1 to 8) (ii) an integer for the
         * maximum number of requests the data structure can store (iii) an integer for
         * the total number of requests to be processed (iv) a string with the name of
         * the shared data structure and (v) the name of an output file.
         */
        // ! Setup shared parameters
        int maxResource = 5;
        int maxDataStore = 100000;
        int maxProcess = 10000;
        String outputLogName = "/home/frank/Desktop/EECS233_WS/2-PS_WS/2019_summer_233_group1/src/week4/Scheduler and Generator/results/LinkedListLog.txt";

        // ! Process the user args input

        // !Then initialize the shared data structure appropriately and spawn the
        // !Generator and Scheduler threads.
        frankDS sharedDataStructure = new frankDS(maxDataStore, maxResource);
        Runnable generatorRunnable = new generator(sharedDataStructure, maxResource, maxProcess, outputLogName);
        Thread generatorThread = new Thread(generatorRunnable);
        generatorThread.start();
        Runnable schedulerRunnable = new scheduler(sharedDataStructure, maxResource, maxProcess, outputLogName);
        Thread schedulerThread = new Thread(schedulerRunnable);
        schedulerThread.start();
        try {
            generatorThread.join();
            schedulerThread.join();
        } catch (Exception e) {
            System.out.println("Generator Thread Failed to join!");
        }

        // sharedDataStructure.debugShowDS();
    }
}