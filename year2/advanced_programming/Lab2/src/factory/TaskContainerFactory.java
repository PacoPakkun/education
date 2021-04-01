package factory;

import repo.Container;
import repo.QueueContainer;
import repo.StackContainer;

public class TaskContainerFactory implements Factory {

    private static TaskContainerFactory instance = null;

    private TaskContainerFactory() {
    }

    public static TaskContainerFactory getInstance() {
        if (instance == null) {
            instance = new TaskContainerFactory();
        }
        return instance;
    }

    @Override
    public Container createContainer(Strategy strategy) {
        if (strategy == Strategy.LIFO) {
            return new StackContainer();
        } else
            return new QueueContainer();
    }
}