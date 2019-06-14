package week2.problem1;

import week2.problem1.Group1_ex2;

//In a readme file included in your project, write about the result you observe. (6/14) [25 points]

//The  basic  bet  made  by  the   in this game is the pass-line bet. 
public class craps {

    private int comeOutRoll_result = -1; // This is used for 3 status!
    private int comeOutRoll_value = -1; // This is used for storing the last value of dice

    private int secondRound_value = -1;
    private int secondround_result = -1;

    private final static int WIN = 2;
    private final static int LOSE = 1;
    private final static int NEUTRAL = 0;

    private final static Boolean DEBUG = true;

    // ! Construcor...
    public craps() {
        if (DEBUG) {
            System.out.println("[Constructor Debug:]We are in the constructor and nothing should happen!");
        }
    }

    // ! Game runner:
    public int run() {
        comeOutRoll_result = comeOutRoll();
        System.out.println("[Runner Debug:] retunned comeOutRoll Result is: "+this.comeOutRoll_result);
        if (comeOutRoll_result == NEUTRAL) {
            int secondround_result = secondRound();
            System.out.println("[Runner Debug:] secondround result is: " + secondround_result);
            return secondround_result;
        } else {
            System.out.println("[Runner Debug:] comeout result is: " + comeOutRoll_result);
            return comeOutRoll_result;
        }
    }

    // A come-out roll of 2, 3, or 12 is called or out, and the shooter loses.
    // A come-out roll of 7 or 11 is a and the shooter wins.
    // The other possible numbers are the point numbers: 4, 5, 6, 8, 9, and 10.
    // If the shooter rolls one of these numbers on the come-out roll, this
    // establishes the win,

    public int comeOutRoll() {
        this.comeOutRoll_value = rollDice();
        System.out.println(comeOutRoll_value);
        switch (this.comeOutRoll_value) {
        case 7:
        case 11:
            if (DEBUG) {
                System.out.println("[ComeOutRoll Result:] Value is " + this.comeOutRoll_value + ". Natural win.");
            }
            return WIN;
        case 2:
        case 3:
        case 12:
            if (DEBUG) {
                System.out.println("[ComeOutRoll Result:] Value is " + this.comeOutRoll_value + ". Craps. Lose.");
            }
            return LOSE;
        case 4:
        case 5:
        case 6:
        case 8:
        case 9:
        case 10:
            if (DEBUG) {
                System.out.println("[ComeOutRoll Result:] Value is " + this.comeOutRoll_value + ". Roll again.");
            }
            return NEUTRAL;
        default:
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    // the point number must be rolled again before a seven.
    // So in the case where a is established
    // the shooter rolls over and over until either the point is rolled (a win) or a
    // seven is rolled (a loss).
    private int secondRound() {
        this.secondRound_value = rollDice();
        if (DEBUG) {
            System.out.println("[SecondRoundRoll Debug:] Initial Roll Value: " + this.secondRound_value
                    + " Needed value: " + this.comeOutRoll_value);
        }
        int secondRoundRoll_count = 1;
        while (secondRound_value != comeOutRoll_value && secondRound_value != 7) {
            secondRound_value = rollDice();
            if (DEBUG) {
                System.out.println("[SecondRoundRoll debug:] Rerolled second round for the " + secondRoundRoll_count
                        + " time. Current result: " + this.secondRound_value + " Needed: " + this.comeOutRoll_value);
            }
            secondRoundRoll_count++;
        }

        if (secondRound_value == comeOutRoll_value) {
            secondround_result = WIN;
            System.out.println("[SecondRoundRoll Debug:] Roll = " + this.secondRound_value + ". Win.");
            return secondround_result;
        } else if (secondRound_value == 7) {
            secondround_result = LOSE;
            System.out.println("[SecondRoundRoll Debug:] Roll = " + this.secondRound_value + ". Lose.");
            return secondround_result;
        } else {
            System.out.println("[SecondRoundRoll Debug:] Error, illegal return value");
            return secondround_result;
        }
    }

    // ! Internal mechanisms:

    private int rollDice() {
        int[] dice_2_6_faces = new int[] { 1, 2, 3, 4, 5, 6 };
        Group1_ex2 dice_2_6 = new Group1_ex2(6, dice_2_6_faces);
        dice_2_6.roll();
        int[] roll_result = dice_2_6.getRollResult();
        int roll_value = roll_result[0] + roll_result[1];

        if (DEBUG) {
            System.out.println("[Roll Dice Debug] Rolled result is: " + roll_result.toString());
            System.out.println("[Roll Dice Debug] Rolled sum value is: " + roll_value);
        }
        return roll_value;
    }

}