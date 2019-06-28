package week3.problem1;

public class MUnboundedArray <T extends Object>{
    
    private final static boolean DEBUG = true;
    private int size;
    private int subsize;
    private T unArray[][];

    public MUnboundedArray(int size, int subsize){
        this.size = size;
        this.subsize = subsize;
        this.unArray[size][subsize];
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
        for(int i = 0; i < unArray.length; i++){
            for(int j = 0; j < unArray[i].length; j++){
                if (this.unArray[i][j] != null){
                    fullSubInd++;
                }
            }
            if (this.unArray[i] != null){
                fullInd++;
            }
        }
        return (fullInd + fullSubInd);
    }
}

