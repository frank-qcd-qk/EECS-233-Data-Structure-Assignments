package week3.problem1;

public class testRunner{
    public static void main(String args[]) {
        MUnboundedArray test = new MUnboundedArray(10);
        test.grow("1");
        test.grow("2");


        test.shrink();
        System.out.println(""+test.get(0));
    }
}