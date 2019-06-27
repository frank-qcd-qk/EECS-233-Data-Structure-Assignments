package week3.problem2;

import java.util.*;
import java.io.*;

public class RandomWriter {
    private final static boolean DEBUG = true;
    
    //! Initializaion vars
    private int k, length;
    private String fileSource, outPut;
    private static boolean initComplete = false;


    private String sourceText;



    //! Constructor
    public RandomWriter(int IO_k, int IO_length, String IO_fileSource, String IO_outPut) {
        this.k = IO_k;
        this.length = IO_length;
        this.fileSource = IO_fileSource;
        this.outPut = IO_outPut;
        initComplete = true;
    }

    public void readFromSource(){
        try {
            File sourceInput = new File(this.fileSource); //Create file object
            FileReader reader = new FileReader(sourceInput); //Start file object reader
            StringBuffer stringBuilder = new StringBuffer(); //Create string builder object

            //* Read file:
            int charCount;
            int totalCharCount = 0;
            char[] charBuffer = new char[1]; //Create a batch read buffer of 1024
            while((charCount = reader.read(charBuffer))>0){
                stringBuilder.append(charBuffer);
                totalCharCount += charCount;
            }

            reader.close();
            sourceText = stringBuilder.toString();

            if(DEBUG){
                System.out.println("==========readFromSource Report==========");
                System.out.println("Total read characters: "+totalCharCount);
                System.out.println("Observed input from file: ");
                System.out.println(sourceText);
                System.out.println("==========readFromSource End==========");
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("[FATAL]Failure observed at file reader stage!");
        }
    }



    // Otherwise, your program should analyze the source text to construct a
    // k-dimensional probability matrix.
    // Here, if k=2, P[‘a’][‘b’][‘c’] will contain the probability that the
    // character ‘c’ will follow the string “ab”. Similarly, if k =3,
    // P[‘a’][‘b’][‘c’][‘d’] will contain the probability that the character ‘d’
    // will follow the string “abc”, and so on.

    // To calculate these probabilities, find every occurrence of the string, say
    // “ab”, and find what characters follow it and their frequencies. Then set the
    // probabilities to be proportional to the frequencies. Use these probabilities
    // to repeatedly choose the next character of the text.

    private void calculateProbablistics(){

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
        RandomWriter wordGen = new RandomWriter(2,3,"/home/frank/Desktop/EECS233_WS/2-PS_WS/2019_summer_233_group1/src/week3/problem2/source.txt","result.txt");
        wordGen.readFromSource();
    }
}