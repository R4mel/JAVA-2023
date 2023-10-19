package practice3_1;

import java.util.*;

public class Main {
    public static int[][] makeArray(Scanner s) {
        System.out.println("array size? ");
        int size = s.nextInt();
        return new int[size][size];
    }

    public static void printArray(int arr[][]) {
        int tmp = 1;
        for (int i = 0; i < arr.length; i++) { // 0 1 2 3 4
            System.out.print("arr[" + i + "] ");
            for (int j = 0; j < arr[0].length; j++) { // 0 1 2 3 4
                if ((i + j + 1) / arr.length > 0) {
                    arr[i][j] = 1;
                }
                if(arr[i][j] != 0 && i != 0){
                    arr[i][j] += tmp;
                    tmp++;
                }
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
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