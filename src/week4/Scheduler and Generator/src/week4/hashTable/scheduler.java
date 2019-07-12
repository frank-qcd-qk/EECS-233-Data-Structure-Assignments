package week4.hashTable;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class scheduler implements Runnable {
    private Group1_Queue availableReouces;
    private sharedHash sharedDataStructuer;
    private int totalRequests;
    private String logName;
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public scheduler(sharedHash sharedDS, int resourceSize, int maxOperation, String logName) {
        this.sharedDataStructuer = sharedDS;
        this.availableReouces = new Group1_Queue<Integer>(resourceSize);
        this.totalRequests = maxOperation;
        this.logName = logName;
        for (int i = 1; i<=resourceSize; i++){
            availableReouces.Enqueue(i);
        }
    }

    public void spawnNextInLine() {
        int freeResourceID = (int) this.availableReouces.Dequeue();
        Object[] next = sharedDataStructuer.updateNextInLine(freeResourceID);
        while(next[0]==null){
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
                + " OP time: " + SysopTime + " at " + dtf.format(sysrequesttime);
        System.out.println(toWrite);
        writeLog(toWrite);
        while (fakeThread.isAlive()) {
            System.out.println("Waiting for the thread to finish!");
        }
        toWrite = "[Scheduler] Finished to process request: PID: " + pid + " RID: " + RID + " Priority: " + PLevel
                + " OP time: " + SysopTime + " at " + dtf.format(sysrequesttime);
        System.out.println(toWrite);
        writeLog(toWrite);
        this.availableReouces.Enqueue(freeResourceID);
    }

    public void run() {
        while (sharedDataStructuer.getCurrentAvailable() != 0 && this.totalRequests > 0) {
            if (this.availableReouces.Peek() != null) {
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
}