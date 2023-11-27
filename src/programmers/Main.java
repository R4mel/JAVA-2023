package programmers;

import java.util.*;

class Solution {
    public int solution(String my_string, String is_suffix) {
        ArrayList<String> a = new ArrayList<>();
        for (int i = 0; i < my_string.length(); i++) {
            a.add(my_string.substring(i, my_string.length()));
        }
        if (a.contains(is_suffix)) return 1;
        return 0;
    }
}

public class Main {
    public static void main(String[] args) {
        var sol = new Solution();
    }
}