package week2.problem2;
import week2.problem2.polynomial;
import week2.problem2.tuple;


public class main{

    public main(){
    }

    public static void main(Strings[] args){
        tuple[] args2tuple;
        if (args%2 != 0){
            throw new IllegalArgumentException("All tuples need a coefficient argument and a degree argument.");
        }
        try {
            for (int i = 0; i <args.length; i++)
            args2tuple = Double.parseDouble(args[i]);
        } 
        catch (IllegalArgumentException e){
            System.out.println("All arguments must be doubles.");
        }
        polynomial worker = new polynomial();
    }
}