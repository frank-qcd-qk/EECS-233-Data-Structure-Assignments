package week4.priorityQueue;

import java.util.Comparator;

public class queueComparator implements Comparator<task> {
    public int compare( task t1, task t2){
        if (t1.P_level>t2.P_level){
            return 1;
        }else{
            return 0;
        }
    }
}