package week4.hashTable;

import java.util.Hashtable;

public class sharedHash{

    private int maximum;
    private int counter = 0;
    private int size = 10;
    Hashtable<Object, Integer> hash = new Hashtable<Object, Integer>(size);

    public sharedHash(int value){
        maximum = value;
    }

    public int getMax(){
        return maximum;
    }

    public int getSize(){
        return size;
    }

    public int getCurrentAvailable(){
        return maximum - counter;
    }

    public int getPriority(Integer key){
        Integer map = hash.get(key);
        int priority = map;
        return priority;
    }

    public void place(Object insert, Integer key){
      // if (counter < maximum){
         hash.put(insert, key);
         counter++;
      // }
    }

}