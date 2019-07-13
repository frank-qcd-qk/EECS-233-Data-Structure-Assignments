package week4.priorityQueue;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class scheduler implements Runnable {
    private Group1_Queue availableReouces;
    private frankDS sharedDataStructuer;
    private int totalRequests;
    private String logName;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public scheduler(frankDS sharedDS, int resourceSize, int maxOperation, String logName) {
        this.sharedDataStructuer = sharedDS;
        this.availableReouces = new Group1_Queue<Integer>(resourceSize);
        this.totalRequests = maxOperation;
        this.logName = logName;
        for (int i = 1; i <= resourceSize; i++) {
            availableReouces.Enqueue(i);
        }
    }

    public void spawnNextInLine() {
        int freeResourceID = (int) this.availableReouces.Dequeue();
        Object[] next = sharedDataStructuer.updateNextInLine(freeResourceID);
        while (next[0] == null) {
            this.availableReouces.Enqueue(freeResourceID);
            freeResourceID = (int) this.availableReouces.Dequeue();
            next = sharedDataStructuer.updateNextInLine(freeResourceID);
        }
        int pid = (int) next[0];
        int RID = (int) next[1];
        int PLevel = (int) next[2];
        int SysopTime = (int) next[3];
        LocalDateTime sysrequesttime = (LocalDateTime) next[4];

        Runnable fakeRunnable = new fakeProcess(pid, RID, PLevel, SysopTime, sysrequesttime, this.logName);
        Thread fakeThread = new Thread(fakeRunnable);
        fakeThread.start();
        try {
            fakeThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String toWrite = "[Scheduler] Started to process request: PID: " + pid + " RID: " + RID + " Priority: " + PLevel
                + " OP time: " + SysopTime + " at " + dtf.format(getSystemTime());
        // System.out.println(toWrite);
        writeLog(toWrite);
        while (fakeThread.isAlive()) {
            System.out.println("[Scheduler]Waiting for the thread to finish!");
        }
        toWrite = "[Scheduler] Finished to process request: PID: " + pid + " RID: " + RID + " Priority: " + PLevel
                + " OP time: " + SysopTime + " at " + dtf.format(getSystemTime());
        System.out.println(toWrite);
        writeLog(toWrite);
        this.availableReouces.Enqueue(freeResourceID);
        sharedDataStructuer.completeOne();
    }

    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while (sharedDataStructuer.getCurrentSize() > 0) {
            if (this.availableReouces.Peek() != null) {
                System.out.println("[Scheduler]Currently have "+sharedDataStructuer.getCurrentSize()+" left");
                spawnNextInLine();
            } else {
                System.out.println("[Scheduler] All Resource Busy!");
            }
        }
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