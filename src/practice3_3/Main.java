package practice3_3;

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

    public static void printArrayElement(Scanner s, int arr[][]) {
        int r = 0, c = 0;
        try {
            System.out.print("dividen? ");
            int a = s.nextInt();
            System.out.print("divisor? ");
            int b = s.nextInt();
            r = a / b;
            c = a % b;
            System.out.println("arr[" + r + "][" + c + "]: " + arr[r][c]);
        }
        catch(ArrayIndexOutOfBoundsException e){
            System.out.println("arr[" + r + "][" + c + "]: " + "array index is out of BOUNDS");
        }
        catch(InputMismatchException e){
            System.out.println("input an INTEGER");
            s.nextLine();
        }
        catch(ArithmeticException e){
            System.out.println("divisor is ZERO");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] arr;

        arr = makeArray(scanner);
        printArray(arr);
        System.out.println();

        for (; ; ) { // 아래 과정을 계속 반복 수행한다.
            printArrayElement(scanner, arr);
            System.out.print("continue? ");
            String a = scanner.next();
            System.out.println();
            if (a.equals("yes")) continue;
            break;
        }

        scanner.close();
        System.out.println("Done.");
    }
}
//