package week2.problem2;

import java.util.Scanner;

import week2.problem2.Polynomial;
import week2.problem2.Tuple;

public class Main {
    private static final Boolean DEBUG = true;
    private static final Boolean OVERIDEIO = true;

    public Main() {
    }

    public static void main(String[] args) {
        Tuple[] args2tuple;
        if(OVERIDEIO){
            args = new String[]{"5.2","3","-2","1","7.5","-1","3","0"};
        }
        int count = args.length;
        Scanner userInput = new Scanner(System.in);
        if ((count % 2 != 0) || count == 0) {
            throw new IllegalArgumentException("[Fatal Error!] All tuples need a coefficient argument and a degree argument.");
        } else {
            args2tuple = new Tuple[count / 2];
        }
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
            System.out.println("[PSVM Debug]args2tuple check: 0: " + args2tuple[0].toString() + " 1: " + args2tuple[1].toString());
        }
        Polynomial worker = new Polynomial(args2tuple);
        if(OVERIDEIO){
            double lowerBound = 0;
            double upperBound = 100;
        }else{
            System.out.print("Enter the lower bound: ");
            double lowerBound = userInput.nextDouble();
            System.out.println();
            System.out.print("Enter the upper bound: ");
            double upperBound = userInput.nextDouble();
            System.out.println();
        }
        if (DEBUG) {
            System.out.println("Obtianed user inputs are: lower bound: " + lowerBound + " upper bound: " + upperBound);
        }
        String polynomialExpression = worker.toString();

        System.out.println("The definite integral of polynomial: " + polynomialExpression +"between ["+lowerBound+","+upperBound+"] is ");
    }
}
