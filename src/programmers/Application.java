package programmers;

import java.util.*;

class Solution {
    public int[] solution(int[] arr, int[][] queries) {

        return arr;
    }

    public void dispPersonInfo(String sname) {
        char first = sname.charAt(0);
        char last = sname.charAt(sname.length() - 1);
        String add = sname.substring(1, sname.length() - 1);
        sname = last + add + first;
        System.out.println(sname);
    }
}

class Application {
    public static void main(String[] args) {
        Solution test = new Solution();
        String a = "Gwangju ,Nam-gu , Bongseon-dong 21";
        a.toLowerCase().replaceAll("-gu", "_gu");
        String[] b = a.split(",");
        for(int i=0; i<b.length; i++){
            System.out.println(b[i].trim());
        }

    }
}