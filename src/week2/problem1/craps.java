package week2.problem1;
import java.util.*;



//In a readme file included in your project, write about the result you observe. (6/14) [25 points]


//The  basic  bet  made  by  the  “shooter” in this game is the pass-line bet. 
public class craps{

    private int comeOutRoll_result=-1; //This is used for 3 status!
    private int comeOutRoll_value = -1; //This is used for storing the last value of dice


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


    //A  come-out  roll  of  2,  3,  or  12  is  called  “craps”  or  “crapping  out,”  and  the  shooter  loses.  
    //A  come-out  roll  of  7  or  11  is  a  “natural,”  and  the  shooter  wins.  
    //The  other possible numbers are the point numbers: 4, 5, 6, 8, 9, and 10. 
    //If the shooter rolls one of  these  numbers  on  the  come-out  roll,  this  establishes  the  “point”—to  win,  
    
    public int comeOutRoll(){
        this.comeOutRoll_value = rollDice();
        switch(this.comeOutRoll_value){
            case 7:
            case 11:
                System.out.println("[ComeOutRoll Result:] Value is " + comeOutRoll_value + ". Natural win.");
                return WIN;
            case 2:
            case 3:
            case 12:
                System.out.println("[ComeOutRoll Result:] Value is " + comeOutRoll_value + ". Craps. Lose.");
                return LOSE;
            default:
                System.out.println("[ComeOutRoll Result:] Value is " + comeOutRoll_value + ". Roll again.");
                return NEUTRAL;
        }
    }

    //the  point  number must be rolled again before a seven. 
    //So in the case where a “point” is established 
    //the shooter rolls over and over until either the point is rolled (a win) or a seven is rolled (a loss). 
    private int secondRound(){

    }

    //! Internal mechanisms:

    private int rollDice(){
        int[] dice_2_6_faces = new int[] {1,2,3,4,5,6};
        Group1_ex2 dice_2_6 = new Group1_ex2(6,dice_2_6_faces);
        int[] roll_result = new int[2];
        int roll_value = -1;
        try{
            roll_result = dice_2_6.getRollResult();
            roll_value = roll_result[0]+roll_result[1];
        }catch(ArrayIndexOutOfBoundsException ex){
            System.out.println("[FATAL ERROR!] Roll result array out of bound error!");
        }

        if (DEBUG){
            System.out.println("[Roll Dice Debug] Rolled result is: "+ roll_result.toString());
            System.out.println("[Roll Dice Debug] Rolled sum value is: "+roll_result);
        }
        return roll_value;
    }

}