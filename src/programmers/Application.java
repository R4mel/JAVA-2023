package programmers;

import java.util.Arrays;

class Solution {
    public int[] solution(int[] arr, int[][] queries) {
        for(int [] q1: queries){
            int tmp1 = arr[q1[0]];
            int tmp2 = arr[q1[1]];
            arr[q1[0]] = tmp2;
            arr[q1[1]] = tmp1;
        }
        return arr;
    }
}

class Application {
    public static void main(String[] args) {
        Solution test = new Solution();
        int [] arr = {0, 1, 2, 3, 4};
        int [][] queries = {{0, 3},{1, 2},{1, 4}};
        System.out.println(Arrays.toString(test.solution(arr, queries)));
    }
}