package week2.problem2;

public class Polynomial{

    private double coefficient;
    private double degree; 

    public Polynomial(tuple[] input){
        for (i=0; i < tuple.length(); i++){
            this.coefficient = input[i].get_u();
            this.degree = input[i].get_v();
        }
    }
}