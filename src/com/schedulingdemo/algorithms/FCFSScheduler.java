// package com.schedulingdemo.algorithms;

// import com.schedulingdemo.Process;
// import com.schedulingdemo.SchedulingAlgorithm;

// import java.util.List;

// public class FCFSScheduler implements SchedulingAlgorithm {
//     @Override
//     public String schedule(List<Process> processes) {
//         StringBuilder result = new StringBuilder("First-Come, First-Served Scheduling:\n");
//         int currentTime = 0;
//         for (Process process : processes) {
//             if (currentTime < process.getArrivalTime()) {
//                 currentTime = process.getArrivalTime();
//             }
//             result.append("Process ").append(process.getName()).append(" starts at time ").append(currentTime).append("\n");
//             currentTime += process.getBurstTime();
//             result.append("Process ").append(process.getName()).append(" finishes at time ").append(currentTime).append("\n");
//         }
//         result.append("\n");
//         return result.toString();
//     }
// }

package com.schedulingdemo.algorithms;

import com.schedulingdemo.Process;
import com.schedulingdemo.SchedulingAlgorithm;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class FCFSScheduler implements SchedulingAlgorithm {
    @Override
    public String schedule(List<Process> processes) {
        StringBuilder result = new StringBuilder("First-Come, First-Served Scheduling:\n");
        
        // Sort processes by arrival time
        List<Process> sortedProcesses = new ArrayList<>(processes);
        sortedProcesses.sort(Comparator.comparingInt(Process::getArrivalTime));

        int currentTime = 0;
        for (Process process : sortedProcesses) {
            if (currentTime < process.getArrivalTime()) {
                currentTime = process.getArrivalTime();
            }
            result.append("Process ").append(process.getName())
                  .append(" starts at time ").append(currentTime).append("\n");
            
            currentTime += process.getBurstTime();
            
            result.append("Process ").append(process.getName())
                  .append(" finishes at time ").append(currentTime).append("\n");
        }
        
        result.append("\n");
        return result.toString();
    }
}