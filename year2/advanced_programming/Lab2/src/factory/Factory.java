package factory;

import repo.Container;

public interface Factory {
    Container createContainer(Strategy strategy);
}
