package week4.hashTable;

import java.util.Hashtable;

public class sharedHash{

    private int maxSize = 10;
    Hashtable<Object, Integer> hash = new Hashtable<Object, Integer>(maxSize);

    public int getSize(){
        return maxSize;
    }

    public int getPriority(Integer key){
        Integer map = hash.get(key);
        int priority = map;
        return priority;
    }

    public void place(Object insert, Integer key){
        hash.put(insert, key);
    }

}