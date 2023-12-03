package LiveCodingTest_1;

import java.util.*;

class Student {
    public int hakbun;
    public int kor;
    public int eng;
    public int math;
    public int sum;
    public int avg;
    public String name = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHakbun() {
        return hakbun;
    }

    public void setHakbun(int hakbun) {
        this.hakbun = hakbun;
    }

    public int getKor() {
        return kor;
    }

    public void setKor(int kor) {
        this.kor = kor;
    }

    public int getEng() {
        return eng;
    }

    public void setEng(int eng) {
        this.eng = eng;
    }

    public int getMath() {
        return math;
    }

    public void setMath(int math) {
        this.math = math;
    }

    public int getSum() {
        sum = getEng() + getKor() + getMath();
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getAvg() {
        avg = sum / 3;
        return avg;
    }

    public void setAvg(int avg) {
        this.avg = avg;
    }

    public Student(int hakbun, String name, int kor, int math, int eng) {
        setHakbun(hakbun);
        setName(name);
        setEng(eng);
        setKor(kor);
        setMath(math);
    }

    public void print() {
        // "[학번] 이름 : 국어 / 영어 / 수학 (총점 / 평균)"
        System.out.println("[" + getHakbun() + "] " + getName() + " : " + getKor() + " / " + getMath() + " / " + getEng() + " (" + getSum() + " / " + getAvg() + ")");
    }
}

public class CH4_1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("The number of students : ");
        int a = in.nextInt();
        Student[] students = new Student[a];
        int b;
        String c;
        int d;
        int e;
        int f;
        for (int i = 0; i < a; i++) {
            b = in.nextInt();
            c = in.next();
            d = in.nextInt();
            e = in.nextInt();
            f = in.nextInt();
            students[i] = new Student(b, c, d, e, f);
        }

        System.out.println("[Student Information]");
        for (int i = 0; i < a; i++) {
            students[i].print();
        }
        int maxIndex = 0;
        int minIndex = 0;
        for (int i = 1; i < a; i++) {
            if (students[maxIndex].getSum() < students[i].getSum()) maxIndex = i;
            else if (students[minIndex].getSum() > students[i].getSum()) minIndex = i;
        }

        System.out.print("The first place : ");
        students[maxIndex].print();
        System.out.print("The last place : ");
        students[minIndex].print();
    }
}