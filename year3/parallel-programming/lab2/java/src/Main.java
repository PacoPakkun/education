import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    static class Subthread extends Thread {
        int thread, p, N, M, n, m;
        int[][] f;
        double[][] w;
        int start;
        int stop;
        CyclicBarrier barrier;
        int[][] temp1;
        int[][] temp2;

        public Subthread(int thread, int p, int n, int m, int n1, int m1, int[][] f, double[][] w, int start, int stop, CyclicBarrier barrier) {
            this.thread = thread;
            this.p = p;
            N = n;
            M = m;
            this.n = n1;
            this.m = m1;
            this.f = f;
            this.w = w;
            this.start = start;
            this.stop = stop;
            this.barrier = barrier;
        }

        // executarea transformarii pe matrice
        void execute() {
            int temp[][] = new int[n][M];
            for (int i = 0; i < n / 2; i++) {
                for (int j = 0; j < M; j++) {
                    temp[i][j] = temp1[i][j];
                }
            }
            for (int i = n / 2; i < n; i++) {
                for (int j = 0; j < M; j++) {
                    temp[i][j] = f[start + i - n / 2][j];
                }
            }
            for (int i = start; i < stop; i++) {
                for (int j = 0; j < M; j++) {
                    int suma = 0;
                    for (int k = -n / 2; k <= n / 2; k++) {
                        for (int l = -m / 2; l <= m / 2; l++) {
                            int pos1 = i + k, pos2 = j + l;
                            if (pos1 < 0)
                                pos1 = 0;
                            if (pos1 > N - 1)
                                pos1 = N - 1;
                            if (pos2 < 0)
                                pos2 = 0;
                            if (pos2 > M - 1)
                                pos2 = M - 1;

                            if (pos1 < start) {
                                suma += temp1[pos1 - start + m / 2][pos2] * w[pos1][pos2];
                            } else {
                                if (pos1 > stop - 1) {
                                    suma += temp2[pos1 - stop][pos2] * w[pos1][pos2];
                                } else {
                                    suma += temp[pos1 - i + n / 2][pos2] * w[pos1][pos2];
                                }
                            }
                        }
                    }
                    f[i][j] = suma;
                }
                for (int k = 1; k < n; k++) {
                    for (int l = 0; l < M; l++) {
                        temp[k - 1][l] = temp[k][l];
                    }
                }
                if (i < stop - 1) {
                    for (int l = 0; l < M; l++) {
                        if (i + n / 2 + 1 > stop - 1)
                            temp[n - 1][l] = temp2[i + n / 2 + 1 - stop][l];
                        else
                            temp[n - 1][l] = f[i + n / 2 + 1][l];
                    }
                }
            }
        }

        // subthread care pregateste operatia de transformare pe mai multe linii (descompunere pe orizontala) direct pe matricea initiala
        // copiaza intr-o lista temporara valorile care intra in conflict cu alte threaduri
        // pentru sincronizare foloseste bariere cu restul threadurilor
        public void run() {
            try {
                temp1 = new int[n / 2][M];
                temp2 = new int[n / 2][M];
                if (thread != 0) {
                    for (int i = start - m / 2; i < start; i++) {
                        if (i >= 0)
                            if (M >= 0) System.arraycopy(f[i], 0, temp1[i - start + m / 2], 0, M);
                    }
                }
                if (thread != p - 1) {
                    for (int i = stop; i < stop + m / 2; i++) {
                        if (i < N)
                            if (M >= 0) System.arraycopy(f[i], 0, temp2[i - stop], 0, M);
                    }
                }
                barrier.await();
                execute();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // executia transformarii paralelizata cu p threaduri
    static void paralel(int N, int M, int n, int m, int[][] f, double[][] w, int p) throws InterruptedException {
        int cat = N / p;
        int rest = N % p;
        int start = 0;
        int end;
        Thread[] threads = new Thread[p];
        CyclicBarrier barrier = new CyclicBarrier(p);

        for (int i = 0; i < p; i++) {
            if (rest > 0) {
                end = start + cat + 1;
                rest--;
            } else
                end = start + cat;

            threads[i] = new Subthread(i, p, N, M, n, m, f, w, start, end, barrier);
            threads[i].start();

            start = end;
        }

        for (int i = 0; i < p; i++) {
            threads[i].join();
        }
    }

    // executia transformarii secventiala, intr-o parcurgere pe linii si coloane
    static void secvential(int N, int M, int n, int m, int[][] f, double[][] w, int[][] v) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                int suma = 0;
                for (int k = -n / 2; k <= n / 2; k++) {
                    for (int l = -m / 2; l <= m / 2; l++) {
                        int pos1 = i + k, pos2 = j + l;
                        if (pos1 < 0)
                            pos1 = 0;
                        if (pos1 > N - 1)
                            pos1 = N - 1;
                        if (pos2 < 0)
                            pos2 = 0;
                        if (pos2 > M - 1)
                            pos2 = M - 1;
                        suma += f[pos1][pos2] * w[pos1][pos2];
                    }
                }
                v[i][j] = suma;
            }
        }
    }

    // verifica echivalenta a 2 matrici
    public static boolean check(int N, int M, int[][] a, int[][] b) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (a[i][j] != b[i][j])
                    return false;
            }
        }
        return true;
    }

    // afiseaza o matrice
    public static void show(int N, int M, int[][] f) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                System.out.print(String.valueOf(f[i][j]) + " ");
            }
            System.out.println();
        }
    }

    // populeaza fisierul cu datele necesare executiei
    // genereaza matrici de valori aleatoare
    public static void populate(int N, int M, int n, int m, int p) throws IOException {
        FileWriter myWriter = new FileWriter("date.txt");
        myWriter.write(String.format("%d %d %d %d %d\n", N, M, n, m, p));
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                myWriter.write(String.valueOf(new Random(System.nanoTime()).nextInt(100)) + " ");
            }
            myWriter.write('\n');
        }
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                myWriter.write(String.valueOf(new Random(System.nanoTime()).nextDouble()) + " ");
            }
            myWriter.write('\n');
        }
        myWriter.close();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        // POPULARE
