import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class Main {
    static class Subthread extends Thread {
        double[] a;
        double[] b;
        double[] c;
        int start;
        int stop;

        public Subthread(double[] a, double[] b, double[] c, int start, int stop) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.start = start;
            this.stop = stop;
        }

        public void run() {
            for (int i = start; i < stop; i++) {
//                c[i] = a[i] + b[i];
                c[i] = Math.sqrt(Math.pow(a[i], 4) + Math.pow(b[i], 4));
            }
        }
    }

    static void paralel(double[] a, double[] b, double[] c, int p) throws InterruptedException {
        int cat = a.length / p;
        int rest = a.length % p;
        int start = 0;
        int end;
        Thread[] threads = new Thread[p];

//        System.out.println(Thread.activeCount());
//        Thread[] t = new Thread[Thread.activeCount()];
//        int nt = Thread.enumerate(t);
//        for (int i = 0; i < Thread.activeCount(); i++) {
//            System.out.println(t[i].getName());
//        }

        for (int i = 0; i < p; i++) {
            if (rest > 0) {
                end = start + cat + 1;
                rest--;
            } else
                end = start + cat;

            threads[i] = new Subthread(a, b, c, start, end);
            threads[i].start();

            start = end;
        }

        for (int i = 0; i < p; i++) {
            threads[i].join();
        }
    }

    static class Subthread2 extends Thread {
        double[] a;
        double[] b;
        double[] c;
        int start;
        int stop;
        int pas;
        int n;

        public Subthread2(double[] a, double[] b, double[] c, int start, int pas, int n) {
            this.a = a;
            this.b = b;
            this.c = c;
            this.start = start;
            this.pas = pas;
            this.n = n;
        }


        public void run() {
            for (int i = start; i < n; i += pas) {
                c[i] = Math.sqrt(a[i] * a[i] * a[i] * a[i] + b[i] * b[i] * b[i] * b[i]);
            }
        }
    }

    static void paralel2(double[] a, double[] b, double[] c, int p) throws InterruptedException {
        int pas = p;

        Thread[] threads = new Thread[p];

        for (int i = 0; i < p; i++) {
            threads[i] = new Subthread2(a, b, c, i, p, a.length);
            threads[i].start();
        }

        for (int i = 0; i < p; i++) {
            threads[i].join();
        }
    }

    static void secvential(double[] a, double[] b, double[] c) {
        for (int i = 0; i < a.length; i++) {
//            c[i] = a[i] + b[i];
            c[i] = Math.sqrt(Math.pow(a[i], 4) + Math.pow(b[i], 4));
        }
    }

    static boolean check(double[] a, double[] b) {
        if (a.length != b.length)
            return false;
        for (int i = 0; i < a.length; i++) {
            if (a[i] != b[i])
                return false;
        }
        return true;
    }

    static void print_array(double[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) throws InterruptedException {
        int n = 10000000;
        double[] a = new double[n];
        double[] b = new double[n];
        double[] c = new double[n];
        double[] d = new double[n];
        double[] e = new double[n];

        for (int i = 0; i < n; i++) {
            a[i] = i;
            b[i] = n - i;
        }

        // secvential
        long time_start = System.nanoTime();
        secvential(a, b, c);
        long time_end = System.nanoTime();

//        print_array(c);
        System.out.println(time_end - time_start);
        System.out.println();

        // paralel 1
        time_start = System.nanoTime();
        paralel(a, b, d, 4);
        time_end = System.nanoTime();

        System.out.println(check(c, d));
//        print_array(d);
        System.out.println(time_end - time_start);
        System.out.println();

        // paralel 2
        time_start = System.nanoTime();
        paralel2(a, b, e, 4);
        time_end = System.nanoTime();

        System.out.println(check(c, e));
//        print_array(d);
        System.out.println(time_end - time_start);
        System.out.println();
    }
}
