package week2.problem2;

import java.util.*;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import week2.problem2.Tuple;

//mod
public class Polynomial {

    private static final Boolean DEBUG = false;
    private static final double BINSIZE = 4;
    private static double[] coefficient;
    private static double[] degree;
    private static long execution_time;

    public Polynomial(Tuple[] userTuple) {

        coefficient = new double[userTuple.length];
        degree = new double[userTuple.length];

        if (coefficient.length != degree.length) {
            throw new ArrayIndexOutOfBoundsException();
        }

        for (int i = 0; i < userTuple.length; i++) {

            double currentCoefficient = userTuple[i].get_u();
            double currentDegree = userTuple[i].get_v();
            coefficient[i] = currentCoefficient;
            degree[i] = currentDegree;
            if (DEBUG) {
                System.out.println("[Constructor DEBUG]Tuple pair @ " + i + "Coefficient: " + currentCoefficient
                        + " Degree: " + currentDegree);
            }
        }

        if (DEBUG) {
            System.out.println("[Constructor DEBUG]Coefficient is: " + Arrays.toString(coefficient));
            System.out.println("[Constructor DEBUG]Coefficient is: " + Arrays.toString(degree));
        }

    }

    @Override
    public String toString() {
        // TODO: Provide methods to evaluatea given polynomial at some x and to print it
        // TODO: as a string in a nice way.
        String returnner = "Input Polynomial is: ";
        for (int i = 0; i < degree.length; i++) {
            if (DEBUG) {
                System.out.println("[toString Debug]Current retunner @ tuple pair " + i + " is: |||" + returnner);
            }
            // ! Process sign first
            if (coefficient[i] > 0 && i != 0) {
                returnner += "+";
            }
            // ! Add coefficient
            returnner += coefficient[i];
            // ! Process degree
            if (degree[i] > 0) {
                /// ? Degree >0, we include x
                returnner += "*(x";
                if (degree[i] > 1) {
                    returnner += "^" + degree[i];
                }
                /*
                 * String tempHold = Double.toString(degree[i]); returnner +=
                 * superscript(tempHold);
                 */
                returnner += ")";
            } else if (degree[i] == 0) {
                // ? Degree = 0, there is no need to display x
                returnner += " ";
            } else if (degree[i] < 0) {
                // ? Degree <0, we display as 1/x method, but added paranthesis
                returnner += "/(x";
                if (degree[i] < -1) {
                    returnner += "^" + degree[i];
                }
                /*
                 * String tempHold = Double.toString(-degree[i]); returnner +=
                 * superscript(tempHold);
                 */
                returnner += ")";
            }
        }
        if (DEBUG) {
            System.out.println("[toString Debug] Output will be:" + returnner);
        }
        return returnner;
    }

    public double definiteIntegral(double[] bounds) {
        long startTime = System.nanoTime(); 
        double step_size = (bounds[1]-bounds[0])/BINSIZE;
        if(DEBUG){
            System.out.println("[Integral Debug] Current step Size:"+step_size);
        }
        double result = 0;
        double poi = bounds[0];
        if (step_size<0){
            throw new InputMismatchException();
        }
        for (int i = 0;i<BINSIZE;i++){
            double local1 = getVal(poi);
            double local2 = getVal(poi+step_size);
            //! Trapozoidal calculation
            double localSum = (local1+local2)/2.0*step_size;
            result += localSum;
            if (DEBUG){
                System.out.println("[Integral Debug ]POI1: "+local1+" POI2: "+local2+" localSum: "+localSum+" Cumulative: "+result);
            }            
            poi += step_size;
        }
        long endTime = System.nanoTime();
        execution_time = endTime - startTime;
        return result;
    }

    public long getExecutionTime(){
        return execution_time;
    }

    private double getVal(double poi) {
        double sum=0;
        for (int i = 0; i< coefficient.length; i++){
            //* Derp proof: Math.pow(base,exponent)
            sum+= coefficient[i]*Math.pow(poi, degree[i]);
            if(DEBUG){
                System.out.println("[Calculator Debug]Current calculation: POI: "+poi+" Tuple pair @"+i+" coefficient: "+coefficient[i]+" Degree: "+degree[i]);
                System.out.println("[Calculator Debug]Current Sum is: "+sum);
            }
        }
        return sum;
    }

}