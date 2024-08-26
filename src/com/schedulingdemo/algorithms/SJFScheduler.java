// package com.schedulingdemo.algorithms;

// public class SJFScheduler {
    
// }

// package com.schedulingdemo.algorithms;

// import com.schedulingdemo.Process;
// import com.schedulingdemo.SchedulingAlgorithm;

// import java.util.ArrayList;
// import java.util.Comparator;
// import java.util.List;

// public class SJFScheduler implements SchedulingAlgorithm {
//     @Override
//     public String schedule(List<Process> processes) {
//         StringBuilder result = new StringBuilder("Shortest Job First Scheduling:\n");
//         List<Process> remainingProcesses = new ArrayList<>(processes);
//         int currentTime = 0;
//         while (!remainingProcesses.isEmpty()) {
//             List<Process> availableProcesses = remainingProcesses.stream()
//                     .filter(p -> p.getArrivalTime() <= currentTime)
//                     .toList();
            
//             if (availableProcesses.isEmpty()) {
//                 currentTime++;
//                 continue;
//             }

//             Process shortestJob = availableProcesses.stream()
//                     .min(Comparator.comparingInt(Process::getBurstTime))
//                     .orElseThrow();

//             result.append("Process ").append(shortestJob.getName()).append(" starts at time ").append(currentTime).append("\n");
//             currentTime += shortestJob.getBurstTime();
//             result.append("Process ").append(shortestJob.getName()).append(" finishes at time ").append(currentTime).append("\n");

//             remainingProcesses.remove(shortestJob);
//         }
//         result.append("\n");
//         return result.toString();
//     }
// }


package com.schedulingdemo.algorithms;

import com.schedulingdemo.Process;
import com.schedulingdemo.SchedulingAlgorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class SJFScheduler implements SchedulingAlgorithm {
    @Override
    public String schedule(List<Process> processes) {
        StringBuilder result = new StringBuilder("Shortest Job First Scheduling:\n");
        List<Process> remainingProcesses = new ArrayList<>(processes);
        int currentTime = 0;

        while (!remainingProcesses.isEmpty()) {
            List<Process> availableProcesses = new ArrayList<>();
            for (Process p : remainingProcesses) {
                if (p.getArrivalTime() <= currentTime) {
                    availableProcesses.add(p);
                }
            }

            if (availableProcesses.isEmpty()) {
                currentTime++;
                continue;
            }

            Process shortestJob = availableProcesses.stream()
                    .min(Comparator.comparingInt(Process::getBurstTime))
                    // .orElseThrow();
                    .orElseThrow(() -> new NoSuchElementException("No process found"));
                    

            result.append("Process ").append(shortestJob.getName())
                  .append(" starts at time ").append(currentTime).append("\n");
            currentTime += shortestJob.getBurstTime();
            result.append("Process ").append(shortestJob.getName())
                  .append(" finishes at time ").append(currentTime).append("\n");

            remainingProcesses.remove(shortestJob);
        }

        result.append("\n");
        return result.toString();
    }
}