package socialnetwork.utils;

public interface Observer<E extends Event> {
    void update(E e);
}