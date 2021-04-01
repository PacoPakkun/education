package socialnetwork.utils;

import socialnetwork.domain.Utilizator;

public class ChangeEvent implements Event {

    private ChangeEventType type;
    private Utilizator data, oldData;

    public ChangeEvent() {
    }

    public ChangeEvent(ChangeEventType type, Utilizator data) {
        this.type = type;
        this.data = data;
    }

    public ChangeEvent(ChangeEventType type, Utilizator data, Utilizator oldData) {
        this.type = type;
        this.data = data;
        this.oldData = oldData;
    }

    public ChangeEventType getType() {
        return type;
    }

    public Utilizator getData() {
        return data;
    }

    public Utilizator getOldData() {
        return oldData;
    }
}
