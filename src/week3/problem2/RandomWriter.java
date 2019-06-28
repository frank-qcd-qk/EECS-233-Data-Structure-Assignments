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

    private ArrayList<ArrayList<Character>> probabilityTable = new ArrayList<ArrayList<Character>>();
    private ArrayList<String> lookupTable = new ArrayList<String>();

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

    public void sequentialRunner() {
        if (!initComplete) {
            throw new ExceptionInInitializerError("[Fatal]Constructor Failed to populate key global variables!");
        }
        readFromSource();
        System.out.println();
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
        int sourceTextIndexer = 0; // Responsible for indexing across the imported string
        int lookupTableIndexer = 0; // Responsible for telling the system a specific combo's adress in lookup table
        int probabilityTablePointer = 0; // Respobsible for telling the probability table pointer where to look at
        while ((sourceTextIndexer < (sourceText.length() - this.k))) {

            // ! Local Burnable variables
            StringBuilder segmetation = new StringBuilder(this.k);
            ArrayList<Character> temperary = new ArrayList<Character>();

            // ! Sgement the characters by index
            boolean successSegment = true;
            int spaceCount = 0;
            for (int i = 0; i < this.k; i++) {
                char tempChar = sourceText.charAt(sourceTextIndexer + i);
                // * Avoid any potential issue with special characters in the text.
                if (Character.isLetter(tempChar)) {
                    segmetation.append(tempChar);
                } else {
                    // * Space handling
                    if ((tempChar == ' ') && (Character.isLetter(sourceText.charAt(sourceTextIndexer + i + 1)))) {
                        segmetation.append(tempChar);
                    } else if ((tempChar == ' ') && ((sourceText.charAt(sourceTextIndexer + i + 1) == ' '))) {
                        // * Treat multiple space as a single space
                        while (sourceText.charAt(sourceTextIndexer + i) == ' ') {
                            sourceTextIndexer++;
                        }
                        segmetation.append(tempChar);
                    } else {
                        successSegment = false;
                        break;
                    }
                }
            }
            sourceTextIndexer++;

            // ! Append to seed lookup table only if it is not a dupe
            if (successSegment) {
                probabilityTablePointer = lookupTable.indexOf(segmetation.toString());
                if (probabilityTablePointer == -1) {
                    lookupTable.add(segmetation.toString());
                    probabilityTablePointer = lookupTableIndexer;
                    lookupTableIndexer++;
                    // ! Add probability list
                    temperary.add(sourceText.charAt(sourceTextIndexer + this.k-1));
                    probabilityTable.add(temperary);
                } else {
                    temperary = probabilityTable.get(probabilityTablePointer);
                    temperary.add(sourceText.charAt(sourceTextIndexer + this.k-1));

                }

            }
        }

        if (DEBUG) {
            System.out.println("==========GenerateProbablistics Debug 1==========");
            System.out.println("Current Index table looks like: ");
            System.out.println(lookupTable.toString());
            System.out.println("Current Probability Matrix Looks like: ");
            for (int i = 0; i < probabilityTable.size(); i++) {
                System.out.println("Combinataion: |"+lookupTable.get(i)+"| has: "+probabilityTable.get(i).toString());
            }
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
        RandomWriter wordGen = new RandomWriter(3, 3,
                "/home/frank/Desktop/EECS233_WS/2-PS_WS/2019_summer_233_group1/src/week3/problem2/source.txt",
                "result.txt");
        wordGen.sequentialRunner();
    }
}