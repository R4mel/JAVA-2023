package programmers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public static String sol(String my_string, String overwrite_string, int s) {
        String first = my_string.substring(0, s);
        String second = my_string.substring(s+ overwrite_string.length());
        return first + overwrite_string + second;
    }

    public static void main(String[] args) {
//        String result = sol("Program29b8UYP", "merS123", 7);
//        System.out.println(result);
//        String result2 = sol("He11oWor1d", "lloWorl", 2);
//        System.out.println(result2);
        System.out.println(sol("aaaaaa", "bbb", 3));
    }
}

// "aaaaaa", "bbb", 3

// "aaabbb"