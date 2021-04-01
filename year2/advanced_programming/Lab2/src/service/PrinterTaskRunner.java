package service;

import java.time.LocalDateTime;

public class PrinterTaskRunner extends AbstractTaskRunner {
    public PrinterTaskRunner(TaskRunner taskRunner) {
        super(taskRunner);
    }

    @Override
    public void executeOneTask() {
        taskRunner.executeOneTask();
        System.out.println("Task executat la " + LocalDateTime.now());
    }
}
