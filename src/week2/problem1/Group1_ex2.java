package week1;

import java.util.*;

public class Group1_ex2 {

  private static final boolean DEBUG = true;
  private static final int DICE_COUNT = 2;
  private static final int DICE_1 = 0;
  private static final int DICE_2 = 1;
  private int faces;
  private int[][] multiDiceResult;
  private int[] rollResult = new int[DICE_COUNT];

  // ! Constructor

  // * Default dice, provided how many sides it have, and auto populate
  public Group1_ex2(int userSideNumber) {
    this.faces = userSideNumber;
    this.multiDiceResult = new int[DICE_COUNT][this.faces];
    System.out.println("No provided face numbers... Defaulting...");
    for (int i = 0; i < this.faces; i++) {
      multiDiceResult[DICE_1][i] = i + 1;
      multiDiceResult[DICE_2][i] = i + 1;
    }
  }

  // * Assuming same dice with given faces, dupe checksum for two input
  public Group1_ex2(int userSideNumber, int[] userDefinedFaces) {
    this.faces = userSideNumber;
    if (userDefinedFaces.length != userSideNumber) {
      throw new IllegalArgumentException("Instructed dice side number and instructed face array mismatch...");
    }
    this.multiDiceResult = new int[DICE_COUNT][this.faces];
    for (int i = 0; i < this.faces; i++) {
      if (userDefinedFaces[i] < 1) {
        throw new IllegalArgumentException("Die face cannot be smaller than 0!");
      } else {
        this.multiDiceResult[DICE_1][i] = userDefinedFaces[i];
        this.multiDiceResult[DICE_2][i] = userDefinedFaces[i];
      }
    }
  }

  // * Default dice, provided the faces, and auto handles
  public Group1_ex2(int[] userDefinedFaces) {
    this.faces = userDefinedFaces.length;
    this.multiDiceResult = new int[DICE_COUNT][this.faces];
    for (int i = 0; i < this.faces; i++) {
      if (userDefinedFaces[i] < 1) {
        throw new IllegalArgumentException("Die face cannot be smaller than 0!");
      } else {
        this.multiDiceResult[DICE_1][i] = userDefinedFaces[i];
        this.multiDiceResult[DICE_2][i] = userDefinedFaces[i];
      }
    }
  }

  // * Not necessary, but done different dimension array
  public Group1_ex2(int userSideNumber1, int userSideNumber2, int[] userDefinedFacesDice1,
      int[] userDefinedFacesDice2) {
    if ((userDefinedFacesDice1.length != userSideNumber1) && (userDefinedFacesDice2.length != userSideNumber2)) {
      throw new IllegalArgumentException("Instructed dice side number and instructed face array mismatch...");
    }
    this.multiDiceResult = new int[2][];
    this.multiDiceResult[DICE_1] = new int[userDefinedFacesDice1.length];
    this.multiDiceResult[DICE_2] = new int[userDefinedFacesDice1.length];
    for (int i = 0; i < userDefinedFacesDice1.length; i++) {
      if (userDefinedFacesDice1[i] < 1) {
        throw new IllegalArgumentException("Die face cannot be smaller than 0!");
      } else {
        this.multiDiceResult[DICE_1][i] = userDefinedFacesDice1[i];
      }
    }
    for (int j = 0; j < userDefinedFacesDice1.length; j++) {
      if (userDefinedFacesDice1[j] < 1) {
        throw new IllegalArgumentException("Die face cannot be smaller than 0!");
      } else {
        this.multiDiceResult[DICE_2][j] = userDefinedFacesDice1[j];
      }
    }
  }

  // ! Populate the system with roll method
  public void roll() {
    int[] rollIndex = { randomGenerator(this.multiDiceResult[DICE_1].length),
        randomGenerator(this.multiDiceResult[DICE_2].length) };
    rollResult[DICE_1] = this.multiDiceResult[DICE_1][rollIndex[DICE_1]];
    rollResult[DICE_2] = this.multiDiceResult[DICE_2][rollIndex[DICE_2]];
    if (DEBUG) {
      System.out.println("============START roll debug=============");
      System.out.println("Report Roll Result 1: " + rollResult[DICE_1] + " Reference Index: " + rollIndex[DICE_1]);
      System.out.println("Report Roll Result 1: " + rollResult[DICE_2] + " Reference Index: " + rollIndex[DICE_2]);
      System.out.println("============END roll debug=============");
    }
  }

  // !Roll result getter
  public int[] getRollResult() {
    return rollResult;
  }

