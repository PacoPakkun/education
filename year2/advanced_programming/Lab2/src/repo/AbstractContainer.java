package repo;

import domain.Task;

public abstract class AbstractContainer implements Container {
    protected Task[] tasks;
    protected int size;

    public AbstractContainer() {
        tasks = new Task[10];
        size = 0;
    }

    @Override
    public Task remove() {
        if (!isEmpty()) {
            size--;
            return tasks[size];
        }
        return null;
    }

    public abstract void add(Task task);

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
