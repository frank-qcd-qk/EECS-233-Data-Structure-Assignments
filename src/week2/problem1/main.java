package week2.problem1;
public class main{

    private final static int WIN = 2;
    private final static int LOSE = 1;
    private final static int NEUTRAL = 0;

    public static void run(){
        craps game_single = new craps();
        game_single.run()
    }
    //Using your week1.Groupn_Ex2 class for a pair of dice, simulate 100,000 pass-line bets and output how many result in a win, 
    //and how many result in a loss. 
    public static void run_100k(){
        craps game_single = new craps();
        int win_counter = 0;
        int lose_counter = 0;
        for (int i = 0; i<100000;i++){
            if(game_single.run()==WIN){
                win_counter++;
            }else if (game_single.run()==LOSE){
                lose_counter++;
            }else{
                throw new ClassNotFoundException
            }
        }
    }
}
