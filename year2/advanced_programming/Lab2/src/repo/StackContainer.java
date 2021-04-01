package repo;

import domain.Task;

public class StackContainer extends AbstractContainer {

    public StackContainer() {
        super();
    }

    @Override
    public void add(Task task) {
        if (tasks.length == size) {
            Task t[] = new Task[tasks.length * 2];
            System.arraycopy(tasks, 0, t, 0, tasks.length);
            tasks = t;
        }
        tasks[size] = task;
        size++;
    }

}
