package week3.problem1;

import java.lang.reflect.Array;

public class MUnboundedArray<T extends Object> {

    private final static boolean DEBUG = true;
    private Object[] unArray;
    private int counter = 0;
    private int placeholder = 0;

    public MUnboundedArray(int size) {
        Object[] copyArray = new Object[size];
        this.unArray = copyArray;
    }

    public Object get(int index) {
        return this.unArray[index];
    }

    public void set(int index, Object thing) {
        this.unArray[index] = thing;
    }

    public int size() {
        counter = 0;
        for (int i = 0; i < placeholder; i++) {
            if (this.unArray[i] != null) {
                counter++;
            }
        }
        if (DEBUG) {
            System.out.println("Method Size: Index value is " + (counter - 1));
        }
        return (counter);
    }

    public void reallocate(int newSize) {
        if (DEBUG){
            System.out.println("Reallocate: unArray at 0 before method is" + unArray[0]);
            System.out.println("Reallocate: " + size());
        }
        int resize = (placeholder);
        Object[] tempArray = new Object[newSize];
        for (int i = 0; i < resize; i++) {
            tempArray[i] = unArray[i];
            if(DEBUG){
                System.out.println("Reallocate Called, resize value is "+ resize);

                System.out.println("Reallocate Called, tempArray at " + i +", value is "+ tempArray[i]);
                System.out.println("Reallocate Called, unarray in method at " + i +", value is "+ unArray[i]);
            }
        }
        unArray = tempArray;
        if (DEBUG){
            System.out.println("Reallocate Called, newsize value is: " + newSize);
            System.out.println("Reallocate: unArray at 0 is" + unArray[0]);
            System.out.println("Reallocate: tempArray at 0 is" + tempArray[0]);
        }
    }

    public void grow(Object thing) {
        int growCompare = size();
        if (growCompare == unArray.length) {
            reallocate(growCompare * 2);
        }
        set(placeholder, thing);
        placeholder++;
    }

    public void shrink() {
        int shrinkCompare = size();
        if (placeholder > 0) {
            placeholder--;
            if (placeholder > 0 && unArray.length >= (shrinkCompare * 4)) {
                reallocate(shrinkCompare / 2);
            }
        }
    }
}