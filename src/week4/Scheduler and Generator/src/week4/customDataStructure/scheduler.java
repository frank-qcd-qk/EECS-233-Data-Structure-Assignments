package week4.customDataStructure;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class scheduler implements Runnable {
    private frankDS sharedDataStructure;
    private int totalRequests, availableReouces;
    private String logName;
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public scheduler(frankDS sharedDS, int resourceSize, int maxOperation, String logName) {
        this.sharedDataStructure = sharedDS;
        this.totalRequests = maxOperation;
        this.logName = logName;
        this.availableReouces = resourceSize;
    }

    public void run() {
        // ! Delay for 100 ms to make sure the generator is running first
        do {
            spawnNextInLine();
        } while (sharedDataStructure.getCurrentSize() > 0
                || sharedDataStructure.getProcecssedCount() <= this.totalRequests);
    }

    public void spawnNextInLine() {
        // ! Get an available resource ID wait/repeat if not available
        int freeResourceID = -1;
        do {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            freeResourceID = sharedDataStructure.getNextAvailableResourceID();
            if (freeResourceID == -1) {
                System.out.println("[Scheduler]All resources are busy!");
            }
        } while (freeResourceID == -1);
        System.out.println("[Scheduler]Resource ID get:" + freeResourceID);
        if (freeResourceID > this.availableReouces || freeResourceID < 1) {
            throw new IndexOutOfBoundsException("[Fatal]Resource ID invalid!");
        }

        // ! Get the next in line to be processed/roam next resource ID if current
        // resource ID has nothing
        Object[] next = sharedDataStructure.updateNextInLine(freeResourceID);
        if (next[0] == null) {
            do {
                sharedDataStructure.returnResource(freeResourceID);
                freeResourceID = sharedDataStructure.getNextAvailableResourceID(); // Since we just returned one, we are
                                                                                   // sure we have at least one resource
                                                                                   // ID that we can chase behind
                next = sharedDataStructure.updateNextInLine(freeResourceID);
            } while (next[0] == null);
        }

        // ! Populate information!
        int pid = (int) next[0];
        int RID = (int) next[1];
        int PLevel = (int) next[2];
        int SysopTime = (int) next[3];
        LocalDateTime sysrequesttime = (LocalDateTime) next[4];

        String toWrite = "[Scheduler] Started to process request: PID: " + pid + " RID: " + RID + " Priority: " + PLevel
                + " OP time: " + SysopTime + " at " + dtf.format(getSystemTime());
        // System.out.println(toWrite);
        writeLog(toWrite);
        Runnable fakeRunnable = new fakeProcess(sharedDataStructure, pid, RID, PLevel, SysopTime, sysrequesttime,
                this.logName);
        Thread fakeThread = new Thread(fakeRunnable);
        fakeThread.start();
    }

    /**
     * File writer
     */
    private void writeLog(String tobeWritten) {
        try {
            File writeFile = new File(this.logName);
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