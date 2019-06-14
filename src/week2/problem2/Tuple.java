package week2.problem2;
// Create a Tuple class that holds a pair of real values, u and v. Provide

// getter and setter methods and a toString method to prdouble a Tuple.

public class Tuple {

    private double u; // THIS IS THE COEFFICIENT
    private double v; // THIS IS THE DEGREE

    public Tuple() {

    }

    public Tuple(double uin, double vin) {
        u = uin;
        v = vin;
    }

    public double get_u() {
        return u;
    }

    public double get_v() {
        return v;
    }

    public void set_u(double newVal) {
        u = newVal;
    }

    public void set_v(double newVal) {
        v = newVal;
    }

    @Override
    public String toString() {
        return "Tuple (" + get_u() + ", " + get_v() + ")";
    }
}
