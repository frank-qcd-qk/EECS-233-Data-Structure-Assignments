package week2.problem2;

public class Polynomial{

    private double coefficient;
    private double degree; 
    private double variable;

    public Polynomial(tuple[] input){
        for (i=0; i < tuple.length(); i++){
            this.coefficient = input[i].get_u();
            this.degree = input[i].get_v();
        }
    }

    @Override
    public String toString() {
        // TODO: Provide methods to evaluatea given polynomial at some x and to print it
        // TODO: as a string in a nice way.
    }

    public double definiteIntegral(int[] bounds) {
        // TODO: You should do this by breaking up the [a,b] range into 1000 bins and
        // approximating the polynomial by a line in each bin
    }
}