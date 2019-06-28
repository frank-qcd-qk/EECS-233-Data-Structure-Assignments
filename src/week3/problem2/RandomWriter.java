package week3.problem2;

import java.util.*;
import java.io.*;
import java.util.concurrent.TimeUnit;

public class RandomWriter {
    private final static boolean DEBUG = true;

    // ! Initializaion vars
    private int k, length;
    private String fileSource, outPut;
    private static boolean initComplete = false;

    // ! File Write IO
    private String sourceText;
    private String returnText;

    // ! The dictionary for the program
    private ArrayList<ArrayList<Character>> probabilityTable = new ArrayList<ArrayList<Character>>();
    private ArrayList<String> lookupTable = new ArrayList<String>();

    // ! Performance analyzer
    private long fileReader_start, fileReader_end, fileReader_time;
    private long dictionaryGenerator_start, dictionGenerator_end, dictionGenerator_time;
    private long sentenceGenerator_start, sentenceGenerator_end, sentenceGenerator_time;
    private long fileWrite_start, fileWrite_end, fileWrite_time;

    /**
     * 
     * @param IO_k          User defined sample size
     * @param IO_length     user defined ouput length
     * @param IO_fileSource user defined source file path
     * @param IO_outPut     used defined output file path
     */
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
        System.out.println();
        generateSentence();
        System.out.println();
        writeToSource();
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
        fileReader_start = System.nanoTime();
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
            fileReader_end = System.nanoTime();
            fileReader_time = fileReader_end - fileReader_start;

