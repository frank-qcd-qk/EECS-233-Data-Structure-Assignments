package week2.problem2;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import week2.problem2.Polynomial;
import week2.problem2.Tuple;

/*
! The option DEBUG allows the full screen outputs
! The option OVERIDEIO allows no user interaction debug
*/

public class Main {
    private static final Boolean DEBUG = false;
    private static final Boolean OVERIDEIO = false;

    public Main() {
    }

    public static void main(String[] args) {
        Tuple[] args2tuple;
        if (OVERIDEIO) {
            args = new String[] { "5.2", "3", "-2", "1", "7.5", "-1", "3", "0" };
            //args = new String[] {"1","2","1","0"};
        }
        int count = args.length;
        Scanner userInput = new Scanner(System.in);
        double lowerBound, upperBound;
        //! Input is not a pair guard:
        if ((count % 2 != 0) || count == 0) {
            throw new IllegalArgumentException(
                    "[Fatal Error!] All tuples need a coefficient argument and a degree argument.");
        } else {
            args2tuple = new Tuple[count / 2];
        }
        //! Input is pair but not double guard
        try {
            int tupleIndexer = 0;
            for (int i = 0; i < args.length; i++) {
                double coefficientHold = Double.parseDouble(args[i]);
                i++;
                double degreeHold = Double.parseDouble(args[i]);
                args2tuple[tupleIndexer] = new Tuple();
                if (DEBUG) {
                    System.out.println("[PSVM Debug]Tuple pair " + tupleIndexer + " is: Coefficient: " + coefficientHold
                            + " Degree: " + degreeHold);
                }
                args2tuple[tupleIndexer].set_u(coefficientHold);
                args2tuple[tupleIndexer].set_v(degreeHold);
                if (DEBUG) {
                    String hold = args2tuple[tupleIndexer].toString();
                    System.out.println("[PSVM Debug]args2tuple report: " + hold);
                }
                tupleIndexer++;
            }
        } catch (IllegalArgumentException e) {
            System.out.println("[Fatal Error!]All arguments must be doubles.");
        }
        if (DEBUG) {
            System.out.println(
                    "[PSVM Debug]args2tuple check: 0: " + args2tuple[0].toString() + " 1: " + args2tuple[1].toString());
        }
        Polynomial worker = new Polynomial(args2tuple);
        if (OVERIDEIO) {
            lowerBound = 0;
            upperBound = 4;
        } else {
            System.out.print("Enter the lower bound: ");
            lowerBound = userInput.nextDouble();
            System.out.println();
            System.out.print("Enter the upper bound: ");
            upperBound = userInput.nextDouble();
            System.out.println();
        }
        if (DEBUG) {
            System.out.println("Obtianed user inputs are: lower bound: " + lowerBound + " upper bound: " + upperBound);
        }
        double[] bounds = new double[]{lowerBound,upperBound};
        //! Temporary hold the expression for polynomial
        String polynomialExpression = worker.toString();
        //! Temporarily hold the integration result
        double integralResult = worker.definiteIntegral(bounds);
        //!UI Interaction
        long runTime = TimeUnit.MILLISECONDS.convert(worker.getExecutionTime(),TimeUnit.NANOSECONDS);
        System.out.println("The definite integral of polynomial: " + polynomialExpression + "between [" + lowerBound
                + "," + upperBound + "] is "+integralResult);
        System.out.println("Time taken to compute the integral: "+runTime+" Miliseconds");
        System.out.println("Raw execution nanoseconds: "+worker.getExecutionTime());
    }
}
