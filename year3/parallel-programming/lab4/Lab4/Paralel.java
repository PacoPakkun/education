import java.util.ArrayList;
import java.util.List;

public class Paralel {
    private final Polinom polinomRezultat;
    private final MyQueue queue;
    private final int n;
    private final int p;
    private final String baseDataPath;

    public Paralel(int n, int p, String baseDataPath) {
        this.polinomRezultat = new Polinom();
        this.queue = new MyQueue(n);
        this.n = n;
        this.p = p;
        this.baseDataPath = baseDataPath;
    }

    public void resolve() throws InterruptedException {
        List<MyThread> threads = new ArrayList<>(p);
        for (int i = 1; i < p; i++) {
            MyThread thread = new MyThread(queue, polinomRezultat);
            threads.add(thread);
        }

        Thread readerThread = new Thread(() -> {
            for (int i = 1; i <= n; i++) {
                String filename = baseDataPath + "polinom" + i + ".txt";
                try {
                    for (Element el : new Polinom(filename).getLinkedList()) {
                        queue.add(el);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                queue.add(null);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        readerThread.start();
        for (Thread t : threads)
            t.start();

        readerThread.join();
        for (Thread t : threads) {
            t.join();
        }
    }

    public void writeToFile() {
        polinomRezultat.writeToFile(baseDataPath + "result_paralel.txt");
    }
}

class MyThread extends Thread {
    private final MyQueue queue;
    private final Polinom polinomRezultat;

    public MyThread(MyQueue queue, Polinom polinomRezultat) {
        this.queue = queue;
        this.polinomRezultat = polinomRezultat;
    }

    @Override
    public void run() {
        while (true) {
            Element elementCurent = new Element();
            synchronized (queue) {
                try {
                    elementCurent = queue.remove();
                    if (elementCurent == null) {
                        queue.add(null);
                        return;
                    }
                } catch (InterruptedException ex) {
                    System.out.println(ex.getMessage());
                }
            }

            synchronized (polinomRezultat) {
                polinomRezultat.setElement(elementCurent);

            }
        }
    }
}
