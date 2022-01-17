public class Secvential {
    private final Polinom polinomRezultat;
    private final int n;
    private final String baseDataPath;

    public Secvential(int n, String baseDataPath) {
        this.polinomRezultat = new Polinom();
        this.n = n;
        this.baseDataPath = baseDataPath;
    }

    public void resolve() throws InterruptedException {
        for (int i = 1; i <= n; i++) {
            String filename = baseDataPath + "polinom" + i + ".txt";
            Polinom polinom = new Polinom(filename);

            for (Element element : polinom.getLinkedList()) {
                polinomRezultat.setElement(element);
            }
        }
    }

    public void writeToFile() {
        polinomRezultat.writeToFile(baseDataPath + "result_secvential.txt");
    }
}