            if (DEBUG) {
                System.out.println("==========readFromSource Report==========");
                System.out.println("Observed input from file: ");
                System.out.println(this.sourceText);
                System.out.println("Total read characters: " + totalCharCount);
                System.out.println("Time Consumed: " + fileReader_time + " Milliseconds");
                System.out.println("==========readFromSource End==========");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[FATAL]Failure observed at file reader stage!");
        }
    }

    /**
     * generate Probablistics method
     * <p>
     * This method is responsbile for generating the dictionary that will be used
     * for the later program. The method utilizes a 1D dynamic array as the "lookup
     * table" and a 2D dynamic array to store the potential characters after
     * </p>
     * 
     * @author Chude Qian CXQ41
     * @return GLOBAL: Dictionary, Lookup Table
     * 
     */
    private void generateProbablisitics() {
        dictionaryGenerator_start = System.nanoTime();
        int sourceTextIndexer = 0; // Responsible for indexing across the imported string
        int lookupTableIndexer = 0; // Responsible for telling the system a specific combo's adress in lookup table
        int probabilityTablePointer = 0; // Respobsible for telling the probability table pointer where to look at
        while ((sourceTextIndexer < (this.sourceText.length() - this.k))) {

            // ! Local Burnable variables
            StringBuilder segmetation = new StringBuilder(this.k);
            ArrayList<Character> temperary = new ArrayList<Character>();

            // ! Sgement the characters by index
            boolean successSegment = true;
            for (int i = 0; i < this.k; i++) {
                char tempChar = this.sourceText.charAt(sourceTextIndexer + i);
                // * Avoid any potential issue with special characters in the text.
                if (Character.isLetter(tempChar)) {
                    segmetation.append(tempChar);
                } else {
                    // * Space handling
                    if ((tempChar == '-') && (tempChar == ' ')
                            && (Character.isLetter(this.sourceText.charAt(sourceTextIndexer + i + 1)))) {
                        segmetation.append(tempChar);
                    } else if ((tempChar == ' ') && ((this.sourceText.charAt(sourceTextIndexer + i + 1) == ' '))) {
                        // * Treat multiple space as a single space
                        while (this.sourceText.charAt(sourceTextIndexer + i) == ' ') {
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
                    // if((sourceText.charAt(sourceTextIndexer + this.k - 1) == '
                    // ')||(Character.isLetter(sourceText.charAt(sourceTextIndexer + this.k - 1)))){
                    temperary.add(sourceText.charAt(sourceTextIndexer + this.k - 1));
                    probabilityTable.add(temperary);
                    // }
                } else {
                    temperary = probabilityTable.get(probabilityTablePointer);
                    // if((sourceText.charAt(sourceTextIndexer + this.k - 1) == '
                    // ')||(Character.isLetter(sourceText.charAt(sourceTextIndexer + this.k - 1)))){
                    temperary.add(sourceText.charAt(sourceTextIndexer + this.k - 1));
                    // }
                }

            }
        }
        dictionGenerator_end = System.nanoTime();
        dictionGenerator_time = dictionGenerator_end - dictionaryGenerator_start;
        if (DEBUG) {
            System.out.println("==========GenerateProbablistics Debug 1==========");
            System.out.println("Current Index table looks like: ");
            System.out.println(lookupTable.toString());
            System.out.println("Current Probability Matrix Looks like: ");
            for (int i = 0; i < probabilityTable.size(); i++) {
                System.out.println(
                        "Combinataion: |" + lookupTable.get(i) + "| has: " + probabilityTable.get(i).toString());
            }
            System.out.println("==========GenerateProbablistics Debug 1 END==========");
        }
    }

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

    /**
     * Initial Seed generator
     * <p>
     * This method is responsible for generating a seed from the sentence and return
     * it as a string. This method is also respobsible for regenerating the seed
     * when the seed hit an impass.
     * </p>
     * 
     * @author Chude Qian CXQ41
     * @return String a seed
     */
    private String initialSeedGenerator() {
        String initialSeed;
        do {
            int seedSelector = randomGenerator(this.sourceText.length() - this.k - 1);
            initialSeed = this.sourceText.substring(seedSelector, seedSelector + this.k);
        } while (!initialSeed.matches("^[A-Za-z]+$")); // Regex expression: ^[ A-Za-z]+$ means letters and spaces only
        return initialSeed;
    }

    /**
     * generateSentence Method
     * <p>
     * This method is respobsible for generating the sentence and return as a String
     * to who ever needs this.
     * </p>
     * 
     * @author Chude Qian CXQ41
     * @return Generated Sentence
     */
    private String generateSentence() {
        sentenceGenerator_start = System.nanoTime();
        String currentSeed;
        int lookupIndex = 0;
        StringBuilder retTextBuilder = new StringBuilder();

        // * Generate Initial Seed
        currentSeed = initialSeedGenerator();
        // * append the seed
        retTextBuilder.append(currentSeed);
        if (DEBUG) {
            System.out.println("==========string Generation Report==========");
        }

        while (retTextBuilder.length() < this.length) {
            if (DEBUG) {
                System.out.println("-----");
                System.out.println("Current Seed is: |" + currentSeed + "|");
            }

            // * Look for next possibility
            lookupIndex = lookupTable.indexOf(currentSeed);
            if (DEBUG) {
                System.out.println("Seed Index: " + lookupIndex);
            }
            // * handles pair not found issue
            if (((lookupIndex) == -1) || (probabilityTable.get(lookupIndex).size() == 0)) {
                if (DEBUG) {
                    System.out.println("Seed is not a valid seed, retry...");
                }
                do {
                    currentSeed = initialSeedGenerator();
                    if (DEBUG) {
                        System.out.println("New Generated Seed: |" + currentSeed + "|");
                    }
                    retTextBuilder.append(currentSeed);
                    lookupIndex = lookupTable.indexOf(currentSeed);
                } while ((!((lookupIndex) >= 0)) || (probabilityTable.get(lookupIndex).size() == 0));
            }

            // * looks for the possible word choice and add
            int randomSelector = randomGenerator(probabilityTable.get(lookupIndex).size());
            Character nextPossible = probabilityTable.get(lookupIndex).get(randomSelector);
            if (DEBUG) {
                System.out.println("Picked next possible characer: |" + nextPossible + "|");
            }
            retTextBuilder.append(nextPossible);

            // * Handle convention with comma and periods.
            if ((!(Character.isLetter(nextPossible))) && nextPossible != ' ') {
                retTextBuilder.append(' ');
            }

            // * Update the seed
            currentSeed = retTextBuilder.substring(retTextBuilder.length() - this.k, retTextBuilder.length());
            if (DEBUG) {
                System.out.println("New Seed is: |" + currentSeed + "|");
                System.out.println("Current Sentence is: |" + retTextBuilder.toString() + "|");
                System.out.println("-----");
            }
        }
        System.out.println("Current Sentence is: |" + retTextBuilder.toString() + "|");
        sentenceGenerator_end = System.nanoTime();
        sentenceGenerator_time = sentenceGenerator_end - sentenceGenerator_start;
        this.returnText = retTextBuilder.toString();
        return this.returnText;
    }
    /**
     * writeToSource Method
     * <p>
     * This method is in charge of writing the file to the result.txt document
     * </p>
     * 
     * @throws IOexception
     */
    private void writeToSource(){
        fileWrite_start = System.nanoTime();
        try {
            File writeFile = new File(this.outPut);
            FileWriter writer = new FileWriter(writeFile);
            writer.write(this.returnText);
            writer.close();
        } catch (Exception e) {
            System.out.println("[FATAL] Failed to write file!");
        }
        fileWrite_end = System.nanoTime();
        fileWrite_time = fileWrite_end - fileWrite_start;
    }

    public String getOPTime() {
        if (DEBUG) {
            System.out.println("==========Operation Report==========");
            System.out.println("File Read Time: " + fileReader_time + " Nano Seconds.");
            System.out.println("Dictionary generate Time: " + dictionGenerator_time + " Nano Seconds.");
            System.out.println("Sentencec Generate Time: " + sentenceGenerator_time + " Nano Seconds.");
            System.out.println("File Write Time: " + fileWrite_time);
            System.out.println("==========END Operation Report==========");
        }
        return "" + "==========Operation Report==========" + System.lineSeparator() + "File Read Time: "
                + fileReader_time + " Nano Seconds." + System.lineSeparator() + "Dictionary generate Time: "
                + dictionGenerator_time + " Nano Seconds." + System.lineSeparator() + "Sentencec Generate Time: "
                + sentenceGenerator_time + " Nano Seconds." + System.lineSeparator() + "File Write Time: "
                + fileWrite_time + " Nano Seconds." + System.lineSeparator()
                + "==========END Operation Report==========";
    }

    /**
     * Main Runner
     * <p>
     * This main is responsible for running the program. There are two operating
     * mode:Debug and regular. Debug uses pre-defined path, and args uses user
     * defined path. Once Parsed successful, the sequential reader takes in
     * responsibility to work.
     * </p>
     * 
     * @author Chude Qian CXQ41
     * @param args
     * @throws illegalInputException
     */
    public static void main(String args[]) {
        if (DEBUG) {
            RandomWriter wordGen = new RandomWriter(3, 100,
                    "/home/frank/Desktop/EECS233_WS/2-PS_WS/2019_summer_233_group1/src/week3/problem2/source.txt",
                    "/home/frank/Desktop/EECS233_WS/2-PS_WS/2019_summer_233_group1/src/week3/problem2/result.txt");
            wordGen.sequentialRunner();
            System.out.println(wordGen.getOPTime());
        } else {
            int userInput_k = 0;
            int userInput_length = 0;
            String userInput_SourcePath, userInput_ResultPath;
            if (args.length > 4) {
                throw new IllegalArgumentException("[FATAL]Too much input variable! Only 4 is allowed.");
            }
            try {
                for (int i = 0; i < 4; i++) {
                    userInput_k = Integer.parseInt(args[0]);
                    userInput_length = Integer.parseInt(args[1]);
                }
            } catch (Exception e) {
                System.out.println("[Fatal]Param 1, Param 2 need to be integer!");
            }

            userInput_SourcePath = args[2];
            userInput_ResultPath = args[3];
            try {
                File SourcePath = new File(userInput_SourcePath);
                File ResultPath = new File(userInput_ResultPath);

            } catch (Exception e) {
                System.out.println("[Fatal]File Path Incorrect!");
            }
            RandomWriter wordGen = new RandomWriter(userInput_k, userInput_length, userInput_SourcePath,
                    userInput_ResultPath);
            System.out.println("[MAIN]Parse Success! Working");
            wordGen.sequentialRunner();
            System.out.println(wordGen.getOPTime());
        }

    }
}