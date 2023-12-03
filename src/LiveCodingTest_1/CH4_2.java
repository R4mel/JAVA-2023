package LiveCodingTest_1;

import java.util.*;

public class CH4_2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        BankAccount user[] = {new BankAccount(1, "Kim"), new BankAccount(2, "Lee"), new BankAccount(3, "Park")}; // 3개의
        // 계좌를
        // 개설
        // (수정
        // 불가)
        while (true) {
            System.out.print("[Menu]\n1. Account Information / 2. Deposit / 3. Withdraw / 4. Transfer / 5. Exit\n>> "); // 메뉴
            // 출력
            int input = in.nextInt();

            switch (input) {
                case 1: // 계좌정보 출력
                    System.out.print("Input the account number (<Account No.>)\n>> ");
                    int a = in.nextInt();
                    if (a > 3) {
                        System.out.println("Unknown Account");
                        break;
                    }
                    user[a - 1].printAccount();
                    break;
                case 2: // 입금 기능
                    System.out.print("Input the account number and amount (<Account No.> <Amount>)\n>> ");
                    int man = in.nextInt();
                    int money = in.nextInt();
                    if (man > 3 || man <= 0) {
                        System.out.println("Unknown Account");
                        break;
                    }
                    if (money < 0) {
                        System.out.println("The amount should be a positive number.");
                        break;
                    }
                    user[man - 1].deposit(money);
                    user[man - 1].printAccount();
                    break;
                case 3: // 출금 기능
                    System.out.print("Input the account number and amount (<Account No.> <Amount>)\n>> ");
                    int man1 = in.nextInt();
                    int with = in.nextInt();
                    if (man1 > 3 || man1 <= 0) {
                        System.out.println("Unknown Account");
                        break;
                    }
                    if (with < 0) {
                        System.out.println("The amount should be a positive number.");
                        break;
                    }
                    if (user[man1 - 1].balance < with) {
                        System.out.println("The balance is insufficient.");
                        break;
                    }
                    user[man1 - 1].withdraw(with);
                    user[man1 - 1].printAccount();
                    break;
                case 4: // 송금 기능
                    System.out.print(
                            "Input the account number and amount (<Account No.(from)> <Amount> <Account No.(to)>)\n>> ");
                    int sender = in.nextInt();
                    int money1 = in.nextInt();
                    int receiver = in.nextInt();
                    if (money1 < 0) {
                        System.out.println("The amount should be a positive number.");
                        break;
                    }
                    if (sender > 3 || sender <= 0) {
                        System.out.println("Unknown Account");
                        break;
                    }
                    if (receiver > 3 || receiver <= 0) {
                        System.out.println("Unknown Account");
                        break;
                    }
                    if (user[sender - 1].balance < money1) {
                        System.out.println("The balance is insufficient.");
                        break;
                    }
                    user[sender - 1].withdraw(money1);
                    user[receiver - 1].deposit(money1);
                    user[sender - 1].printAccount();
                    user[receiver - 1].printAccount();
                    break;
                case 5: // 종료
                    System.out.print("Exit");
                    return;
            }
        }
    }
}