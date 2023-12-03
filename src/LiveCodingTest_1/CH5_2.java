package LiveCodingTest_1;

import java.util.*;

interface Car {
    // 현재 자동차의 로고를 리턴함
    String getLogo();

    // 차가 달리는데 필요한 적절한 연료량(배터리)을 감소하고, 현재 남아 있는 연료량(배터리 잔량)을 리턴함
    int consumeFuel();

    default void run() {
        // IONIC6(or KiaEV6) Car is running: 50%
        System.out.println(getLogo() + " Car is running: " + consumeFuel() + "%");
    }
}

abstract class Battery implements Car {
    private int capacity; // 배터리 충전량 %로 표현됨; 가득찬 경우 100%, 방전된 경우 0%

    Battery(int capacity) {
        this.capacity = capacity;
    }

    int getCapacity() {
        return capacity;
    }

    void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    // 배터리를 일정량 충전하고 현재 배터리 잔량을 보여 줌 "... 67% ..."
    abstract void charge();
}

// 문제: 위 계층구조 그림을 참조하여 적절한 클래스를 상속하거나 인터페이스를 구현하라.
abstract class EvCar extends Battery {

    // 문제: 생성자 및 필요한 멤버 함수들 구현하라.

    @Override
    public void run() {
        System.out.println(getLogo() + " Car is running: " + consumeFuel() + "%");
    }

    private String logo; // 자동차의 로고

    public String getLogo() {
        return logo;
    }

    EvCar(String logo, int capacity) {
        super(capacity);
        this.logo = logo;
    }

    public String setLogo(String logo) {
        return this.logo = logo;
    }
}

// 문제: 위 계층구조 그림을 참조하여 적절한 클래스를 상속하라.
class IONIC6 extends EvCar {

    // 문제: 생성자 및 필요한 멤버 함수들 구현하라.

    IONIC6(String logo, int capacity) {
        super(logo, capacity);
    }

    @Override
    public void run() {
        System.out.println(getLogo() + " Car is running: " + consumeFuel() + "%");
    }

    @Override
    public int consumeFuel() {
        setCapacity(getCapacity() - 10);
        if (getCapacity() < 0) setCapacity(0);
        return getCapacity();
    }

    @Override
    void charge() {
        setCapacity(getCapacity() + 10);
        if (getCapacity() > 100) setCapacity(100);
        System.out.println(getLogo() + ": charging battery: " + getCapacity() + "%");
    }
}

// 문제: 위 계층구조 그림을 참조하여 적절한 클래스를 상속하라.
class EV6 extends EvCar {

    // 문제: 생성자 및 필요한 멤버 함수들 구현하라.

    EV6(String logo, int capacity) {
        super(logo, capacity);
    }

    @Override
    public void run() {
        System.out.println(getLogo() + " Car is running: " + consumeFuel() + "%");
    }

    @Override
    public int consumeFuel() {
        setCapacity(getCapacity() - 5);
        if (getCapacity() < 0) setCapacity(0);
        return getCapacity();
    }

    @Override
    void charge() {
        setCapacity(getCapacity() + 5);
        if (getCapacity() > 100) setCapacity(100);
        System.out.println("Battery is being charged " + getCapacity() + "% : " + getLogo());
    }
}

// 주의: mainEvCar(EvCar ec)를 제외하고는 절대 수정하지 마라.
public class CH5_2 {
    
    static IONIC6 io6 = new IONIC6("IONIC6", 70);
    static EV6 ev6 = new EV6("KiaEV6", 15);

    static void evCarRun(Car c) {
        c.run();
    }

    static void evCarCharge(Battery b) {
        b.charge();
    }

    static void mainEvCar(EvCar ec) {
        evCarRun(ec);
        evCarCharge(ec);

        // 문제: 위 evCarRun(Car c)와 evCarCharge(Battery b)를 순서적으로 직접 호출하라.
        // 경고: ec.run(); ec.charge();로 호출하여 출력되게 하면 0점 처리함

    }

    static void display() {
        System.out.println(io6.getLogo() + ": " + io6.getCapacity() + "%");
        System.out.println(ev6.getLogo() + ": " + ev6.getCapacity() + "%");
    }

    public static void mainMenu(Scanner scan) {
        display();

        while (true) {
            System.out.print("\n0.exit  1.run  2.charge  3.inferface  4.logo >> ");
            int input = scan.nextInt();
            if (input == 0)
                break;
            switch (input) {
                case 1:
                    io6.run();
                    ev6.run();
                    break;
                case 2:
                    io6.charge();
                    ev6.charge();
                    break;
                case 3:
                    mainEvCar(io6);
                    System.out.println("--------");
                    mainEvCar(ev6);
                    break;
                case 4:
                    System.out.print("IONIC6 logo? ");
                    io6.setLogo(scan.next());
                    System.out.print("KiaEV6 logo? ");
                    ev6.setLogo(scan.next());
                    System.out.println("--------");
                    display();
                    break;
            }
        }
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        mainMenu(scan);
        scan.close();
    }
}