package LiveCodingTest_1;

public class BankAccount {
    int accountNumber; // 계좌번호
    String owner; // 이름
    int balance; // 잔액

    BankAccount(int accountNumber, String owner) {
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = 0;
    }

    void deposit(int amount) {
        balance += amount;
    } // 입금 메소드

    void withdraw(int amount) {
        if (balance - amount < 0)
            return;
        else
            balance -= amount;

    } // 출금 메소드

    public void printAccount() {
        System.out.println(owner + "'s Balance : " + balance);
    } // 계좌정보 출력을 위한 메소드
}
