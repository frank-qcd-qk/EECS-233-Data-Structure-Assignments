package week2.problem2;
// Create a Tuple class that holds a pair of real values, u and v. Provide
// getter and setter methods and a toString method to prdouble a Tuple.

public class tuple{
    
    private double u;
    private double v;

    public double get_u(){
        return this.u;
    }

    public double get_v(){
        return this.v;
    }

    public void set_u(double newVal){
        this.u = newVal;
    }

    public void set_v(double newVal){
        this.v = newVal;
    }


    public tuple(){

    }
}