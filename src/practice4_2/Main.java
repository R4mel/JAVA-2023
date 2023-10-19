package practice4_2;

import java.util.*;

class Student {

    private int count;

    public double getDiff() {
        return diff;
    }

    public void setDiff(double diff) {
        this.diff = diff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public int getTall() {
        return tall;
    }

    public void setTall(int tall) {
        this.tall = tall;
    }

    private int tall;
    private double diff;

    public void printStudent() {
        System.out.printf("%s\t%d\t%6.2f\n", name, tall, diff);
    }

    // 필요한 경우 멤버 값을 설정하고 구해오는 메소드(함수)를 만들어 멤버에 접근하라.
    Student(String name, int tall) {
        setName(name);
        setTall(tall);
    }

    Student(int count) {
        this.count = count;
    }
}

class Manager extends Student {

    public Student[] student;
    public double mean = 0;
    public int count = 0;
    static private int[] tall = {172, 183, 168, 161, 171, 172};
    static private String[] name = {"bob", "john", "alice", "nana", "tom", "sandy"};

    private Manager(int size) {
        super(size);
        student = new Student[size];
    }

    public static Manager makeManager(Scanner in) {

        System.out.print("input continuously 6 indices(index) of array: ");

        Manager manager = new Manager(6);

        // 0 ~ 5 사이의 서로 다른 6개의 숫자(임의의 순서)들을 연속적으로 입력 받으면서
        // 학생 객체를 생성한 후 manager에 등록한다.
        for (int i = 0; i < name.length; i++) {
            int j = in.nextInt();
            // manager 객체의 append 메소드를 실행하여 학생 객체 등록
            // append()는 manager내의 학생 객체 배열에 순서적으로 삽입한다.
            manager.append(new Student(name[j], tall[j]));
        }

        return manager;
    }

    public void displayAll() {
        System.out.print("name\ttall\tdifference\n");
        for (Student value : student) {
            value.printStudent();
        }
    }

    public void append(Student s) {
        student[count++] = s;
    }

    public void calculateMean() {
        for (Student item : student) {
            mean += item.getTall();
        }
        mean /= count;
        System.out.printf("tall mean: %.2f\n\n", mean);
        for (Student value : student) {
            value.setDiff((double) value.getTall() - mean);
        }
        displayAll();
    }

    public void findStudent(String name) {
        for (Student value : student) {
            if (name.equals(value.getName())) {
                value.printStudent();
                return;
            }
        }
        System.out.println(name + ": not found.");
    }

    public void findStudent(String pname, int tall) {
        for (Student value : student) {
            if (pname.equals(value.getName()) && value.getTall() == tall) {
                value.printStudent();
                return;
            }
        }
        System.out.println(pname + " " + tall + ": not found.");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Manager manager = Manager.makeManager(in);
//         manager = new Manager(3);
        // 위 문장의 주석을 제거하면 Manager 생성자 접근할 수 없다는 에러가 발생하여야 한다.

        while (true) {
            System.out.print("\n0.Exit 1.DisplayAll 2.CalculateMean\n");
            System.out.print("3.FindStudent 4.FindStudent2 5.MakeManager >> ");
            int input = in.nextInt();
            if (input == 0)
                break;
            switch (input) {
                case 1:
                    manager.displayAll();
                    break;
                case 2:
                    manager.calculateMean();
                    break;
                case 3:
                    System.out.print("name? ");
                    String pname = in.next();
                    manager.findStudent(pname);
                    break;
                case 4:
                    System.out.print("name, tall? ");
                    pname = in.next();
                    int tall = in.nextInt();
                    manager.findStudent(pname, tall);
                    break;
                case 5:
                    manager = Manager.makeManager(in);
                    break;
            }
        }
        in.close();
    }
}
//