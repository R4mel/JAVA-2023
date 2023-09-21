package CH4_2;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //--------------------------------
        // AutoCheck(chk, trace)
        // chk: 1(자동 오류 체크), 0(키보드에서 직접 입력하여 프로그램 실행)
        // trace: true(오류발생한 곳 출력), false(단순히 O, X만 표시)
        //--------------------------------
        int chk = 1;
//        if (chk != 0) new AutoCheck(chk, true).run(); else

        run(new Scanner(System.in));
    }

    public static void run(Scanner scan) {
        UI.setScanner(scan);
        MainMenu.run();
        scan.close();
    }
}

class MainMenu {
    final static int MENU_COUNT = 5;

    public static void run() {
        String menuStr =
                "******* Main Menu ********\n" +
                        "* 0.exit 1.PersonManager *\n" +
                        "* 2.ch2 3.ch3            *\n" +
                        "**************************\n";

        while (true) {
            int menuItem = UI.selectMenu(menuStr, MENU_COUNT);
            switch (menuItem) {
                case 0:
                    System.out.println("\nGood bye!!");
                    return;
                case 1:
                    new MultiManager().run();
                    break;
                case 2:
                    Ch2.run();
                    break;
                case 3:
                    Ch3.run();
                    break;
            }
        }
    }
}

class Person {
    private String name;    // 이름
    private int id;      // Identifier
    private double weight;  // 체중
    private boolean married; // 결혼여부
    private String address; // 주소

    // 생성자 함수들
    public Person(String name, int id, double weight, boolean married, String address) {
        set(name, id, weight, married, address);
        println("Person(): ");
    }

    public Person(String name) {
        this(name, 0, 0.0, false, "");
    }

    public void set(String name, int id, double weight, boolean married, String address) {
        this.name = name;
        this.id = id;
        this.weight = weight;
        this.married = married;
        this.address = address;
    }

    public void println() {
        print();
        System.out.println();
    }

    public void println(String msg) {
        System.out.print(msg);
        print();
        System.out.println();
    }

    // assign() 함수
    public void assign(Person user) {
        set(user.getName(), user.getId(), user.getWeight(), user.getMarried(), user.getAddress());
    }

    // Getter: getXXX() 관련 함수들
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public boolean getMarried() {
        return married;
    }

    public String getAddress() {
        return address;
    }

    // Setter: overloading: set() 함수 중복
    public void set(String name) {
        this.name = name;
    }

    public void set(int id) {
        this.id = id;
    }

    public void set(double weight) {
        this.weight = weight;
    }

    public void set(boolean married) {
        this.married = married;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Candidates for virtual functions and overriding
    // print(), clone(), whatAreYouDoing(), equals(), input() 함수
    public void print() {
        System.out.print(name + " " + id + " " + weight + " " + married + " :" + address + ":");
    }

    public Person clone() {
        System.out.println("Person::clone()");
        return new Person(name, id, weight, married, address);
    }

    public void whatAreYouDoing() {
        System.out.println(name + " is taking a rest.");
    }

    public boolean equals(Person user) {
        return (user.getName() == getName() && user.getId() == getId());
    }

    public void input(Scanner s) {
        name = s.next();
        id = s.nextInt();
        weight = s.nextDouble();
        married = s.nextBoolean();
        while ((address = s.findInLine(":.*:")) == null)
            s.nextLine();
        address = address.substring(1, address.length() - 1);
        set(name, id, weight, married, address);
    }
}

class UI {
    public static boolean echo_input = false; // 자동오류체크 시 true로 설정됨
    public static Scanner scan;

    public static void setScanner(Scanner s) {
        scan = s;
    }

    public static int selectMenu(String menuStr, int menuCount) {
        return getIndex("\n" + menuStr + "menu item? ", menuCount);
    }

    public static int getInt(String msg) {
        int value;
        while (true) {
            System.out.print(msg);
            try {
                value = scan.nextInt();
                break;
            } catch (InputMismatchException e) {
                UI.scan.nextLine();
                System.out.println("Input an INTEGER. Try again!!");
            }
        }

        if (echo_input) System.out.println(value);
        scan.nextLine();
        return value;
    }

