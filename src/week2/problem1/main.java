package week2.problem1;

public class main {

    private final static int WIN = 2;
    private final static int LOSE = 1;
    private final static int NEUTRAL = 0;

    public static void run() {
        craps game_single = new craps();
        int result = game_single.run();
        if (game_single.run() == WIN) {
            System.out.println("Win!");
        } else if (game_single.run() == LOSE) {
            System.out.println("Lose!");
        } else {
            System.out.println("[FATAL ERROR!:] Win or Lose status not defined!");
            while (1 > 0)
                ;
        }
    }

    // Using your week1.Groupn_Ex2 class for a pair of dice, simulate 100,000
    // pass-line bets and output how many result in a win,
    // and how many result in a loss.
    public static void run_100k() {
        craps game_single = new craps();
        int win_counter = 0;
        int lose_counter = 0;
        for (int i = 0; i < 100000; i++) {
            if (game_single.run() == WIN) {
                win_counter++;
            } else if (game_single.run() == LOSE) {
                lose_counter++;
            } else {
                System.out.println("[FATAL ERROR!:] Win or Lose status not defined!");
                while (1)
                    ;
            }
        }
        System.out.println("===============RESULT===============");
        System.err.println("Total win: " + win_counter);
        System.out.println("Total lose: " + lose_counter);
        System.out.println("===============END===============");
    }
}
