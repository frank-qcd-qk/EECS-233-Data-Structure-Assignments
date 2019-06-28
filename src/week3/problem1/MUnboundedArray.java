package week3.problem1;

public class MUnboundedArray <T extends Object>{
    
    private final static boolean DEBUG = true;
    private T unArray[][];

    public MUnboundedArray(int size, int subsize){
        this.unArray[size][subsize];
    }

    public T get(int index, int subIndex){
        T output = this.unArray[index][subIndex];
        return output;   
    }

    public void set(int index, int subIndex, T thing){
        this.unArray[index][subIndex] = thing;
    }


}

