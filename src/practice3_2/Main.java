package practice3_2;

import java.util.*;

public class Main {
    public static int[][] makeArray(Scanner s) {
        System.out.print("array size? ");
        int size = s.nextInt();
        int[][] arr = new int[size][];
        arr[0] = new int[size];
        for (int i = arr.length, j = 0; i > 0; i--, j++) {
            arr[j] = new int[i];
        }
        return arr;
    }

    public static void printArray(int arr[][]) {
        for(int i=0; i<arr.length; i++){
            int tmp = i;
            System.out.print("arr[" + i + "] ");
            for(int j=0; j<arr[i].length; j++){
                arr[i][j] += tmp;
                tmp++;
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        int[][] arr;

        for (; ; ) { // 아래 과정을 계속 반복 수행한다.
            arr = makeArray(scanner);
            printArray(arr);
            System.out.print("continue? ");
            String ans;
            ans = scanner.next();
            System.out.println();
            if (ans.equals("yes")) continue;
            break;
        }

        scanner.close();
        System.out.println("Done.");
    }
}
//
