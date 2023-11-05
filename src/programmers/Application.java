package programmers;

import java.util.*;

class Solution {
    public int[] solution(int[] arr, int[][] queries) {

        return arr;
    }

    public String string(String expr) {
        // 2+3
        String first = expr.substring(0, 1).trim();
        String opr = expr.substring(1, 2).trim();
        String last = expr.substring(2).trim();
        System.out.println(first);
        System.out.println(opr);
        System.out.println(last);
        return first + " " + opr + " " + last;
    }

}

class Application {
    public static void main(String[] args) {
        Solution test = new Solution();
        System.out.println(test.string("2+3"));
    }
}