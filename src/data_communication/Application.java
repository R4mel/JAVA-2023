package data_communication;

import java.util.Arrays;
import java.util.ArrayList;

class Solution {
    private static final String[] flagAndEsc = {"7E", "1B"};
    private static final String b = "1B";

    public void byteStuffing(ArrayList<String> s) {
        int flagAndEscCounter = 0; // 플래그 카운터
        int size = s.size();
        int[] count = new int[size]; // 플래그 값

        // Data 출력
        System.out.print("D ");
        for (String value : s) {
            System.out.print(value + " ");
        }
        System.out.println();

        // 플래그 값 개수 확인
        for (int i = 0; i < size; i++) {
            if (s.get(i).equals(flagAndEsc[0]) || s.get(i).equals(flagAndEsc[1])) {
                count[i]++;
            }
        }

        // 플래그 앞에 "1B" 추가
        for (int i = 0; i < size; i++) {
            if (count[i] != 0) {
                s.add(i + flagAndEscCounter, b); // "1B"들어가면서 뒤로 한칸씩 밀려남.
                flagAndEscCounter++;
            }
        }

        // Tx 출력
        System.out.print("=> Tx ");
        for (String value : s) {
            System.out.print(value + " ");
        }
        System.out.println();
        System.out.println();
    }

    public void byteUnStuffing(ArrayList<String> s) {
        int size = s.size();
        int[] count = new int[size]; // 플래그 값 세는 배열
        var result = new ArrayList<String>(); // 플래그 값을 제거한 ArrayList

        // Rx 출력
        System.out.print("Rx ");
        for (String value : s) {
            System.out.print(value + " ");
        }
        System.out.println();

        for (int i = 0; i < size; i++) {
            if (s.get(i).equals(flagAndEsc[0])) {
                count[i]++;
            } else if (s.get(i).equals(flagAndEsc[1])) {
                count[i]++;
                i++;
            }
        }

        // 플래그 값 제거한 ArrayList 생성
        for (int i = 0; i < size; i++) {
            if (count[i] == 0) {
                result.add(s.get(i));
            }
        }

        // Data 출력
        System.out.print("=> D ");
        for (String value : result) {
            System.out.print(value + " ");
        }
        System.out.println();
        System.out.println();
    }
}

public class Application {
    public static void main(String[] args) {
        var data1 = new ArrayList<>(Arrays.asList("55", "7E", "AA", "F0", "1B", "0A"));
        var data2 = new ArrayList<>(Arrays.asList("7E", "7C", "BA", "78", "1B", "1B", "B6", "7E"));
        var data3 = new ArrayList<>(Arrays.asList("55", "1B", "7E", "AA", "F0", "1B", "1B", "1B", "0A"));
        var data4 = new ArrayList<>(Arrays.asList("1B", "1B", "1B", "1B", "1B", "1B", "1B", "1B", "1B"));
        var data5 = new ArrayList<>(Arrays.asList("7E", "7E", "7E", "7E", "7E", "7E", "7E", "7E", "7E"));

        Solution test = new Solution();
        test.byteStuffing(data1);
        test.byteStuffing(data2);
        test.byteStuffing(data3);
        test.byteStuffing(data4);
        test.byteStuffing(data5);
        // 위의 값을 그대로 byteUnStuffing 했기 때문에 처음 데이터 값과 동일하게 출력되어야 함.
        test.byteUnStuffing(data1);
        test.byteUnStuffing(data2);
        test.byteUnStuffing(data3);
        test.byteUnStuffing(data4);
        test.byteUnStuffing(data5);
    }
}