package DP;

public class fibo {
    public static long fibBottomUp(int n) {
        long[] bottomUp = new long[n + 1];
        bottomUp[1] = 1;
        bottomUp[2] = 1;
        for (int i = 3; i <= n; i++) {
            bottomUp[i] = bottomUp[i - 1] + bottomUp[i - 2];
        }
        return bottomUp[n];
    }
    // Dynamic Programming, Bottom-up

    // test

}
