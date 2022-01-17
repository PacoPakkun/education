import java.util.concurrent.locks.ReentrantLock;

class Element {
    public Integer exponent;
    public Integer coeficient;
    public ReentrantLock lock;

    public Element(int exponent, int coeficient) {
        this.exponent = exponent;
        this.coeficient = coeficient;
        this.lock = new ReentrantLock();
    }

    public Element() {

    }

    public void add(Integer coef) {
        lock.lock();
        this.coeficient += coef;
        lock.unlock();
    }
}