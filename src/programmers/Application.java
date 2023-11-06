package programmers;

import java.util.*;

class Solution {
    public String solution(String my_string, int[][] queries) {
        char[] arr = my_string.toCharArray();
        for (int i = 0; i < queries.length; i++) {
            int start = queries[i][0];
            int end = queries[i][1];
            for (int j = start; j <= (start + end) / 2; j++) {
                char temp = arr[j];
                arr[j] = arr[start + end - j];
                arr[start + end - j] = temp;
            }
        }
        return new String(arr);
    }
}

class Application {
    public static void main(String[] args) {
        Solution test = new Solution();
        int[][] a = {{2, 3}, {0, 7}, {5, 9}, {6, 10}};
        System.out.println(test.solution("rermgorpsam", a));
    }
}