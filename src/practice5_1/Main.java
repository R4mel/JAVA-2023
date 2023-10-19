package practice5_1;

import java.util.*;

class Point {
    private int x, y; // 점의 좌표

    public Point(int x, int y) {
        this.x = x;    this.y = y;
    }
    public void display() {  // 좌표 출력
        System.out.print("("+x+","+y+")");
    }
}

// 화면에 그래픽을 그려 주기 위한 인터페이스이다.
interface Graphics {

    // 다음과 같이 선의 양 끝 점 p1과 p2의 좌표를 출력한다.
    // 예) LinuxGUI: L(10,11)(20,22)
    // "LinuxGUI"는 이 함수를 구현한 시스템 이름이며, 구현 시스템에 따라 다르다.

    void drawLine(Point p1, Point p2);
}

// Window 시스템을 위한 인터페이스이다.
interface Window {

    // 다음과 같이 생성할 윈도우 창의 대각선 양 끝 점 p1과 p2의 좌표를 출력한다.
    // 예) LinuxGUI: W(10,11)(20,22)
    // "LinuxGUI"는 이 함수를 구현한 시스템 이름이며, 구현 시스템에 따라 다르다.

    void createWindow(Point p1, Point p2); // 윈도우 창을 생성해 주는 함수이다.
}

// Window 창과 그래픽을 지원하는 시스템을 위한 GUI 인터페이스이다.
// 문제: 이 인터페이스는 기존의 Graphics와 Window 인터페이스를 상속해야 한다.
interface GUI extends Graphics, Window{ // 두 인터페이스 상속할 것

    // GUI에 새로 추가된 함수임
    // 다음과 같이 마우스 이벤트가 발생한 좌표를 출력을 한다.
    // 예) LinuxGUI: E(10,11)
    // "LinuxGUI"는 이 함수를 구현한 시스템 이름이며, 구현 시스템에 따라 다르다.

    void inputMounseEvent(Point p); // 마우스 이벤트를 받아들이는 함수이다.
}

class LinuxGUI implements GUI{
    @Override
    public void drawLine(Point p1, Point p2) {
        System.out.print("LinuxGUI: L");
        p1.display();
        p2.display();
        System.out.println();
    }

    @Override
    public void createWindow(Point p1, Point p2) {
        System.out.print("LinuxGUI: W");
        p1.display();
        p2.display();
        System.out.println();
    }

    @Override
    public void inputMounseEvent(Point p) {
        System.out.print("LinuxGUI: E");
        p.display();
        System.out.println();
    }
}

class MSWindowsGUI implements GUI{
    @Override
    public void drawLine(Point p1, Point p2) {
        System.out.print("MSWindowsGUI: L");
        p1.display();
        p2.display();
        System.out.println();
    }

    @Override
    public void createWindow(Point p1, Point p2) {
        System.out.print("MSWindowsGUI: W");
        p1.display();
        p2.display();
        System.out.println();
    }

    @Override
    public void inputMounseEvent(Point p) {
        System.out.print("MSWindowsGUI: E");
        p.display();
        System.out.println();
    }
}

public class Main {

    static Point p1, p2;

    // 한 점의 좌표를 입력 받은 후 Point 객체를 생성한 후 리턴
    static Point getPoint(Scanner in) {
        System.out.print("Point x and y? ");
        int x = in.nextInt();
        int y = in.nextInt();
        return new Point(x, y);
    }

    static void runGraphis(Graphics g) {
        g.drawLine(p1, p2);
    }

    static void runWindow(Window w) {
        w.createWindow(p1, p2);
    }

    static void GUIsystem(GUI gui) {
        runGraphis(gui);
        runWindow(gui);
        gui.inputMounseEvent(p1);
    }

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        // 두 점의 좌표를 미리 생성해 둔다.
        p1 = getPoint(in);
        p2 = getPoint(in);
        System.out.println();

        LinuxGUI linux = new LinuxGUI();
        GUIsystem(linux);

        MSWindowsGUI ms = new MSWindowsGUI();
        GUIsystem(ms);

        in.close();
    }
}