//        populate(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
        populate(10, 10, 3, 3, 2);

        // CITIRE
        File myObj = new File("date.txt");
        Scanner myReader = new Scanner(myObj);
        String[] input = myReader.nextLine().split(" ");
        int N = Integer.parseInt(input[0]), M = Integer.parseInt(input[1]);
        int n = Integer.parseInt(input[2]), m = Integer.parseInt(input[3]);
        int p = Integer.parseInt(input[4]);
        int[][] f = new int[N][M];
        double[][] w = new double[N][M];
        int[][] v = new int[N][M];
        for (int i = 0; i < N; i++) {
            input = myReader.nextLine().split(" ");
            for (int j = 0; j < M; j++) {
                f[i][j] = Integer.parseInt(input[j]);
            }
        }
        for (int i = 0; i < N; i++) {
            input = myReader.nextLine().split(" ");
            for (int j = 0; j < M; j++) {
                w[i][j] = Double.parseDouble(input[j]);
            }
        }
        myReader.close();


        // EXECUTIE
        // SECVENTIAL
        long time_start = System.nanoTime();
        secvential(N, M, n, m, f, w, v);
        long time_end = System.nanoTime();
        System.out.println("secvential:   " + String.valueOf((double) (time_end - time_start) / 1E6));

        // PARALEL
        time_start = System.nanoTime();
        paralel(N, M, n, m, f, w, p);
        time_end = System.nanoTime();
        System.out.println("paralel:   " + String.valueOf((double) (time_end - time_start) / 1E6));

        if (!check(N, M, f, v)) throw new InterruptedException("result error");
    }
}
