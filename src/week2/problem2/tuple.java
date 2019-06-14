package week2.problem2;
// Create a Tuple class that holds a pair of real values, u and v. Provide
// getter and setter methods and a toString method to prdouble a Tuple.

public class tuple{
    
    private static double u; //THIS IS THE COEFFICIENT
    private static double v; //THIS IS THE DEGREE

    public tuple(){

    }

    public tuple(double uin, double vin){
        u = uin;
        v = vin;
    }

    public static double get_u(){
        return u;
    }

    public static double get_v(){
        return v;
    }

    public static void set_u(double newVal){
        u = newVal;
    }

    public static void set_v(double newVal){
        v = newVal;
    }

    public String toString(double uval, double vval){
        return "Tuple (" + uval + ", " + vval + ")";
    }
}
