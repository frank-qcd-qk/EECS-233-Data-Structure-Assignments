package week4.customDataStructure;

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
        int resourcePool = -1;
        int maxDataStore = -1;
        int maxProcess = -1;
        String DataStructureName = "";
        String outputLogName = "";

        // ! Process the user args input

        // !Then initialize the shared data structure appropriately and spawn the
        // !Generator and Scheduler threads.
        frankDS sharedDataStructure = new frankDS(maxDataStore);


        
    }
}