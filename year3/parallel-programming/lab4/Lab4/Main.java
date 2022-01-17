import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Random;

public class Main {
    private static final String baseDataPath = "data/10polinoame/";
    private static final int n = 10;   // numarul de polinoame
    private static final int p = 4;   // numarul de threaduri

    public static void main(String[] args) throws InterruptedException {
        long startTime, endTime;
//        generateFiles();

        Secvential secvential = new Secvential(n, baseDataPath);
        startTime = System.nanoTime();
        secvential.resolve();
        endTime = System.nanoTime();

        System.out.println((double) (endTime - startTime) / 1E6);
        secvential.writeToFile();

        Paralel paralel = new Paralel(n, p, baseDataPath);
        startTime = System.nanoTime();
        paralel.resolve();
        endTime = System.nanoTime();

        System.out.println((double) (endTime - startTime) / 1E6);
        paralel.writeToFile();

        try {
            boolean filesAreEqual = checkFiles(baseDataPath + "result_secvential.txt", baseDataPath + "result_paralel.txt");
            System.out.println("Files are equal: " + filesAreEqual);
        } catch (java.io.IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void generateFiles() {
        int exponent, coeficient;
        Random random = new Random();
        int nrMonoame = random.nextInt(100);

        //generam cate un fisier pentru fiecare polinom
        for (int i = 1; i <= n; i++) {
            try {
                File file = new File(baseDataPath + "polinom" + i + ".txt");
                boolean created = file.createNewFile();
                if (created) {
                    FileWriter writer = new FileWriter(baseDataPath + "polinom" + i + ".txt");

                    //fiecare fisier va contine pe o linie "coeficient monom"
                    //conditie: fisierele nu contin monoame cu coeficient egal cu 0
                    //numerele se creeaza prin generare de numere aleatoare

                    for (int j = 0; j < nrMonoame; j++) {
                        exponent = random.nextInt(999) + 1;
                        coeficient = random.nextInt(5000);

                        writer.write(exponent + " " + coeficient + "\n");
                    }

                    writer.close();
                }

            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    public static boolean checkFiles(String filename1, String filename2) throws IOException {
        File f1 = new File(filename1);
        File f2 = new File(filename2);
        byte[] b1 = Files.readAllBytes(f1.toPath());
        byte[] b2 = Files.readAllBytes(f2.toPath());
        return Arrays.equals(b1, b2);
    }
}
