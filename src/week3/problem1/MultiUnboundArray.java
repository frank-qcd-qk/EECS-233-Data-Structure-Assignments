package week3.problem1;

public class MultiUnboundedArray {

    private static final boolean DEBUG = true;
    private int dimension = 1;
    private MUnboundedArray multiDimensionArray = new MUnboundedArray(1);

    public MultiUnboundedArray(int dims){
        this.dimension = dims;
    }

    public MUnboundedArray multiDimensionArray() {
        Object pointer;
        pointer = multiDimensionArray.get(0);
        for (int i = 0; i < this.dimension; i++) {
            MUnboundedArray local = new MUnboundedArray(1);
            pointer.set(0, local);
            pointer = pointer.get(0).get(0);
        }
    }
}