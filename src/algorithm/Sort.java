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

class SelectionSort {
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

class MergeSort {
    public static void merge_sort(int[] a, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            merge_sort(a, p, q);
            merge_sort(a, q + 1, r);
            merge(a, p, q, r);
        }
    }

    public static void merge(int[] a, int p, int q, int r) {
        int i = p, j = q + 1, k = p;
        int[] tmp = new int[a.length];
        while (i <= q && j <= r) {
            if (a[i] < a[j]) {
                tmp[k++] = a[i++];
            } else tmp[k++] = a[j++];
        }
        while (i <= q) {
            tmp[k++] = a[i++];
        }
        while (j <= r) {
            tmp[k++] = a[j++];
        }
        for (i = p; i <= r; i++) {
            a[i] = tmp[i];
        }

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
