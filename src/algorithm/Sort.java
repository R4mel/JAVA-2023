package algorithm;

import java.util.*;

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


class QuickSort {
    // 배열 마지막 값을 pivot으로 설정, 작은 값들, 피봇, 큰 값들로 분할
    // q = pivot를 배열의 맞는 위치에 넣어준 뒤 배열의 시작부터 q-1까지, q+1부터 배열의 끝까지 recursion으로 정렬
    // test
}

class HeapSort {
    // O(logN)
    // test
    // asdf
}

class RadixSort {
    // 배열의 인덱스값을 일의자리를 기준으로 정렬해주고 인덱스값에 맞춰서 정렬
}

class CountingSort {
    // 배열의 값을 인덱스로 생각하고 카운팅해서 배열에 넣기
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
