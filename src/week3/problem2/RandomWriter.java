package week3.problem2;

import java.util.*;
import java.io.*;

public class RandomWriter {
    private final static boolean DEBUG = true;

    // ! Initializaion vars
    private int k, length;
    private String fileSource, outPut;
    private static boolean initComplete = false;

    private String sourceText;

    private List<ArrayList<Object>> probabilityTable = new ArrayList<ArrayList<Object>>();
    private List<String> lookupTable = new ArrayList<String>();

    // ! Performance analyzer
    private long fileReader_start, fileReader_end, fileReader_time;

    // ! Constructor
    public RandomWriter(int IO_k, int IO_length, String IO_fileSource, String IO_outPut) {
        this.k = IO_k;
        this.length = IO_length;
        this.fileSource = IO_fileSource;
        this.outPut = IO_outPut;
        initComplete = true;
    }

    public void sequentialRunner(){
        if (!initComplete){
            throw new ExceptionInInitializerError("[Fatal]Constructor Failed to populate key global variables!");
        }
        readFromSource();
        generateProbablisitics();
    }


    /**
     * readFromSource Method
     * <p>
     * This function reads a local file specified by the constructor and store its
     * content into sourceText global variable
     * </p>
     * 
     * @author Chude Qian CXQ41
     * @version 1.0
     */
    private void readFromSource() {
        fileReader_start = System.currentTimeMillis();
        try {
            File sourceInput = new File(this.fileSource); // Create file object
            FileReader reader = new FileReader(sourceInput); // Start file object reader
            StringBuffer stringBuilder = new StringBuffer(); // Create string builder object

            // * Read file:
            int charCount;
            int totalCharCount = 0;
            char[] charBuffer = new char[1]; // Create a batch read buffer of 1024
            while ((charCount = reader.read(charBuffer)) > 0) {
                stringBuilder.append(charBuffer);
                totalCharCount += charCount;
            }

            reader.close();
            this.sourceText = stringBuilder.toString();
            fileReader_end = System.currentTimeMillis();
            fileReader_time = fileReader_end - fileReader_start;

            if (DEBUG) {
                System.out.println("==========readFromSource Report==========");
                System.out.println("Observed input from file: ");
                System.out.println(sourceText);
                System.out.println("Total read characters: " + totalCharCount);
                System.out.println("Time Consumed: " + fileReader_time + " Milliseconds");
                System.out.println("==========readFromSource End==========");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[FATAL]Failure observed at file reader stage!");
        }
    }

    
    private void generateProbablisitics() {
        int sourceTextIndexer = 0;
        int lookupTableIndexer = 0;
        int probabilityTablePointer =0;
        while((sourceTextIndexer<(sourceText.length()-this.k))){
            //! Local Burnable variables
            StringBuilder segmetation = new StringBuilder(this.k);
            List<Character> temperary = new ArrayList<Character>();


            //! Sgement the characters by index
            boolean successSegment = true;
            for (int i = 0; i < this.k;i++){
                char tempChar = sourceText.charAt(sourceTextIndexer+i);
                if (Character.isLetter(tempChar)){
                    segmetation.append(tempChar);
                }else{
                    successSegment = false;
                    break;
                }
            }
            sourceTextIndexer+= this.k -1;

            //! Append to seed lookup table only if it is not a dupe
            if (successSegment){
                probabilityTablePointer=lookupTable.indexOf(segmetation.toString());
                if(probabilityTablePointer==-1){
                    lookupTable.add(segmetation.toString());
                    lookupTableIndexer++;
                    probabilityTablePointer = lookupTableIndexer;
                }
            }
        }
        if (DEBUG){
            System.out.println("==========GenerateProbablistics Debug 1==========");
            System.out.println("Current Index table looks like: ");
            System.out.println(lookupTable.toString());
            System.out.println("==========GenerateProbablistics Debug 1 END==========");
        }
    }

    // Your class should have a main method that takes the following four command
    // line arguments
    // A non-negative integer k
    // A non-negative integer length.
    // The name of an input text file source that contains more than k characters.
    // The name of an output file result.

    // Your program should validate the inputs properly.

    // If any of the command line arguments are invalid,
    // your program should write an informative error message to System.err and
    // terminate.

    public static void main(String args[]) {
        RandomWriter wordGen = new RandomWriter(2, 3,
                "/home/frank/Desktop/EECS233_WS/2-PS_WS/2019_summer_233_group1/src/week3/problem2/source.txt",
                "result.txt");
        wordGen.sequentialRunner();
    }
}