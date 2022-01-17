import java.util.LinkedList;

public class MyQueue {
    private final LinkedList<Element> queue;
    private final int n;
    private int count;

    public MyQueue(int n) {
        this.n = n;
        this.count = 0;
        this.queue = new LinkedList<>();
    }

    synchronized public void add(Element element) throws InterruptedException {
        while(count == n) {
            this.wait();
        }

        queue.add(element);
        ++count;
        this.notifyAll();
    }

    synchronized public Element remove() throws InterruptedException {
        while(count == 0) {
            this.wait();
        }

        --count;
        Element element = queue.get(0);
        queue.remove(0);
        this.notifyAll();
        return element;
    }
}
