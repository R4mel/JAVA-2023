package algorithm;

class BubbleSort {
    public static void bubble_sort(int[] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    Sort.swap(a, j, j + 1);
                }
            }
        }
    }
}

class SelectionSort{
    public static void selection_sort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[minIndex]) {
                    minIndex = j;
                }
            }
            Sort.swap(a, minIndex, i);
        }
    }
}

class MergeSort{
    public static void merge_sort(int[] a){
        
    }
}

public class Sort {
    public static void swap(int[] arr, int source, int target) {
        int tmp = arr[source];
        arr[source] = arr[target];
        arr[target] = tmp;
    }

    public static void printArray(int[] arr) {
        for (int data : arr) {
            System.out.print(data + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int[] item = new int[]{9, 7, 4, 6, 12, 8, 1};
        System.out.println("before");
        printArray(item);
        SelectionSort.selection_sort(item);
        System.out.println("after");
        printArray(item);
    }
}
