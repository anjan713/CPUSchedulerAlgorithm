
package com.schedulingdemo;

import java.util.List;

public interface SchedulingAlgorithm {
    String schedule(List<Process> processes);
}