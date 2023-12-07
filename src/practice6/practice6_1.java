package practice6;

import java.util.*;

class Customer {
    private String name, email, telno;
    private int age;
    private double point;

    // 매개변수를 가지는 생성자 정의 : 멤버변수 모두를 초기화하는 생성자임
    public Customer(String name, String email, String telno, int age, double point) {
        this.name = name;
        this.email = email;
        this.telno = telno;
        this.age = age;
        this.point = point;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    @Override
    public String toString() {
        // StringBuffer 클래스의 append 메소드를 이용하여 아래 실행결과에 보이는 것과 같이 출력이 나올 수 있도록 문자열 생성
        var sb = new StringBuffer();
        sb.append("Name: " + getName() + "\n");
        sb.append("Email: " + getEmail() + "\n");
        sb.append("Telno: " + getTelno() + "\n");
        sb.append("Age: " + getAge() + "\n");
        sb.append("Point: " + getPoint() + "\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        Customer customer = (Customer) obj;
        return getAge() == customer.getAge() && getTelno().equals(customer.getTelno());
        // 전화번호와 나이를 비교하여 두 값이 일치하면 true 아니면 false를 반환한다.
    }
}

public class practice6_1 {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        String line;
        final int COUNT = 3;
        Customer[] list = new Customer[COUNT];

        for (int i = 0; i < COUNT; i++) {
            System.out.print("Customer: ");
            line = scn.nextLine();
            String[] a = line.split("#");
            list[i] = new Customer(a[0], a[1], a[2], Integer.parseInt(a[3]), Double.parseDouble(a[4]));
            // 고객이름#이메일#전화번호#나이#포인트 형식으로 문자열을 line 변수에 입력받기
            // #을 기준으로 데이터를 분리하고 각 데이터들로 Customer 객체 생성하고 배열에 저장하기

        }
        System.out.println("Print customers >>>>> ");

        for (Customer c : list)
            System.out.println(c);

        // 배열에 저장된 첫번째 고객과 두번째 고객을 비교하여 같은지 다른지 결과애 따라 출력하기

        if (list[0].equals(list[1]))
            System.out.println("Customer 1 and 2 are same.");
        else
            System.out.println("Customer 1 and 2 are different");
    }
}