    public static int getIndex(String msg, int size) {
        int value;
        for (; ; ) {
            value = getPosInt(msg);
            if (value > size - 1) {
                System.out.println(value + ": OUT of selection range(0 ~ " + (size - 1) + ") Try again!!");
                continue;
            }
            break;
        }
        return value;
    }

    public static int getPosInt(String msg) {
        int value;
        for (; ; ) {
            value = getInt(msg);
            if (value < 0) {
                System.out.println("Input a positive INTEGER. Try again!!");
                continue;
            }
            break;
        }
        return value;
    }

    public static String getNext(String msg) {
        System.out.print(msg);
        String token = scan.next();
        if (echo_input) System.out.println(token);
        scan.nextLine();
        return token;
    }

    public static String getLine(String msg) {
        System.out.print(msg);
        String line = scan.nextLine();
        if (echo_input) System.out.println(line);
        return line;
    }
}

class CurrentUser {
    Person user;

    CurrentUser(Person user) {
        this.user = user;
    }

    public void run() {
        String menuStr =
                "++++++++++++++++++++++ Current User Menu ++++++++++++++++++++++++\n" +
                        "+ 0.logout 1.display 2.getter 3.setter 4.copy 5.whatAreYouDoing +\n" +
                        "+ 6.equals 7.update                                             +\n" +
                        "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
        final int MENU_COUNT = 8;
        while (true) {
            int menuItem = UI.selectMenu(menuStr, MENU_COUNT);
            switch (menuItem) {
                case 1:
                    display();
                    break;
                case 2:
                    getter();
                    break;
                case 3:
                    setter();
                    break;
                case 4:
                    copy();
                    break;
                case 5:
                    whatAreYouDoing();
                    break;
                case 6:
                    equals();
                    break;
                case 7:
                    update();
                    break;
                case 0:
                    return;
            }
        }
    }

    void display() {
        user.println();
    } // Menu item 1

    void getter() { // Menu item 2
        System.out.println("name:" + user.getName() + ", id:" + user.getId() + ", weight:" +
                user.getWeight() + ", married:" + user.getMarried() +
                ", address:" + user.getAddress());
    }

    void setter() { // Menu item 3
        var p = new Person("p");
        p.set(p.getName());
        p.set(user.getId());
        p.set(user.getWeight());
        p.set(user.getMarried());
        p.setAddress(user.getAddress());
        p.println("p.set(): ");
    }

    void copy() { // Menu item 4
        user.println("user: ");
        var p = user.clone();
        p.println("p: ");
    }

    void whatAreYouDoing() {  // Menu item 5
        user.whatAreYouDoing();
    }

    void equals() { // Menu item 6
        user.println("user: ");
        var p = new Person("user");
        p.set(1);
        p.println("p: ");
        System.out.println("p.equals(user): " + p.equals(user));
        p.assign(user);
        p.println("p.assign(user): ");
        System.out.println("p.equals(user): " + p.equals(user));
    }

    void update() { // Menu item 7
        System.out.println("input person information:");
        user.input(UI.scan); // user 100 65 true :426 hakdong-ro, Gangnam-gu, Seoul:
        if (UI.echo_input) user.println(); // 자동오류체크시 출력됨
        display();
    }
}

class MultiManager {
    private Person person = new Person("p0", 0, 70.0, false, "Gwangju Nam-gu 21");

    void run() {
        new CurrentUser(person).run();
    }
}

class Ch3 {
    public static void run() {
        String menuStr =
                "************* Ch3 Menu **************\n" +
                        "* 0.Exit 1.array 2.exception 3.game *\n" +
                        "*************************************\n";

        final int MENU_COUNT = 4;
        while (true) {
            int menuItem = UI.selectMenu(menuStr, MENU_COUNT);
            switch (menuItem) {
                case 0:
                    return;
                case 1:
                    array();
                    break;
                case 2:
                    exception();
                    break;
                case 3:
                    game();
                    break;
            }
        }
    }

    public static void array() {
        double[][] arr1 = {{0}, {1, 2}, {3, 4, 5}};
        printArray(arr1);
        double[][] arr2 = {{0, 1, 2, 3}, {4, 5, 6}, {7, 8}, {9}};
        printArray(arr2);
        var arr3 = inputArray();
        printArray(arr3);
        arr3 = inputArray();
        printArray(arr3);
    }

