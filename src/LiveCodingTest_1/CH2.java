package LiveCodingTest_1;

import java.util.*;

public class CH2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        do {
            try {
                System.out.print("Enter a radius(-1 to exit) >> ");
                int n = scanner.nextInt();
                // 입력받은 반지름이 -1이면 반복구문 벗어나기
                if (n == -1) {
                    System.out.println("Program is ended.");
                    break;

                }
                System.out.print("Enter a operator(# to area, @ to length) >> ");
                String op = scanner.next();
                float area, length;
                final float PI = (float) 3.14;
                // 문자 변수를 선언하여 연산자 입력받기
                // float 타입으로 상수 기호 PI 선언하기
                // float 타입으로 area, length 변수 선언하기
                // switch 구문으로 연산자 처리하기
                area = PI * n * n;
                length = PI * 2 * n;
                switch (op) {
                    case "#":  // 면적 계산하기
                        System.out.println("Circle: radius = " + n + " Area size = " + area);
                        break;

                    case "@":  // 둘레 계산하기
                        System.out.println("Circle: radius = " + n + " Length = " + length);
                        break;
                    default:
                        System.out.println("Wrong operator!!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Do not enter float");
                scanner.nextLine();
            }
        } while (true);

        scanner.close();
    }
}