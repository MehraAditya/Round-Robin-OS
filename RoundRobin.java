import java.util.Random;

public class RoundRobin {

    
 private static final int MAX_PROCESSES = 20;
 private static final int TIME_QUANTUM = 5;


    public static void main(String[] args) {
        Random rand = new Random();
        int n = rand.nextInt(MAX_PROCESSES) + 1;
        Process[] pro = new Process[n];
        CreateProcess(pro, n);
        RoundRobinAlgo(pro, n, 100);
        Display(pro, n);
    }
    
    public static void CreateProcess(Process[] pro, int n) {
        for (int i = 0; i < n; i++) {
            pro[i] = new Process();
            pro[i].pid = i + 1;
            pro[i].ArrivalTime = new Random().nextInt(100);
            pro[i].BurstTime = new Random().nextInt(50) + 1;
            pro[i].RemainingTime = pro[i].BurstTime;
            pro[i].WaitTime = 0;
            pro[i].TurnAroundTime = 0;
        }
    }
    
    public static void RoundRobinAlgo(Process[] pro, int n, int limit) {
        int current_time = 0;
        int completed_processes = 0;
        int current_process = 0;
        int time_slice = TIME_QUANTUM;
        while (completed_processes < n && current_time < limit) {
            if (pro[current_process].RemainingTime > 0) {
                if (pro[current_process].RemainingTime <= time_slice) {
                    current_time += pro[current_process].RemainingTime;
                    time_slice -= pro[current_process].RemainingTime;
                    pro[current_process].RemainingTime = 0;
                    completed_processes++;
                    pro[current_process].TurnAroundTime = Math.abs(current_time - pro[current_process].ArrivalTime) ;
                    pro[current_process].WaitTime = Math.abs(pro[current_process].TurnAroundTime - pro[current_process].BurstTime);
                } else {
                    current_time += time_slice;
                    pro[current_process].RemainingTime -= time_slice;
                    time_slice = TIME_QUANTUM;
                }
            }
            current_process = (current_process + 1) % n;
        }
    }
    
    public static void Display(Process[] pro, int n) {
        float total_WaitTime = 0;
        float total_TurnAroundTime = 0;
    
        System.out.printf("%-4s %-12s %-11s %-10s %-16s\n", "PID", "Arrival Time", "Burst Time", "Wait Time", "Turnaround Time");
        
        for (int i = 0; i < n; i++) {
            System.out.printf("%-4d %-12d %-11d %-10d %-16d\n", pro[i].pid, pro[i].ArrivalTime, pro[i].BurstTime, pro[i].WaitTime, pro[i].TurnAroundTime);
            total_WaitTime += pro[i].WaitTime;
            total_TurnAroundTime += pro[i].TurnAroundTime;
        }
        float avg_WaitTime = total_WaitTime / n;
        float avg_TurnAroundTime = total_TurnAroundTime / n;
        System.out.printf("Average wait time: %.2f\n", avg_WaitTime);
        System.out.printf("Average turnaround time: %.2f\n", avg_TurnAroundTime);
    }
    
    static class Process {
        int pid;
        int ArrivalTime;
        int BurstTime;
        int RemainingTime;
        int WaitTime;
        int TurnAroundTime;
    }
    


}