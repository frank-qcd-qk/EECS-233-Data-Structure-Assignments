package week2.problem1;
import java.util.*;

import week1.Group1_ex2;




//In a readme file included in your project, write about the result you observe. (6/14) [25 points]


//The  basic  bet  made  by  the  “shooter” in this game is the pass-line bet. 
public class craps{

    int comeOutRoll_result=-1;



    private final static int WIN = 2;
    private final static int LOSE = 1;
    private final static int NEUTRAL = 0;

    private final static Boolean DEBUG = true;


    //! Construcor...
    public craps(){

    }




    /*
    This method will help us start the come out roll.
    It should call the random number generator.
    */
    public int comeOutRoll(){
        int firstRoll = rollDice();
        switch(firstRoll){
            case 7:
            case 11:
                System.out.println("Value is " + firstRoll + ". Natural win.");
                return WIN;
                break;
            case 2:
            case 3:
            case 12:
                System.out.println("Value is " + firstRoll + ". Craps. Lose.");
                return LOSE;
                break;       
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
                System.out.println("Value is " + firstRoll + ". Roll again.");
                return NEUTRAL;
                comeOutRoll_result = firstRoll;
                break;
        }
    }





    //A  come-out  roll  of  2,  3,  or  12  is  called  “craps”  or  “crapping  out,”  and  the  shooter  loses.  
    //A  come-out  roll  of  7  or  11  is  a  “natural,”  and  the  shooter  wins.  
    //The  other possible numbers are the point numbers: 4, 5, 6, 8, 9, and 10. 
    //If the shooter rolls one of  these  numbers  on  the  come-out  roll,  this  establishes  the  “point”—to  win,  



    private int comeOutRoll_det(){
        int secondRoll = rollDice();
    }


    //the  point  number must be rolled again before a seven. 
    //So in the case where a “point” is established 
    //the shooter rolls over and over until either the point is rolled (a win) or a seven is rolled (a loss). 
    private int secondRound(int previousRoll){

    }




    //! Internal mechanisms:
    //Using your week1.Groupn_Ex2 class for a pair of dice, simulate 100,000 pass-line bets and output how many result in a win, 
    //and how many result in a loss. 
    private int rollDice(){
        int[] dice_2_6_faces = new int[] {1,2,3,4,5,6};
        Group1_ex2 dice_2_6 = new Group1_ex2(6,dice_2_6_faces);
        int[] roll_result = new int[2];
        
        try{
        roll_result = dice_2_6.getRollResult();
        int roll_value = roll_result[0]+roll_result[1];
        }catch(ArrayIndexOutOfBoundsException ex){
            System.out.println("[FATAL ERROR!] Roll result array out of bound error!");
        }

        if (DEBUG){
            System.out.println("[Roll Dice Debug] Rolled result is: "+ roll_result.toString());
            System.out.println("[Roll Dice Debug] Rolled sum value is: "+roll_result);
        }

        return roll_result;
    }

    private int randomGenerator(int limitation) {
        Random randomGen = new Random();
        return randomGen.nextInt(limitation);
      }
}