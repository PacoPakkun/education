import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Populate {

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

    public static void main(String[] args) throws IOException {
        populate(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
    }
}
