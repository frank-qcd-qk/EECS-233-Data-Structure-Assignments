package week3.problem1;

public class MultiUnboundedArray {
    /**
     * @deprecated This method is replaced by the in text version. Refer to random writer class
     */
    @Deprecated
    public MultiUnboundedArray(){
        MUnboundedArray mother = new MUnboundedArray<>(1);
        MUnboundedArray temp = new MUnboundedArray<>(1);
        mother.add(temp);
    }

}