    public static void printArray(double[][] arr) {
        System.out.println("arr length: " + arr.length);
        for (int i = 0; i < arr.length; i++) {
            System.out.print("arr[" + i + "] ");
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static double[][] inputArray() {
        int value = UI.getPosInt("array rows? ");
        double[][] arr = new double[value][];
        for (int i = 0; i < value; i++) {
            arr[i] = new double[i + 1];
        }

        for (int i = 0; i < value; i++) {
            System.out.print("input " + (i + 1) + " doubles for row " + i + ": ");
            for (int j = 0; j < arr[i].length; j++) {
                arr[i][j] = UI.scan.nextDouble();
            }
        }
        return arr;
    }

    static Random random = null; // 난수 발생기

    public static void exception() {
        var random = new Random(UI.getInt("seed for random number? "));// 난수 발생기
        int i = 0;
        int arr[];
        while (true) {
            try {
                String str = UI.getNext("array[] size? ");
                i = Integer.parseInt(str);   // 문자열 숫자를 정수로 변환: "123" -> 123
                arr = new int[i];
                break;
            } catch (NumberFormatException e) {
                System.out.println(e);
            } catch (NegativeArraySizeException e) {
                System.out.println(e);
            }
        }

        for (i = 0; i < arr.length; ++i) // arr[] 전체를 난수 값으로 초기화
            arr[i] = random.nextInt(3);  // [0,1,2] 범위의 난수 발생
        System.out.print("array[]: { ");
        for (var v : arr)                // 배열 전체 출력
            System.out.print(v + " ");     // 각각의 v=arr[i] 원소 값을 출력함
        System.out.println("}");

        while (true) {
            try {
                i = UI.getPosInt("array[] index? ");
                System.out.println("array[" + i + "] = " + arr[i]);
                break;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(e);
            }
        }
        int numerator = UI.getIndex("numerator   index? ", arr.length); // 분자 index
        while (true) {
            try {
                int denominator = UI.getIndex("denominator index? ", arr.length); // 분모 index


                System.out.println(arr[numerator] + " / " + arr[denominator] + " = "
                        + (arr[numerator] / arr[denominator]));
                break;
            } catch (ArithmeticException e) {
                System.out.println(e);
            }
        }
        System.out.println("makeArray(): first");
        while (true) {
            try {
                arr = makeArray();
                break;
            } catch (OutOfMemoryError e) {
                System.out.println(e);
            }
        }

        System.out.println("makeArray(): second");

        while (true) {
            try {
                arr = makeArray();
                System.out.println("array length = " + arr.length);
                break;
            } catch (NullPointerException e) {
                System.out.println("NullPointerException");
            }
        }
    }

    // tag 0: OutOfMemoryError, 1: return null pointer, 2: return normal array
    public static int[] makeArray() {
        int tag = UI.getPosInt("makeArray tag[0,1,2]? ");
        return (tag == 0) ? new int[0x7fffffff] : (tag == 1) ? null : new int[10];
    }

    public static void game() {
        final int USER = 0;     // 상수 정의
        final int COMPUTER = 1;
        String MJBarray[] = {"m", "j", "b"}; // 묵(m) 찌(j) 빠(b) 문자열을 가진 배열
        System.out.println("Start the MUK-JJI-BBA game.");
        // 난수 발생기
        random = new Random(UI.getInt("seed for random number? "));
        // 누가 우선권을 가졌는지 저장하고 있음, USER:사용자 우선권, COMPUTER:computer 우선권
        int priority = USER;
        String priStr[] = {"USER", "COMPUTER"}; // 우선권을 화면에 출력할 때 사용할 문자열
        String user;
        while (true) {
            while (true) {
                System.out.println();
                System.out.println(priStr[priority] + " has the higher priority.");
                user = UI.getNext("m(muk), j(jji), b(bba) or stop? ");
                if (user.equals("stop")) return;
                if (user.equals("m") || user.equals("j") || user.equals("b")) break;
                else System.out.println("Select one among m, j, b.");
            }

            String computer = MJBarray[random.nextInt(MJBarray.length)];
            System.out.print("User = " + user + ", Computer = " + computer + ", ");
            if (user.equals(computer)) System.out.println(priStr[priority] + " WINs.");
            else {
                System.out.println(" SAME.");
                if (user.equals("m") && computer.equals("b")) priority = COMPUTER;
                else if (user.equals("j") && computer.equals("m")) priority = COMPUTER;
                else if (user.equals("b") && computer.equals("j")) priority = COMPUTER;
                else if (user.equals("m") && computer.equals("j")) priority = USER;
                else if (user.equals("b") && computer.equals("j")) priority = USER;
                else if (user.equals("j") && computer.equals("b")) priority = USER;
            }
        }
    }

}

class Ch2 {
    public static void run() {
        final int MENU_COUNT = 6;
        String menuStr =
                "************* Ch2 Menu ***********\n" +
                        "* 0.exit 1.output 2.readToken    *\n" +
                        "* 3.readLine 4.operator 5.switch *\n" +
                        "**********************************\n";

        while (true) {
            int menuItem = UI.selectMenu(menuStr, MENU_COUNT);
            switch (menuItem) {
                case 0:
                    return;
                case 1:
                    output();
                    break;
                case 2:
                    readToken();
                    break;
                case 3:
                    readLine();
                    break;
                case 4:
                    operator();
                    break;
                case 5:
                    switchCase();
                    break;
            }
        }
    }

    public static void output() {
        String toolName = "JDK";
        double version = 1.8;
        String released = "is released.";
        System.out.println(toolName + version + released);
        System.out.println(toolName + " " + version + " " + released);

        int i1 = 1, i2 = 2, i3 = 3; // 변수 선언과 함께 초기화

        System.out.println(i1 + i2 + i3);
        System.out.println("" + i1 + i2 + i3);
        System.out.println(i1 + i2 + i3 + " " + i1 + i2 + i3);

        boolean b = true;
        double d = 1.2;

        System.out.println("" + b + " " + !b + " " + d);
    }

    public static void readToken() {
        String name;    // 이름
        int id;      // Identifier
        double weight;  // 체중
        boolean married; // 결혼여부
        String address; // 주소

        System.out.println("person information(name id weight married :address:):");
        name = UI.scan.next();
        id = UI.scan.nextInt();
        weight = UI.scan.nextDouble();
        married = UI.scan.nextBoolean();

        while ((address = UI.scan.findInLine(":.*:")) == null)
            UI.scan.nextLine();  // 현재 행에 주소가 없다면 그 행을 스킵함
        address = address.substring(1, address.length() - 1); // 주소 양 끝의 ':' 문자 삭제
        System.out.print(name + " " + id + " " + weight + " " + married + " :" + address + ":");
    }

    public static void readLine() {
        String name = UI.getNext("name? "); // "name? "을 출력한 후 이름을 입력 받음
        System.out.println("name: " + name);
        int id = UI.getInt("id? ");         // "id? "을 출력한 후 id을 입력 받음
        System.out.println("id: " + id);
        String address = UI.getLine("address? ");// "address? " 출력 후 한줄 전체 입력받음
        System.out.println("address :" + address + ":");
    }


    private static void printBinStr(int v) {
        String s = Integer.toBinaryString(v);
        for (int i = 0; i < (32 - s.length()); ++i)
            System.out.print('0');
        System.out.println(s);
    }

    public static void operator() {
        int b = 0b11111111_00000000_11111111_11111111;
        printBinStr(b);

        int left_b = b << 4;
        printBinStr(left_b);

        System.out.println();
        b = 0b11111111_00000000_00000000_11111111;
        printBinStr(b);

        int right_b_1 = b >> 4;
        printBinStr(right_b_1);
        int right_b_2 = b >>> 4;
        printBinStr(right_b_2);
    }


    public static void switchCase() {
        String menuStr =
                "********* Switch Menu *********\n" +
                        "* 0.exit 1.output 2.readToken *\n" +
                        "* 3.readLine 4.operator       *\n" +
                        "*******************************\n";

        while (true) {
            String menu = UI.getNext("\n" + menuStr + "menu item string? ");
            switch (menu) {
                case "exit":
                    return;
                case "output":
                    output();
                    break;
                case "readToken":
                    readToken();
                    break;
                case "readLine":
                    readLine();
                    break;
                case "operator":
                    operator();
                    break;
            }
        }
    }
}