import domain.MessageTask;
import domain.SortStrategy;
import domain.SortingTask;
import domain.Task;
import factory.Strategy;
import service.DelayTaskRunner;
import service.PrinterTaskRunner;
import service.StrategyTaskRunner;

import java.time.LocalDateTime;

public class Main {

    public static Task[] createTaskArray() {
        MessageTask t1 = new MessageTask("1", "Feedback lab1",
                "Ai obtinut 9.60", "Paco", "Ana", LocalDateTime.now());
        MessageTask t2 = new MessageTask("2", "Feedback lab2",
                "Ai obtinut 5.60", "Paco", "Ana", LocalDateTime.now());
        MessageTask t3 = new MessageTask("3", "Feedback lab3",
                "Ai obtinut 10", "Paco", "Ana", LocalDateTime.now());
        SortingTask t4 = new SortingTask("4", "BubbleSort {5 4 3 2 1}",
                new int[]{5, 4, 3, 2, 1}, SortStrategy.BUBBLE_SORT);
        SortingTask t5 = new SortingTask("5", "QuickSort {7 3 5 9 1}",
                new int[]{7, 3, 5, 9, 1}, SortStrategy.QUICK_SORT);
        return new Task[]{t1, t2, t3, t4, t5};
    }

    public static void main(String[] args) {
        Task[] tasks = createTaskArray();

        Strategy strategy;
        if (args[0].matches("FIFO"))
            strategy = Strategy.FIFO;
        else
            strategy = Strategy.LIFO;

        StrategyTaskRunner runner = new StrategyTaskRunner(strategy);
        for (Task t : tasks)
            runner.addTask(t);

        //runner.executeAll();

        //PrinterTaskRunner printerRunner = new PrinterTaskRunner(runner);
        //printerRunner.executeAll();

        DelayTaskRunner delayRunner = new DelayTaskRunner(runner);
        delayRunner.executeAll();
    }
}
