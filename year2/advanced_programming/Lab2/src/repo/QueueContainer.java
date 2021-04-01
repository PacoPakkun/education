package repo;

import domain.Task;

public class QueueContainer extends AbstractContainer {

    public QueueContainer() {
        super();
    }

    @Override
    public void add(Task task) {
        if (tasks.length == size) {
            Task t[] = new Task[tasks.length * 2];
            System.arraycopy(tasks, 0, t, 0, tasks.length);
            tasks = t;
        }
        for (int i = size; i > 0; i--) {
            tasks[i] = tasks[i - 1];
        }
        tasks[0] = task;
        size++;
    }
}