  // ! Total Value
  public int totalValue(int diceID) {
    int sum = 0;
    // Dice one:
    for (int i = 0; i < this.multiDiceResult[diceID].length; i++) {
      sum += this.multiDiceResult[diceID][i];
    }

    if (sum <= 0) {
      throw new ArithmeticException("Sum of the dice failed! Returning uninitialized value...");
    } else {
      return sum;
    }
  }

  // ! To String
  @Override
  public String toString() {
    String returnner = "";
    for (int i = 0; i < DICE_COUNT; i++) {
      returnner += "Dice " + i + " has " + this.multiDiceResult[i].length + " faces with values [";
      for (int j = 0; j < this.multiDiceResult[i].length; j++) {
        returnner += this.multiDiceResult[i][j];
        if (j + 1 != this.multiDiceResult[i].length) {
          returnner += ", ";
        }
      }
      returnner += "]. ";
    }
    if (DEBUG) {
      System.out.println("==========START toString Debug==========");
      System.out.println(returnner);
      System.out.println("==========END toSring Debug==========");
    }
    return returnner;
  }

  // ! Roll method simulation
  // * Extracted for future re-use.
  private int randomGenerator(int limitation) {
    Random randomGen = new Random();
    return randomGen.nextInt(limitation);
  }

  // ! LOCAL TESTER:
  public static void main(String[] args) {
    int diceSides = -1;
    int[] inputArray1;
    int[] inputArray2;
    int diceSidesT3 = -1;
    // ? Input Protection
    if (DEBUG) {
      diceSides = 6;
      inputArray1 = new int[] { 3, 5, 7, 9, 11, 15 };
      inputArray2 = new int[] { 1, 2, 3, 4 };
      diceSidesT3 = 4;
    } else {
      if (args.length > 0) {
        try {
          String userDiceFaces = args[0];
          diceSides = Integer.parseInt(userDiceFaces);
        } catch (Exception e) {
          System.out.println("Input not integer! Halt...");
        }
        if (diceSides < 0) {
          throw new IllegalArgumentException("Obtained an impossible dice face count!");
        }
      } else {
        throw new IllegalArgumentException("No input parameter given...");
      }
    }
    // ! Provide sides
    System.out.println("**********TEST1**********");
    Group1_ex2 diceMachine1 = new Group1_ex2(diceSides);
    // ? Dice Init check
    String diceDSP1 = diceMachine1.toString();
    System.out.println(diceDSP1);
    // ? Roll Check
    diceMachine1.roll();
    int[] diceResult1 = diceMachine1.getRollResult();
    System.out.println("Rolled:");
    System.out.println("Dice 1: " + diceResult1[DICE_1] + " Dice 2: " + diceResult1[DICE_2]);
    // ?Sum Check
    System.out.println("Total Sum for Dice 1 is: " + diceMachine1.totalValue(DICE_1));
    System.out.println("Total Sum for Dice 2 is: " + diceMachine1.totalValue(DICE_2));
    System.out.println("**********END TEST1**********");
    System.out.println("");

    // ! Provide with array face
    System.out.println("**********TEST2**********");
    Group1_ex2 diceMachine2 = new Group1_ex2(inputArray1);
    // ? Dice Init check,
    String diceDSP2 = diceMachine2.toString();
    System.out.println(diceDSP2);
    // ? Roll Check
    diceMachine2.roll();
    int[] diceResult2 = diceMachine1.getRollResult();
    System.out.println("Rolled:");
    System.out.println("Dice 1: " + diceResult2[DICE_1] + " Dice 2: " + diceResult2[DICE_2]);
    // ?Sum Check
    System.out.println("Total Sum for Dice 1 is: " + diceMachine2.totalValue(DICE_1));
    System.out.println("Total Sum for Dice 2 is: " + diceMachine2.totalValue(DICE_2));
    System.out.println("**********END TEST2**********");
    System.out.println("");

    // ! Provide with array face
    System.out.println("**********TEST3**********");
    Group1_ex2 diceMachine3 = new Group1_ex2(diceSidesT3,inputArray2);
    // ? Dice Init check,
    String diceDSP3 = diceMachine3.toString();
    System.out.println(diceDSP3);
    // ? Roll Check
    diceMachine3.roll();
    int[] diceResult3 = diceMachine3.getRollResult();
    System.out.println("Rolled:");
    System.out.println("Dice 1: " + diceResult3[DICE_1] + " Dice 2: " + diceResult3[DICE_2]);
    // ?Sum Check
    System.out.println("Total Sum for Dice 1 is: " + diceMachine3.totalValue(DICE_1));
    System.out.println("Total Sum for Dice 2 is: " + diceMachine3.totalValue(DICE_2));
    System.out.println("**********END TEST3**********");

  }
}