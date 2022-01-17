import java.util.ArrayList;
import java.util.List;

public class Paralel {
    private final Polinom polinomRezultat;
    private final MyQueue queue;
    private final int n;
    private final int p1;
    private final int p2;
    private final String baseDataPath;

    public Paralel(int n, int p1, int p2, String baseDataPath) {
        this.polinomRezultat = new Polinom();
        this.queue = new MyQueue(n);
        this.n = n;
        this.p1 = p1;
        this.p2 = p2;
        this.baseDataPath = baseDataPath;
    }

    public void resolve() throws InterruptedException {
        List<MyThread> workerThreads = new ArrayList<>(p2);
        for (int i = 0; i < p2; i++) {
            MyThread thread = new MyThread(queue, polinomRezultat);
            workerThreads.add(thread);
        }

        List<Thread> readerThreads = new ArrayList<>(p1);
        for (int i = 0; i < p1; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                for (int j = (finalI * (n / p1)) + 1; j <= (finalI + 1) * (n / p1); j++) {
                    String filename = baseDataPath + "polinom" + j + ".txt";
                    try {
                        for (Element el : new Polinom(filename).getLinkedList()) {
                            synchronized (queue) {
                                queue.add(el);
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            readerThreads.add(thread);
        }


        for (Thread t : readerThreads)
            t.start();
        for (Thread t : workerThreads)
            t.start();

        for (Thread t : readerThreads)
            t.join();
        try {
            synchronized (queue) {
                queue.add(null);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (Thread t : workerThreads)
            t.join();
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

            try {
                polinomRezultat.setElement(elementCurent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
