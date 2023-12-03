package LiveCodingTest_1;

import java.util.*;

public class CH3 {
    public static void main(String[] args) {
        String com[] = {"Scissors", "Rock", "Paper"};
        Scanner scanner = new Scanner(System.in);
        System.out.println("Two people (A user, B user(compuer) play a S-R-P game");
        int i = 0;
        while (true) {
            try {
                System.out.print("Scissors, Rock, Paper >>  ");
                String a = scanner.next();
                if (a.equals("paper")) {
                    System.out.println("A user = " + a + " , B user(computer) = " + com[i] + ", " + "Try again.");
                } else if (a.equals("Paper")) {
                    System.out.println("A user = " + a + " , B user(computer) = " + com[i] + ", " + "B user(computer) win.");
                    i++;
                } else if (a.equals("Rock")) {
                    System.out.println("A user = " + a + " , B user(computer) = " + com[i] + ", " + "same.");
                    i++;
                } else if (a.equals("Scissors")) {
                    System.out.println("A user = " + a + " , B user(computer) = " + com[i] + ", " + "A user win.");
                    i++;
                } else if (a.equals("quit")) {
                    System.out.println("game end ...");
                    return;
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("OutOfBounds Exception!! Enter quit to end game");
            }
        }
    }
}