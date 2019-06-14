package week2.problem2;
import java.util.Arrays;
import week2.problem2.tuple;
//mod
public class polynomial{

    private static final Boolean DEBUG = true;

    private static double[] coefficient;
    private static double[] degree;

    public polynomial(tuple[] userTuple){
        
        coefficient = new double[userTuple.length];
        degree = new double[userTuple.length];

        if (coefficient.length != degree.length){
            throw new ArrayIndexOutOfBoundsException();
        }

        for (int i=0; i < userTuple.length; i++){
            coefficient[i] = userTuple[i].get_u();
            degree[i] = userTuple[i].get_v();
        }

        if (DEBUG){
            System.out.println("[Constructor DEBUG]Coefficient is: "+Arrays.toString(coefficient));
            System.out.println("[Constructor DEBUG]Coefficient is: "+Arrays.toString(degree));
        }

    }

    @Override
    public String toString() {
        // TODO: Provide methods to evaluatea given polynomial at some x and to print it
        // TODO: as a string in a nice way.
        String returnner = "Input Polynomial is: ";
        for (int i = 0; i<degree.length;i++){
            if (DEBUG){
                System.out.println("[toString Debug]Current retunner @ tuple pair "+i+" is: ");
                System.out.println(""+returnner);
            }
            //! Process sign first
            if (coefficient[i]>0){
                returnner += "+";
            }else if(coefficient[i]<0){
                returnner += "-";
            }
            //! Add coefficient
            returnner += coefficient[i];
            //! Process degree
            if (degree[i]>0){
                ///? Degree >0, we include x
                returnner += "*(x";
                returnner += "^"+degree[i];
                /*
                String tempHold = Double.toString(degree[i]);
                returnner += superscript(tempHold);
                */
                returnner +=" )";
            } else if(degree[i]==0){
                //? Degree = 0, there is no need to display x
                returnner += " ";
            } else if(degree[i]<0){
                //? Degree <0, we display as 1/x method, but added paranthesis
                returnner += "/(x";
                returnner += "^"+degree[i];
                /*
                String tempHold = Double.toString(-degree[i]);
                returnner += superscript(tempHold);
                */
                returnner +=") ";
            }
        }
        if (DEBUG){
            System.out.println("[toString Debug] Output will be:" +returnner);
        }
        return returnner;
    }

    public double definiteIntegral(int[] bounds) {
        // TODO: You should do this by breaking up the [a,b] range into 1000 bins and
        // approximating the polynomial by a line in each bin
        return 0;
    }


    // Code refered to stackoverflow: https://stackoverflow.com/questions/8058768/superscript-in-java-string/8058953
}