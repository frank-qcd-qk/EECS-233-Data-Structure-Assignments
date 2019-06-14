package week2.problem2;

public class polynomial{

    private double[] coefficient;
    private double[] degree; 

    public Polynomial(tuple[] userTuple){
        for (i=0; i < userTu/ple.length(); i++){
            this.coefficient = userTuple[i].get_u();
            this.degree = userTuple[i].get_v();
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