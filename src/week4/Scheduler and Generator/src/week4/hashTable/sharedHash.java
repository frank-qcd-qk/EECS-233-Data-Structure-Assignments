package week4.hashTable;

import java.util.Hashtable;

public class sharedHash{

    public static void main(String[] args){
        int size = Integer.parseInt(args[0]);
        Hashtable<Integer, Integer> sharedHash = new Hashtable<Integer, Integer>(size);
    }
}