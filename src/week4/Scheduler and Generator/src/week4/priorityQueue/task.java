package week4.priorityQueue;
import java.time.LocalDateTime;

public class task{
    public int PID;
    public int RID;
    public int P_level;
    public int OP_Time;
    public LocalDateTime SysRequestTime;
    public task(int PID, int RID, int P_level, int OP_Time, LocalDateTime SysGenTime){
        this.PID = PID;
        this.RID = RID;
        this.P_level = P_level;
        this.OP_Time = OP_Time;
        this.SysRequestTime = SysGenTime;
    }
    public task(){
        
    }
}