package service;

import domain.Task;
import factory.Strategy;
import factory.TaskContainerFactory;
import repo.Container;

public class StrategyTaskRunner implements TaskRunner {
    private Container container;


    public StrategyTaskRunner(Strategy strategy) {
        this.container = TaskContainerFactory.getInstance().createContainer(strategy);
    }

    @Override
    public void executeOneTask() {
        if (!container.isEmpty()) {
            Task ms = container.remove();
            ms.execute();
        }

    }

    @Override
    public void executeAll() {
        while (!container.isEmpty()) {
            this.executeOneTask();
        }
    }

    @Override
    public void addTask(Task t) {
        container.add(t);
    }

    @Override
    public boolean hasTask() {
        return !container.isEmpty();
    }
}
