package week2.problem2;
import week2.problem2.polynomial;
import week2.problem2.tuple;


public class main{
    private static final Boolean DEBUG = true;
    public main(){
    }

    public static void main(String[] args){
        tuple[] args2tuple;
        int count = args.length;
        if ((count%2 != 0)|| count == 0){
            throw new IllegalArgumentException("All tuples need a coefficient argument and a degree argument.");
        } else{
            args2tuple = new tuple[count];
        }
        try {
            int tupleIndexer = 0;
            for (int i = 0; i <args.length; i++){
                double coefficientHold = Double.parseDouble(args[i]);
                i++;
                double degreeHold = Double.parseDouble(args[i]);
                args2tuple[tupleIndexer] = new tuple();
                if (DEBUG){
                    System.out.println("Tuple pair "+tupleIndexer+" is: Coefficient: "+coefficientHold+" Degree: "+degreeHold);
                }
                args2tuple[tupleIndexer].set_u(coefficientHold);
                args2tuple[tupleIndexer].set_v(degreeHold);
                if (DEBUG){
                    System.out.println("args2tuple report: "+args2tuple.toString());
                }
                tupleIndexer++;
            }
        } 
        catch (IllegalArgumentException e){
            System.out.println("All arguments must be doubles.");
        }
        polynomial worker = new polynomial(args2tuple);
        worker.toString();
    }
}
