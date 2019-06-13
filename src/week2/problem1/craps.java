package week2.problem1;
import java.util.*;



//In a readme file included in your project, write about the result you observe. (6/14) [25 points]


//The  basic  bet  made  by  the   in this game is the pass-line bet. 
public class craps{

    private int comeOutRoll_result=-1; //This is used for 3 status!
    private int comeOutRoll_value = -1; //This is used for storing the last value of dice

    private int secondRound_value = -1;
    private int secondround_result = -1;

    private final static int WIN = 2;
    private final static int LOSE = 1;
    private final static int NEUTRAL = 0;

    private final static Boolean DEBUG = true;


    //! Construcor...
    public craps(){
        if (DEBUG){
            System.out.println("We are in the constructor and nothing should happen!");
        }
    }

    //! Game runner:
    public int run(){
        int comout_result = comeOutRoll();
        if (comeOutRoll_result == NEUTRAL){
            int secondround_result = secondRound();
            return secondround_result;
        }else{
            return comout_result;
        }
    }


    //A  come-out  roll  of  2,  3,  or  12  is  called    or    out,  and  the  shooter  loses.  
    //A  come-out  roll  of  7  or  11  is  a    and  the  shooter  wins.  
    //The  other possible numbers are the point numbers: 4, 5, 6, 8, 9, and 10. 
    //If the shooter rolls one of  these  numbers  on  the  come-out  roll,  this  establishes  the    win,  
    
    public int comeOutRoll(){
        this.comeOutRoll_value = rollDice();
        System.out.println(comeOutRoll_value);
        switch(this.comeOutRoll_value){
            case 7:
            case 11:
                System.out.println("[ComeOutRoll Result:] Value is " + this.comeOutRoll_value + ". Natural win.");
                return WIN;
            case 2:
            case 3:
            case 12:
                System.out.println("[ComeOutRoll Result:] Value is " + this.comeOutRoll_value + ". Craps. Lose.");
                return LOSE;  
            case 4:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
                System.out.println("[ComeOutRoll Result:] Value is " + this.comeOutRoll_value + ". Roll again.");
                return NEUTRAL;
            default:
                throw new ArrayIndexOutOfBoundsException();
        }
    }

    //the  point  number must be rolled again before a seven. 
    //So in the case where a  is established 
    //the shooter rolls over and over until either the point is rolled (a win) or a seven is rolled (a loss). 
    private int secondRound(){
        secondRound_value = rollDice();
        System.out.println(secondRound_value);
        while(secondRound_value != comeOutRoll_value && secondRound_value != 7){
            secondRound_value = rollDice();
            System.out.println(secondRound_value);
        }
        if(secondRound_value == comeOutRoll_value){
            secondround_result = WIN;
            System.out.println("[SecondRound:] Roll = " + this.secondRound_value + ". Win.");
            return secondround_result;
        }
        else if(secondRound_value == 7){
            secondround_result = LOSE;
            System.out.println("[SecondRound:] Roll = " + this.secondRound_value + ". Lose.");
            return secondround_result;
        }
        else {
            System.out.println("Error, illegal return value");
            return secondround_result;
        }
    }

    //! Internal mechanisms:

    private int rollDice(){
        int[] dice_2_6_faces = new int[] {1,2,3,4,5,6};
        Group1_ex2 dice_2_6 = new Group1_ex2(6,dice_2_6_faces);
        int[] roll_result = new int[2];
        int roll_value = -1;
        try{
            dice_2_6.roll();
            roll_result = dice_2_6.getRollResult();
            roll_value = roll_result[0]+roll_result[1];
        }catch(ArrayIndexOutOfBoundsException ex){
            System.out.println("[FATAL ERROR!] Roll result array out of bound error!");
        }

        if (DEBUG){
            System.out.println("[Roll Dice Debug] Rolled result is: "+ roll_result.toString());
            System.out.println("[Roll Dice Debug] Rolled sum value is: "+roll_value);
        }
        return roll_value;
    }






}