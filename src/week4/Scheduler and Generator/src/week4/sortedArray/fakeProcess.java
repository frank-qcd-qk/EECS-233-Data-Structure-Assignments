package week4.sortedArray;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class fakeProcess implements Runnable {
    frankDS sharedDataStructure;
    private int PID, RID, P_level, time;
    private String logger;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public fakeProcess(frankDS sharedDataStructure,int PID, int RID, int P_level, int time, LocalDateTime requestTime, String logger) {
        this.PID = PID;
        this.RID = RID;
        this.P_level = P_level;
        this.time = time /10;
        this.logger = logger;
        this.sharedDataStructure = sharedDataStructure;
        System.out.println("[Fake Thread "+this.RID+"] for resource "+this.RID+"spawnned!");
    }

    public void run() {
        //! delay per request
        try {
            TimeUnit.MILLISECONDS.sleep(Long.valueOf(this.time));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //! Write log directly from here to simplify life
        String toWrite = "[Fake Process/Scheduler] Finished to process request: PID: " + this.PID + " RID: " + this.RID + " Priority: " + this.P_level
                + " OP time: " + this.time + " at " + dtf.format(getSystemTime());
        writeLog(toWrite);
        //! tell the ds, I am done, here is your resource!
        sharedDataStructure.completeOne();
        sharedDataStructure.returnResource(this.RID);
        return;
    }

    /**
     * File writer
     */
    private void writeLog(String tobeWritten) {
        try {
            File writeFile = new File(this.logger);
            FileWriter writer = new FileWriter(writeFile, true);
            writer.write(tobeWritten + System.lineSeparator());
            writer.close();
        } catch (Exception e) {
            System.out.println("[FATAL] Failed to write file!");
        }
    }

    /**
     * Get the system time
     * 
     * @return the system time
     */
    private LocalDateTime getSystemTime() {
        LocalDateTime report = LocalDateTime.now();
        return report;
    }
}