package week3.problem1;

import java.lang.reflect.Array;

public class MUnboundedArray <T extends Object>{
    
    private final static boolean DEBUG = true;
    private int size;
    private int arrayPlace = 0;
    private int subArrayPlace = 0;
    private int subsize;
    private Object unArray;

    public MUnboundedArray(int dimensions, int size, int subsize){
        this.size = size;
        this.subsize = subsize;
        int[] sizes = new int[dimensions];
        unArray = Array.newInstance(String.class, sizes);
    }

    public Object get(int index, int subIndex){
        Object output = this.unArray[index][subIndex];
        return output;   
    }

    public void set(int index, int subIndex, T thing){
        this.unArray[index][subIndex] = thing;
    }

    public int size(){
        int fullInd = 0;
        int fullSubInd = 0;
        for(int i = 0; i < size; i++){
            for(int j = 0; j < subsize; j++){
                if (this.unArray[i][j] != null){
                    fullSubInd++;
                }
            }
            if (this.unArray[i] != null){
                fullInd++;
            }
        }
        if (DEBUG){
            System.out.println("Method Size: Index value is " + fullInd + " and subindex value is " + fullSubInd);
        }
        return (fullInd + fullSubInd);
    }

    public void reallocate(int newSize, int newSubSize){
        int[] intArray = new int[newSize];
        Object tempArray = Array.newInstance(String.class, intArray);
        for (int i = 0; i > size; i++){
            for (int j = 0; j > subsize; j++){
                tempArray = unArray;
            }
        }
        unArray = tempArray;
    }

    public void grow(){
        boolean arrayCheck = true;
        for (int i = 0; i < size; i++){
            for (int j = 0; j < 1; j++){
                if (this.unArray[i][j] == null){
                    arrayCheck = false;
                }
            }
        }

    }

}

