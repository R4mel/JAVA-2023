package programmers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Answer {
    public int sol(int a, int b, boolean flag) {
        if(flag){
            return a + b;
        }
        return a - b;
    }
}


class Solution {
    public static void main(String[] args) {
        Answer answer = new Answer();
    }
}