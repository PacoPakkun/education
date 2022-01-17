import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantLock;

public class Polinom {
    private final LinkedList<Element> linkedList;
    public final ReentrantLock lock;

    public LinkedList<Element> getLinkedList() {
        return linkedList;
    }

    public Polinom() {
        linkedList = new LinkedList<>();
        this.lock = new ReentrantLock();
    }

    public Polinom(String filename) {
        linkedList = new LinkedList<>();
        this.lock = new ReentrantLock();

        try {
            Scanner scanner = new Scanner(new FileReader(filename));
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(" ");
                linkedList.add(new Element(Integer.parseInt(data[0]), Integer.parseInt(data[1])));
            }
            scanner.close();

            sortExponenti();
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
    }

    public int getIndexOfExp(int exp) {
        for (int i = 0; i < linkedList.size(); i++) {
            if (linkedList.get(i).exponent == exp) {
                return i;
            }
        }
        return -1;
    }

    public void setElement(Element element) throws InterruptedException {
//        lock.lock();
        int index = getIndexOfExp(element.exponent);
        if (index != -1) {
            Element temp = linkedList.get(index);
            temp.add(element.coeficient);
            linkedList.set(index, temp);
        }
//        lock.unlock();
    }

    public void sortExponenti() {
        for (int i = 0; i < linkedList.size() - 1; i++) {
            for (int j = i + 1; j < linkedList.size(); j++) {
                if (linkedList.get(i).exponent > linkedList.get(j).exponent) {
                    Element temp = linkedList.get(i);
                    linkedList.set(i, linkedList.get(j));
                    linkedList.set(j, temp);
                }
            }
        }
    }

    public void writeToFile(String filename) {
        sortExponenti();
        try {
            FileWriter writer = new FileWriter(filename);
            for (Element element : linkedList) {
                writer.write(element.coeficient + " " + element.exponent + '\n');
            }
            writer.close();
        } catch (java.io.IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
