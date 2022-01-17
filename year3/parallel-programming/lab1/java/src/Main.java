import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static class Subthread extends Thread {
        int N, M, n, m;
        int[][] f;
        double[][] w;
        int[][] v;
        int start;
        int stop;

        public Subthread(int n, int m, int n1, int m1, int[][] f, double[][] w, int[][] v, int start, int stop) {
            N = n;
            M = m;
            this.n = n1;
            this.m = m1;
            this.f = f;
            this.w = w;
            this.v = v;
            this.start = start;
            this.stop = stop;
        }

        // subthread care executa operatia de transformare pe mai multe linii (descompunere pe orizontala)
        public void run() {
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
                            suma += f[pos1][pos2] * w[pos1][pos2];
                        }
                    }
                    v[i][j] = suma;
                }
            }
        }
    }

    // executia transformarii paralelizata cu p threaduri
    static void paralel(int N, int M, int n, int m, int[][] f, double[][] w, int[][] v, int p) throws InterruptedException {
        int cat = N / p;
        int rest = N % p;
        int start = 0;
        int end;
        Thread[] threads = new Thread[p];

        for (int i = 0; i < p; i++) {
            if (rest > 0) {
                end = start + cat + 1;
                rest--;
            } else
                end = start + cat;

            threads[i] = new Subthread(N, M, n, m, f, w, v, start, end);
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
        int[][] v1 = new int[N][M];
        int[][] v2 = new int[N][M];
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
        long time_start = System.nanoTime();
        secvential(N, M, n, m, f, w, v1);
        long time_end = System.nanoTime();
        System.out.println("secvential:   " + String.valueOf(time_end - time_start));

        // PARALEL
        time_start = System.nanoTime();
        paralel(N, M, n, m, f, w, v2, p);
        time_end = System.nanoTime();
        System.out.println("paralel:      " + String.valueOf(time_end - time_start));

        if (!check(N, M, v1, v2)) throw new InterruptedException();
    }
}
