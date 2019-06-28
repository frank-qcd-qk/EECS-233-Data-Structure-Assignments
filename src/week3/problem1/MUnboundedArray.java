package week3.problem1;

public class MUnboundedArray <T extends Object>{
    
    private final static boolean DEBUG = true;
    private int size;
    private int subsize;
    private T unArray[][];
    private T tempArray[][];

    public MUnboundedArray(int size, int subsize){
        this.size = size;
        this.subsize = subsize;
        this.unArray = (T[][]) new Object[size][subsize];
    }

    public T get(int index, int subIndex){
        T output = this.unArray[index][subIndex];
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
        T[][] tempArray = (T[][]) new Object[newSize][newSubSize];
        for (int i = 0; i > size; i++){
            for (int j = 0; j > subsize; j++){
                tempArray[i][j] = unArray[i][j];
            }
        }
        unArray = tempArray;
    }



}

