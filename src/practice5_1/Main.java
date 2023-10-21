package practice5_1;

import java.util.*;

class Point {
    private int x, y; // 점의 좌표

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void display() {  // 좌표 출력
        System.out.print("(" + x + "," + y + ")");
    }

    public void move(int width, int height) { // 좌표 이동
        x += width;
        y += height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

// Shape 추상 클래스 선언
abstract class Shape {
    private int thick;       // 선의 두께
    private String color;    // 선의 색깔
    public Shape next;       // Lined list에서 다음 shape의 참조자

    public Shape(int thick, String color) {
        next = null;
        this.thick = thick;
        this.color = color;
    }

    public void display() {  // 그래픽 정보 출력
        System.out.print("(" + thick + "," + color + ")");
    }

    public abstract void move(int width, int height); // 좌표 이동
}

class Line extends Shape {
    private Point p1, p2; // 두 점의 좌표

    public Line(int thick, String color, Point p1, Point p2) {
        super(thick, color);
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public void display() {
        System.out.print("L");
        super.display();
        System.out.print("(" + p1.getX() + "," + p1.getY() + ")");
        System.out.println("(" + p2.getX() + "," + p2.getY() + ")");
    }

    @Override
    public void move(int width, int height) {
        p1.move(width, height);
        p2.move(width, height);
    }
}

class Circle extends Shape {
    private Point center;    // 중심점
    private int radius;      // 반지름

    public Circle(int thick, String color, Point center, int radius) {
        super(thick, color);
        this.center = center;
        this.radius = radius;
    }

    @Override
    public void display() {
        System.out.print("C");
        super.display();
        System.out.println("(" + center.getX() + "," + center.getY() + "):" + radius);
    }

    @Override
    public void move(int width, int height) {
        center.move(width, height);
    }
}

// add() 함수를 통해 추가된 그래픽 객체들을 linked list로 관리한다.
class Manager {
    Shape first, last; // Shape의 linked list의 시작과 끝을 참조함

    public Manager() {
        first = last = null;
    }

    public void add(Shape s) {
        if (first == null) {
            first = s;
            last = first;
        } else {
            last.next = s;
            last = s;
        }
    }

    public void display() { // linked 리스트의 모든 그래픽 객체를 출력한다.
        for (Shape s = first; s != null; s = s.next)
            s.display();
    }

    // 그래픽 객체의 이동할 width, height는 사용자로부터 직접 입력 받은 후
    // linked 리스트의 모든 그래픽 객체의 좌표를 width, height 만큼 이동한다.
    public void move(Scanner in) {
        System.out.print("width and height to move? ");
        int width = in.nextInt();
        int height = in.nextInt();
        for (Shape s = first; s != null; s = s.next)
            s.move(width, height);
        display();
    }
}

public class Main {
    // 그래픽 객체를 미리 생성하여 배열에 저장해 둠.
    static private Shape[] shapes = {
            new Line(0, "BLACK", new Point(10, 11), new Point(20, 21)),
            new Line(1, "WHITE", new Point(30, 31), new Point(40, 41)),
            new Circle(2, "RED", new Point(50, 51), 10),
            new Circle(3, "RED", new Point(50, 51), 10),
    };

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // Manager의 linked 리스트에 처음으로 삽입할 그래픽 객체 배열 인덱스를 입력 받음
        System.out.print("begin index(0~4) of shapes array? ");
        int beg = in.nextInt();

        Manager manager = new Manager();
        // 입력 받은 배열 인덱스(beg)부터 순서적으로 Manager에 추가함
        for (int count = 0; count < shapes.length; count++, beg = ++beg % 4)
            manager.add(shapes[beg]);

        manager.display();

        while (true) {
            System.out.print("\n0.exit 1.display 2.move >> ");
            int input = in.nextInt(); // 메뉴를 보여 주고 메뉴 항목을 입력 받음
            if (input == 0)
                break;
            switch (input) {
                case 1:
                    manager.display();
                    break;
                case 2:
                    manager.move(in);
                    break;
            }
        }
        in.close();
    }
}