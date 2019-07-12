package week4.linkedList;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

public class fakeProcess implements Runnable {
    private int PID, RID, P_level, time;
    private LocalDateTime sysrequesttime;
    private String logger;

    public fakeProcess(int PID, int RID, int P_level, int time, LocalDateTime requestTime, String logger) {
        this.PID = PID;
        this.RID = RID;
        this.P_level = P_level;
        this.time = time;
        this.sysrequesttime = requestTime;
        this.logger = logger;
    }

    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(this.time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}