package domain;

import utils.Constants;

import java.time.LocalDateTime;

public class SortingTask extends Task {

    private abstract class AbstractSorter {
        public abstract void sort(int vector[]);
    }

    private class BubbleSorter extends AbstractSorter {
        @Override
        public void sort(int arr[]) {
            int n = arr.length;
            for (int i = 0; i < n - 1; i++)
                for (int j = 0; j < n - i - 1; j++)
                    if (arr[j] > arr[j + 1]) {
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                    }
        }
    }

    private class QuickSorter extends AbstractSorter {
        private int partition(int arr[], int low, int high) {
            int pivot = arr[high];
            int i = (low - 1);
            for (int j = low; j < high; j++) {
                if (arr[j] < pivot) {
                    i++;
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
            int temp = arr[i + 1];
            arr[i + 1] = arr[high];
            arr[high] = temp;

            return i + 1;
        }

        private void quick(int arr[], int low, int high) {
            if (low < high) {
                int pi = partition(arr, low, high);
                quick(arr, low, pi - 1);
                quick(arr, pi + 1, high);
            }
        }

        @Override
        public void sort(int arr[]) {
            int n = arr.length;
            quick(arr, 0, n - 1);
        }
    }

    int vector[];
    AbstractSorter sorter;

    @Override
    public String toString() {
        String s = "[ " + super.toString() + " ] ";

        for (int el : vector) {
            s += Integer.toString(el) + " ";
        }
        return s;
    }

    public SortingTask(String taskId, String desc, int vector[], SortStrategy strategy) {
        super(taskId, desc);
        this.vector = vector;
        if (strategy == SortStrategy.BUBBLE_SORT)
            sorter = new BubbleSorter();
        else sorter = new QuickSorter();
    }

    @Override
    public void execute() {
        sorter.sort(vector);
        System.out.println(toString());
    }
}
