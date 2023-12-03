package programmers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

class Customer {
    private String name, email, telno;
    private int age;
    private double point;

    // 매개변수를 가지는 생성자 정의 : 멤버변수 모두를 초기화하는 생성자임
    public Customer(String name, String email, String telno, int age, double point){

    }

    @Override
    public String toString() {
        // StringBuffer 클래스의 append 메소드를 이용하여 아래 실행결과에 보이는 것과 같이 출력이 나올 수 있도록 문자열 생성


        return null;
    }

    @Override
    public boolean equals(Object obj) {
        // 전화번호와 나이를 비교하여 두 값이 일치하면 true 아니면 false를 반환한다.


        return false;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        String line;
        final int COUNT = 3;
        Customer[] list = new Customer[COUNT];

        for (int i = 0; i < COUNT; i++) {

            // 고객이름#이메일#전화번호#나이#포인트 형식으로 문자열을 line 변수에 입력받기
            // #을 기준으로 데이터를 분리하고 각 데이터들로 Customer 객체 생성하고 배열에 저장하기


        }
        System.out.println("Print customers >>>>> ");

        for (Customer c : list) System.out.println(c);

        // 배열에 저장된 첫번째 고객과 두번째 고객을 비교하여 같은지 다른지 결과애 따라 출력하기

        if (list[0].equals(list[1])) System.out.println("Customer 1 and 2 are same.");
        else System.out.println("Customer 1 and 2 are different");
    }
}