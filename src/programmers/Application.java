package programmers;

import java.util.*;

class Solution {
    private static final String[] flagAndEsc = {"7E", "1B"};
    private static final String b = "1B";

    public void byteStuffing(ArrayList<String> s) {
        int flagAndEscCounter = 0;
        int size = s.size();
        int[] count = new int[size];

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

        // Rx 출력
        System.out.print("Rx ");
        for (String value : s) {
            System.out.print(value + " ");
        }
        System.out.println();

        // Data 출력
        System.out.print("=> D ");
        for (String value : s) {
            System.out.print(value + " ");
        }
        System.out.println();
        System.out.println();
    }
}

class Application {
    public static void main(String[] args) {
        var data1 = new ArrayList<>(Arrays.asList("55", "7E", "AA", "F0", "1B", "0A"));
        var data2 = new ArrayList<>(Arrays.asList("7E", "7C", "BA", "78", "1B", "1B", "B6", "7E"));
        var data3 = new ArrayList<>(Arrays.asList("55", "1B", "7E", "AA", "F0", "1B", "1B", "1B", "0A"));
        var data4 = new ArrayList<>(Arrays.asList("1B", "1B", "1B", "1B", "1B", "1B", "1B", "1B", "1B"));
        Solution test = new Solution();
        test.byteStuffing(data1);
        test.byteStuffing(data2);
        test.byteStuffing(data4);

        test.byteUnStuffing(data3);
        test.byteUnStuffing(data2);
        test.byteUnStuffing(data4);
    }
}