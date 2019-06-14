package week2.problem1;
import week2.problem1.craps;
public class main {
    private final static Boolean DEBUG = false;
    private final static int WIN = 2;
    private final static int LOSE = 1;

    public static void main(String[] args){
        test_100k();
    }


    public static void test() {
        craps game_single = new craps();
        if (game_single.run() == WIN) {
            System.out.println("Win!");
        } else if (game_single.run() == LOSE) {
            System.out.println("Lose!");
        } else {
            System.out.println("[FATAL ERROR!:] Win or Lose status not defined!");
        }
    }

    // Using your week1.Groupn_Ex2 class for a pair of dice, simulate 100,000
    // pass-line bets and output how many result in a win,
    // and how many result in a loss.
    public static void test_100k() {
        craps game_single = new craps();
        int win_counter = 0;
        int lose_counter = 0;
        for (int i = 0; i < 100000; i++) {
            System.out.println("===================Round "+i+"===================");
            int getResult =game_single.run();
            if(DEBUG){
                System.out.println("[Main Runner Debug] Obatianed result from crap is: "+getResult);
            }
            if ( getResult== WIN) {
                System.out.println("Win!");
                win_counter++;
            } else if (getResult == LOSE) {
                System.out.println("Lose!");
                lose_counter++;
            } else {
                System.out.println("[FATAL ERROR!:] Win or Lose status not defined!");
                while (1>0)
                    ;
            }
            System.out.println("===================End===================");
            System.out.println();
        }
        System.out.println("===============RESULT===============");
        System.err.println("Total win: " + win_counter);
        System.out.println("Total lose: " + lose_counter);
        System.out.println("===============END===============");
    }
}
