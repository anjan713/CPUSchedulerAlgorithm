// package com.schedulingdemo.algorithms;

// public class RoundRobinScheduler {
    
// }

package com.schedulingdemo.algorithms;

import com.schedulingdemo.Process;
import com.schedulingdemo.SchedulingAlgorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RoundRobinScheduler implements SchedulingAlgorithm {
    private final int timeQuantum;

    public RoundRobinScheduler(int timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    @Override
    public String schedule(List<Process> processes) {
        StringBuilder result = new StringBuilder("Round Robin Scheduling (Time Quantum: " + timeQuantum + "):\n");
        Queue<Process> queue = new LinkedList<>(processes);
        int currentTime = 0;
        while (!queue.isEmpty()) {
            Process currentProcess = queue.poll();
            if (currentProcess.getRemainingTime() <= timeQuantum) {
                result.append("Process ").append(currentProcess.getName()).append(" starts at time ").append(currentTime).append("\n");
                currentTime += currentProcess.getRemainingTime();
                result.append("Process ").append(currentProcess.getName()).append(" finishes at time ").append(currentTime).append("\n");
            } else {
                result.append("Process ").append(currentProcess.getName()).append(" starts at time ").append(currentTime).append("\n");
                currentTime += timeQuantum;
                currentProcess.setRemainingTime(currentProcess.getRemainingTime() - timeQuantum);
                result.append("Process ").append(currentProcess.getName()).append(" is preempted at time ").append(currentTime).append("\n");
                queue.offer(currentProcess);
            }
        }
        result.append("\n");
        return result.toString();
    }
}