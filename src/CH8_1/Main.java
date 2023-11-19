package CH8_1;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        //--------------------------------
        // AutoCheck(chk, trace)
        // chk: 1(자동 오류 체크), 0(키보드에서 직접 입력하여 프로그램 실행)
        //--------------------------------
        // trace: true(오류발생한 곳 출력), false(단순히 O, X만 표시)
        // int chk = 1; if (chk != 0) new AutoCheck(chk, true).run(); else
        run(new Scanner(System.in));
    }

    public static void run(Scanner scan) {
        UI.setScanner(scan);
        MainMenu.run();
        scan.close();
    }
}

class MainMenu {
    final static int MENU_COUNT = 7;

    public static void run() {
        String menuStr =  // 7_3 수정
                "************* Main Menu **************\n" +
                        "* 0.exit 1.PersonManager 2.ch2 3.ch3 *\n" +
                        "* 4.ch5 5.PMbyMap 6.MyVectorTest     *\n" +
                        "**************************************\n";

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
                case 4:
                    new Inheritance().run();
                    break;
                case 5:
                    new PMbyMap().run();
                    break; // 7_2
                case 6:
                    new MyVectorTest().run();
                    break; // 7_3
            }
        }
    }
}

interface BaseStation {
    boolean connectTo(String caller, String callee);
    // 이 메소드는 Phone::sendCall(String caller)에서 호출되어야 한다.
    // callee라는 사람이 존재할 경우
    //     System.out.println("base station: sends a call signal of "+caller+
    //               " to "+callee)를 출력하고
    //     이 사람의 등록된 Phone의 receiveCall(caller, callee)을 호출하고 true를 리턴
    // 존재하지 않을 경우 "callee_name: NOT found"라는 에러 메시지 출력하고 false 리턴
}

interface Phone {
    void sendCall(String callee);

    // 이 메소드는 "made a call to 수신자_이름(callee)"라고 출력해야 하며 이 출력의 앞 또는 뒤에
    // 발신자 이름도 함께 출력하되 메이커가 알아서 적절히 회사명, 모델명 등과 함께 표시하면 된다.
    // 그런 후 baseStation.connectTo(caller, callee)를 호출해야 한다.
    void receiveCall(String caller);
    // 이 메소드는 "received a call from 송신자_이름(caller)"라고 출력해야 하며 이 출력의 앞 또는 뒤에
    // 수신자 이름도 함께 출력하되 메이커가 알아서 적절히 회사명, 모델명 등과 함께 표시하면 된다.
}

interface Calculator {
    // +, -, *, / 사칙연산만 지원하고 그 외의 연산자일 경우 "NOT supported operator" 에러 메시지 출력
    // 수식과 계산 결과 또는 에러 메시지를 출력해야 하며 이 출력의 앞 또는 뒤에
    // 계산기 소유주 이름도 함께 출력하되 메이커가 알아서 적절히 회사명, 모델명 등과 함께 표시하면 된다.
    void calculate(double oprd1, String op, double oprd2); // 예: (3, "+", 2.0)

    void calculate(String expr);                     // 예: ("3+2") ("2+ 3")
}

abstract class SmartPhone implements Phone, Calculator {
    protected static BaseStation baseStation;

    protected static Calendar userDate = null; // ch6_1

    public static void setBaseStation(BaseStation bs) {
        baseStation = bs;
    }

    public static void setDate(String line) { // ch6_1
        if (line.equals("")) {
            userDate = null;
            return;
        }

        Scanner s = new Scanner(line);
        userDate = Calendar.getInstance();
        userDate.clear();
        userDate.set(s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt(), s.nextInt());
        s.close();
    }

    protected String owner;  // 스마트폰 소유주 이름

    protected Calendar date; // ch6_1

    public SmartPhone(String owner) {
        this.owner = owner;
        date = (userDate == null) ? Calendar.getInstance() : (Calendar) userDate.clone();  // ch6_1
    }

    public abstract String getMaker();

    public void setOwner(String owner) {
        this.owner = owner;
    }

//    public void print() {
//        System.out.print(owner + "'s Phone: " + getMaker());
//    }

//    public void println() {
//        print();
//        System.out.println();
//    }

    @Override
    public String toString() {
        int ampm = date.get(Calendar.AM_PM);
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH);
        int day = date.get(Calendar.DAY_OF_MONTH);
        int hour = date.get(Calendar.HOUR);
        int minute = date.get(Calendar.MINUTE);
        int second = date.get(Calendar.SECOND);
        if (ampm == Calendar.AM) {
            return owner + "'s Phone: " + getMaker() + "(" + year + "." + month + "." + day + " AM " + hour + ":" + minute + ":" + second + ")";
        }
        return owner + "'s Phone: " + getMaker() + "(" + year + "." + month + "." + day + " PM " + hour + ":" + minute + ":" + second + ")";
        // (2023.10.18 PM 4:4:58)
    }

    public abstract SmartPhone clone();
}

class GalaxyPhone extends SmartPhone {
    private void printTradeMark(String appName) {
        System.out.println(" @ " + owner + "'s Galaxy " + appName);
    }

    public GalaxyPhone(String owner) {
        super(owner);
    }

    @Override
    public void sendCall(String callee) {
        System.out.print("made a call to " + callee);
        printTradeMark("Phone");
        baseStation.connectTo(owner, callee);
    }

    @Override
    public void receiveCall(String caller) {
        System.out.print("received a call from " + caller);
        printTradeMark("Phone");
    }

    @Override
    public void calculate(double oprd1, String op, double oprd2) {
        System.out.print(oprd1 + " " + op + " " + oprd2 + " = ");
        switch (op) {
            case "+":
                System.out.print(oprd1 + oprd2);
                break;
            case "-":
                System.out.print(oprd1 - oprd2);
                break;
            case "*":
                System.out.print(oprd1 * oprd2);
                break;
            case "/":
                System.out.print(oprd1 / oprd2);
                break;
            default:
                System.out.print("NOT supported operator");
                break;
        }
        printTradeMark("Calculator");
    }

    @Override
    public void calculate(String expr) {
        String[] oprs = {"+", "-", "*", "/"};
        int i;
        for (i = 0; i < oprs.length; i++)
            if (expr.indexOf(oprs[i]) >= 0) // expr에 oprs[i] 있는지 조사하고
                break;                      // 있으면 expr 내의 인덱스, 없으면 음수 반환
        if ((i >= oprs.length))             // expr에 적절한 연산자가 없을 경우
            calculate(0, expr, 0);          // 에러 처리를 위해 호출함
        else {
            String a = "";
            String[] opr = null;
            for (var s : oprs) {
                if (expr.contains(s)) {
                    a = s;
                }
            }
            switch (a) {
                case "+":
                    opr = expr.split("\\+");
                    break;
                case "*":
                    opr = expr.split("\\*");
                    break;
                case "-":
                    opr = expr.split("-");
                    break;
                case "/":
                    opr = expr.split("/");
                    break;
            }
            calculate(Double.parseDouble(opr[0]), a, Double.parseDouble(opr[1]));
        }
    }

    @Override
    public String getMaker() {
        return "SAMSUNG Phone ";
    }

    @Override
    public SmartPhone clone() {
        return new GalaxyPhone(owner);
    }
}

class IPhone extends SmartPhone {
    String model;

    public IPhone(String owner, String model) {
        super(owner);
        this.model = model;
    }

    @Override
    public void sendCall(String callee) {
        System.out.print(owner + "'s IPhone " + model + ": ");
        System.out.println("made a call to " + callee);
        baseStation.connectTo(owner, callee);
    }

    @Override
    public void receiveCall(String caller) {
        System.out.print(owner + "'s IPhone " + model + ": ");
        System.out.println("received a call from " + caller);
    }

    private double add(double oprd1, double oprd2) {
        return oprd1 + oprd2;
    }

    private double sub(double oprd1, double oprd2) {
        return oprd1 - oprd2;
    }

    private double mul(double oprd1, double oprd2) {
        return oprd1 * oprd2;
    }

    private double div(double oprd1, double oprd2) {
        return oprd1 / oprd2;
    }

    @Override
    public void calculate(double oprd1, String op, double oprd2) {
        switch (op) {
            case "+":
                System.out.println(owner + "'s IPhone " + model + ": " + oprd1 + " " + op + " " + oprd2 + " = " + add(oprd1, oprd2));
                break;
            case "-":
                System.out.println(owner + "'s IPhone " + model + ": " + oprd1 + " " + op + " " + oprd2 + " = " + sub(oprd1, oprd2));
                break;
            case "*":
                System.out.println(owner + "'s IPhone " + model + ": " + oprd1 + " " + op + " " + oprd2 + " = " + mul(oprd1, oprd2));
                break;
            case "/":
                System.out.println(owner + "'s IPhone " + model + ": " + oprd1 + " " + op + " " + oprd2 + " = " + div(oprd1, oprd2));
                break;
            default:
                System.out.println(owner + "'s IPhone " + model + ": " + op + " = NOT supported operator");
                break;
        }
    }

    @Override
    public void calculate(String expr) {
        String[] oprs = {"+", "-", "*", "/"};
        int i;
        int j = 0;
        for (i = 0; i < oprs.length; i++)
            if (expr.indexOf(oprs[i]) >= 0) // expr에 oprs[i] 있는지 조사하고
                break;                      // 있으면 expr 내의 인덱스, 없으면 음수 반환
        if ((i >= oprs.length))             // expr에 적절한 연산자가 없을 경우
            calculate(0, expr, 0);          // 에러 처리를 위해 호출함
        else {
            for (int a = 0; a < 4; a++) {
                if (expr.indexOf(oprs[a]) != -1) {
                    j = expr.indexOf(oprs[a]);
                    break;
                }
            }

            String first = expr.substring(0, j);
            String opr = expr.substring(j, j + 1).trim();
            String last = expr.substring(j + 1);

            expr = first + " " + opr + " " + last;

            Scanner s = new Scanner(expr);
            calculate(Double.parseDouble(s.next()), s.next(), Double.parseDouble(s.next()));
            s.close();
        }
    }

    @Override
    public String getMaker() {
        return "Apple Phone ";
    }

    @Override
    public SmartPhone clone() {
        return new IPhone(owner, model);
    }
}

class Person implements Comparable<Person> {
    private String name;    // 이름
    private int id;      // Identifier
    private double weight;  // 체중
    private boolean married; // 결혼여부
    private String address; // 주소
    private String passwd = ""; // 비밀번호
    private SmartPhone smartPhone; // 스마트폰: 5_3에서 추가

    private String memo;       // 메모: 6_2

    // 생성자 함수들
    public Person(String name, int id, double weight, boolean married, String address) {
        this(name, id, weight, married, address, null);
//        System.out.print("Person(): ");
//        printMembers();
//        System.out.println();
    }

    public Person(String name) {
        this(name, 0, 0.0, false, "");
    }

    public Person(Scanner sc) {
        inputMembers(sc);
    }

    public Person(String name, int id, double weight, boolean married, String address, SmartPhone smartPhone) {
        set(name, "", id, weight, married, address, smartPhone);
    }

    public void set(String name, String passwd, int id, double weight, boolean married, String address, SmartPhone smartPhone) {
        this.name = name;
        this.id = id;
        this.weight = weight;
        this.married = married;
        this.address = address;
        setSmartPhone(smartPhone);
        memo = ""; // 6_2
    }

//    public void println() {
//        print();
//        System.out.println();
        /*
        1. println() 호출
        2. print() 호출
        3. 슈퍼 클래스의 print()가 호출되지 않고 원래 객체 내에서 오버라이딩된 함수가 호출됨.(5장 18p 참고)
        */
//    }

//    public void println(String msg) {
//        System.out.print(msg);
//        print();
//        System.out.println();
//    }

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

    public String getPasswd() {
        return passwd;
    }

    public SmartPhone getSmartPhone() {
        return smartPhone;
    }

    public SmartPhone getCalculator() {
        return smartPhone;
    }

    public SmartPhone getPhone() {
        return smartPhone;
    }

    public String getMemo() {
        return memo;
    } // 6_2

    // Setter: overloading: set() 함수 중복
    public void set(String name) {
        this.name = name;
        smartPhone.setOwner(name);
    } // 5_3: smartPhone

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

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    // 이 함수는 smPhone이 null인 경우 id에 따라 GalaxyPhone 또는 IPhone 객체를 생성한다.
    public void setSmartPhone(SmartPhone smPhone) { // 5_3
        if (smPhone != null) {
            smartPhone = smPhone;
            smartPhone.setOwner(name);
        } else
            smartPhone = ((id % 2) == 1) ? new GalaxyPhone(name) : // id가 홀수인 경우
                    new IPhone(name, "13"); // id가 짝수인 경우
    }

    public void setMemo(String m) {
        memo = m;
    }

    public Person(Person p) { // 복사 생성자
        assign(p);
//        System.out.print("Person(p): ");
//        printMembers();
//        System.out.println();
    }

    // Candidates for virtual functions and overriding

    // assign() 함수
    public void assign(Person user) {
        set(user.getName(), user.getPasswd(), user.getId(), user.getWeight(), user.getMarried(), user.getAddress(), user.getSmartPhone().clone());
    }

    // print(), clone(), whatAreYouDoing(), equals(), input() 함수
    @Override
    public boolean equals(Object o) {
        Person p = (Person) o;
        return (p.getName().equals(getName()) && p.getId() == getId());
    }

//    void print() {
//        printMembers();
//    }

    void input(Scanner sc) {
        inputMembers(sc);
    }

    public void whatAreYouDoing() {
        System.out.println(name + " is taking a rest.");
    }

    public Person clone() {
//        System.out.println("Person::clone()");
        return new Person(this);
    }

    private void inputMembers(Scanner sc) {
        name = sc.next();
        id = sc.nextInt();
        weight = sc.nextDouble();
        married = sc.nextBoolean();
        while ((address = sc.findInLine(":.*:")) == null)
            sc.nextLine();
        address = address.substring(1, address.length() - 1);
        set(name, "", id, weight, married, address, null);
    }

    @Override
    public String toString() {
        return name + " " + id + " " + weight + " " + married + " :" + address + ": ";
    }

    @Override
    public int compareTo(Person p) {
        return this.getName().compareTo(p.name);
    }
//    private void printMembers() {
//        System.out.print(name + " " + id + " " + weight + " " + married + " :" + address + ": ");
//    }
}

class Student extends Person {
    private String department = ""; // 학과
    private double GPA;        // 평균평점
    private int year;          // 학년

    public Student(String name, int id, double weight, boolean married, String address, String department, double GPA, int year) {
        super(name, id, weight, married, address);
        set(department, GPA, year);
//        System.out.print("Student(): ");
//        printMembers();
    }

    public Student(Student s) {
        super(s);
        set(s.department, s.GPA, s.year);
//        System.out.print("Student(s): ");
//        printMembers();
    }

    // getter and setter
    public void set(String department, double GPA, int year) {
        this.department = department;
        this.GPA = GPA;
        this.year = year;
    }

    public String getDepartment() {
        return department;
    }

    public double getGPA() {
        return GPA;
    }

    public int getYear() {
        return year;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    public void setYear(int year) {
        this.year = year;
    }

    // Overriding
//    @Override
//    public void print() {
//        super.print();
//        printMembers();
//    }

    @Override
    public boolean equals(Object p) {
        Student s = (Student) p;
        return (super.equals(s) && s.getDepartment().equals(getDepartment()) && s.getYear() == getYear());
    }

    @Override
    public void whatAreYouDoing() {
        System.out.println("~~~~~~~~~~~~~~~~ Student::whatAreYouDoing() ~~~~~~~~~~~~~~~~");
        study();
        takeClass();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    @Override
    public Person clone() {
//        System.out.println("Student::clone()");
        return new Student(this);
    }

    @Override
    public void assign(Person user) {
        Student s = (Student) user;
        super.assign(s);
        set(s.getDepartment(), s.getGPA(), s.getYear());
    }

    void input(Scanner sc) {
        super.input(sc);
        inputMembers(sc);
    }

    private void inputMembers(Scanner sc) {
        department = sc.next();
        GPA = sc.nextDouble();
        year = sc.nextInt();
        set(department, GPA, year);
    }

    public Student(Scanner sc) {
        super(sc);
        inputMembers(sc);
    }

    // printMembers(), inputMembers(Scanner sc)
//    private void printMembers() {
//        System.out.print(department + " " + GPA + " " + year);
//    }

    @Override
    public String toString() {
        return super.toString() + department + " " + GPA + " " + year;
    }

    // 새로 추가된 메소드
    public void study() {
        System.out.println(getName() + " is studying as a " + year + "-year student in " + department);
    }

    public void takeClass() {
        System.out.println(getName() + " took several courses and got GPA " + GPA);
    }
}

class Worker extends Person {
    private String company = "";    // 회사명

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    private String position = "";   // 직급

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Worker(Worker s) {
        super(s);
        set(s.company, s.position);
//        System.out.print("Worker(w): ");
//        printMembers();
    }

    public Worker(String name, int id, double weight, boolean married, String address, String company, String position) {
        super(name, id, weight, married, address);
        set(company, position);
//        System.out.print("Worker(): ");
//        printMembers();
    }

    // getter and setter
    public void set(String company, String position) {
        this.company = company;
        this.position = position;
    }

    // Overriding
    @Override
    public boolean equals(Object p) {
        Worker s = (Worker) p;
        return (super.equals(s) && s.getCompany().equals(getCompany()) && s.getPosition().equals(getPosition()));
    }

    @Override
    public void whatAreYouDoing() {
        System.out.println("!!!!!!!!!!!!!!!! Worker::whatAreYouDoing()!!!!!!!!!!!!!!!!!");
        work();
        goOnVacation();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @Override
    public Person clone() {
//        System.out.println("Worker::clone()");
        return new Worker(this);
    }

    @Override
    public void assign(Person user) {
        Worker s = (Worker) user;
        super.assign(s);
        set(s.getCompany(), s.getPosition());
    }

//    @Override
//    public void print() {
//        super.print();
//        printMembers();
//    }

    // printMembers(), inputMembers(Scanner sc)
    void input(Scanner sc) {
        super.input(sc);
        inputMembers(sc);
    }

    private void inputMembers(Scanner sc) {
        company = sc.next();
        position = sc.next();
        set(company, position);
    }

    public Worker(Scanner sc) {
        super(sc);
        inputMembers(sc);
    }

//    private void printMembers() {
//        System.out.print(company + " " + position);
//    }

    @Override
    public String toString() {
        return super.toString() + company + " " + position;
    }

    // 새로 추가된 메소드
    public void work() {
        System.out.println(getName() + " works in " + company + " as " + position);
    }

    public void goOnVacation() {
        System.out.println(getName() + " is now enjoying his(her) vacation.");
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

    public static String getNextLine(String msg) {
        System.out.print(msg);
        String line = scan.nextLine();
        if (echo_input) System.out.println(line);
        return line;
    }
}

class Memo {
    private final StringBuffer mStr;  // 메모를 저장하고 수정하기 위한 문자열 버퍼

    // 문자열 m을 이용하여 StringBuffer를 생성한다.
    public Memo(String m) {
        mStr = new StringBuffer(m);
    }

    // StringBuffer mStr을 문자열로 변환하여 리턴한다.
    public String toString() {
        return mStr.toString();
    }

    private String memoData =
            "ten ten ten ten ten ten ten ten ten ten\n" +
                    "eight eight eight eight eight eight eight eight\n" +
                    "EIGHT EIGHT EIGHT EIGHT EIGHT EIGHT EIGHT EIGHT\n" +
                    "seven seven seven seven seven seven seven\n" +
                    "six six six six six six\n" +
                    "five five five five five\n" +
                    "four four four four\n" +
                    "three three three\n" +
                    "- - - - - - - - - - - - - - - - - - - - -\n" +
                    "The Last of the Mohicans\n" +
                    "James Fenimore Cooper\n" +
                    "Author's Introduction\n" +
                    "It is believed that the scene of this tale, and most of the information \n" +
                    "necessary to understand its allusions, are rendered sufficiently \n" +
                    "obvious to the reader in the text itself, or in the accompanying notes.\n" +
                    "Still there is so much obscurity in the Indian traditions, and so much \n" +
                    "confusion in the Indian names, as to render some explanation useful.\n" +
                    "Few men exhibit greater diversity, or, if we may so express it, \n" +
                    "greater antithesis of character, \n" +
                    "than the native warrior of North America.";

    public void run() {
        String menuStr =  // ch7_3
                "+++++++++++++++++++++ Memo Management Menu +++++++++++++++++++\n" +
                        "+ 0.exit 1.display 2.find 3.findReplace 4.compare 5.dispByLn +\n" +
                        "+ 6.delLn 7.replLn 8.scrollUp 9.scrollDown 10.inputMemo      +\n" +
                        "+ 11.wordCount 12.countWordList                              +\n" +
                        "++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";

        // 멤버 mStr이 비었을 경우 위 memoData로 초기화한다.
        if (mStr.length() == 0) mStr.append(memoData);
        final int MENU_COUNT = 13; // 상수 정의

        while (true) {
            int menuItem = UI.selectMenu(menuStr, MENU_COUNT);
            switch (menuItem) {
                case 1:
                    display();
                    break;
                case 2:
                    find();
                    break;
                case 3:
                    findReplace();
                    break;
                case 4:
                    compare();
                    break;
                case 5:
                    dispByLn();
                    break;
                case 6:
                    delLn();
                    break;
                case 7:
                    replLn();
                    break;
                case 8:
                    scrollUp();
                    break;
                case 9:
                    scrollDown();
                    break;
                case 10:
                    inputMemo();
                    break;
                case 11:
                    wordCount();
                    break; // ch7_3
                case 12:
                    countWordList();
                    break; // ch7_3
                case 0:
                    return;
            }
        }
    }

    void display() { // Menu item 1
        System.out.println("------- Memo -------");
        System.out.print(mStr);
        if (mStr.length() > 0 && mStr.charAt(mStr.length() - 1) != '\n')
            System.out.println();
        System.out.println("--------------------");
    }

    // content 문자열의 start 인덱스부터 word 문자열 단어를 찾아 그 단어의 시작 인덱스를 반환함
    // 찾지 못한 경우 -1를 반환함
    // " "로 분리된 word 단어만 찾고 다른 긴 단어 속에 포함된 경우에는 스킵한다.
    private int findWord(String content, String word, int start) {
        // content.indexOf(word, start):
        //    content 문자열의 start 인덱스 위치부터 word를 검색하고
        //    찾은 경우 찾은 시작 위치를, 못 찾은 경우 -1를 반환함
        //    word가 다른 긴 단어의 속에 포함되어 있어도 검색이 된다.
        if ((start = content.indexOf(word, start)) == -1)
            return -1; // 못 찾은 경우
        if (start > 0) {
            // 찾은 단어의 앞이 공백이 아닌 경우 (긴 단어 속에 포함되어 있는 경우임)
            //    찾은 단어 뒤쪽으로 계속 찾음; 재귀 함수
            if (!Character.isWhitespace(content.charAt(start - 1)))
                return findWord(content, word, start + word.length());
        }
        // 찾은 단어가 content의 끝에 있을 경우
        if ((start + word.length()) == content.length())
            return start; // 찾은 경우
        // 찾은 단어의 뒤가 공백이 아닌 경우 (긴 단어 속에 포함되어 있는 경우임)
        //    찾은 단어 뒤쪽으로 계속 찾음; 재귀 함수
        if (!Character.isWhitespace(content.charAt(start + word.length())))
            return findWord(content, word, start + word.length());
        return start; // 찾은 경우
    }

    void find() { // Menu item 2
        String word = UI.getNext("word to find? ");
        // 전체 메모 mStr를 문자열로 변환한 후 이를 행 단위로 쪼갬
        // 아래 구분자 "\\v"은 행 단위로 쪼개라는 구분자를 의미함; 즉, '\n','\r','\f'등을 의미함
        String[] lines = mStr.toString().split("\\v");
        int tot_count = 0; // 단어를 찾은 총 횟수

        int j;
        for (int i = 0; i < lines.length; i++) {
            int count = 0;
            for (j = 0; ; ) {
                j = findWord(lines[i], word, j);
                if (j >= 0) {
                    count++;
                    j += word.length();
                    findWord(lines[i], word, j);
                } else break;
            }
            if (count > 0) {
                tot_count += count;
                System.out.println("[" + i + "] " + lines[i]);
            }
        }

        System.out.println("--------------------");
        System.out.println(tot_count + " words found");
    }

    // content 문자열의 start 인덱스부터 word 문자열 단어를 찾아 그 단어의 시작 인덱스를 반환함
    // 찾지 못한 경우 -1를 반환함
    // " "로 분리된 word 단어만 찾고 다른 긴 단어 속에 포함된 경우에는 스킵한다.
    private int findWord(StringBuffer content, String word, int start) {
        // content.indexOf(word, start):
        //    content 문자열의 start 인덱스 위치부터 word를 검색하고
        //    찾은 경우 찾은 시작 위치를, 못 찾은 경우 -1를 반환함
        //    word가 다른 긴 단어의 속에 포함되어 있어도 검색이 된다.
        if ((start = content.indexOf(word, start)) == -1)
            return -1; // 못 찾은 경우
        if (start > 0) {
            // 찾은 단어의 앞이 공백이 아닌 경우 (긴 단어 속에 포함되어 있는 경우임)
            //    찾은 단어 뒤쪽으로 계속 찾음; 재귀 함수
            if (!Character.isWhitespace(content.charAt(start - 1)))
                return findWord(content, word, start + word.length());
        }
        // 찾은 단어가 content의 끝에 있을 경우
        if ((start + word.length()) == content.length())
            return start; // 찾은 경우
        // 찾은 단어의 뒤가 공백이 아닌 경우 (긴 단어 속에 포함되어 있는 경우임)
        //    찾은 단어 뒤쪽으로 계속 찾음; 재귀 함수
        if (!Character.isWhitespace(content.charAt(start + word.length())))
            return findWord(content, word, start + word.length());
        return start; // 찾은 경우
    }

    void findReplace() { // Menu item 3
        String find = UI.getNext("word to find? ");
        String repl = UI.getNext("word to replace? ");
        int count = 0; // 단어를 교체(찾은)한 횟수
        for (int i = 0; ; ) {
            i = findWord(mStr, find, i);
            if (i >= 0) {
                mStr.replace(i, i + find.length(), repl);
                count++;
                i += repl.length();
                findWord(mStr, find, i);
            } else break;
        }
        display();
        System.out.println(count + " words replaced");
    }

    void compare() { // Menu item 4
        int less = 0, same = 0, larger = 0;
        String word = UI.getNext("word to compare? ");
        String m = mStr.toString();
        String[] tokens = m.split("\\s");
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].compareTo(word) > 0 && tokens[i].length() > 0) {
                larger++;
            }
            if (tokens[i].compareTo(word) == 0 && tokens[i].length() > 0) {
                same++;
            }
            if (tokens[i].compareTo(word) < 0 && tokens[i].length() > 0) {
                less++;
            }
        }
        System.out.println("less: " + less);
        System.out.println("same: " + same);
        System.out.println("larger: " + larger);
    }

    void dispByLn() { // Menu item 5
        System.out.println("--- Memo by line ---");
        if (mStr.length() > 0) {
            String m = mStr.toString();
            String[] lines = m.split("\\v");
            int count = 0;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].length() > 0) {
                    System.out.println("[" + count + "] " + lines[i]);
                    count++;
                }
            }
        }
        System.out.println("--------------------");
    }

    // 두 개의 정수 값(start, end)을 가지는 클래스
    // 함수에서 두 개의 값을 한꺼번에 리턴하고자 할 때 이 클래스의 객체를 생성하여 반환한다.
    // private 클래스이므로 이 클래스는 Memo 클래스 내에서만 사용가능하다.
    private class Pair {
        public int start, end;

        Pair(int s, int e) {
            start = s;
            end = e;
        }
    }

    // 행번호 lineNum(행은 0부터 시작)인 행의 시작 위치인 start와 (행의 끝+1)의 위치인 end를 찾아줌.
    // end는 사실 그 다음 행(lineNum+1)의 시작 위치이며 마지막 행인 경우 mStr.length()와 같다.
    // 해당 행 번호를 찾았으면 start, end 값을 가진 Pair 객체를, 찾지 못했으면 null을 반환
    private Pair find_line(int lineNum) {
        int start = 0, end = 0;

        if (mStr.length() == 0) {
            return null;
        }

        for (int i = 0; i < lineNum; i++) {
            end = mStr.indexOf("\n", start);
            start = end + 1;
            if (end == -1 || start == mStr.length()) {
                return null;
            }
        }
        end = mStr.indexOf("\n", start);

        if (end != -1) {
            end++;
        }
        if (end == -1) {
            end = mStr.length();
        }

        return new Pair(start, end);
    }

    void delLn() { // Menu item 6
        int lineNum = UI.getPosInt("line number to delete? ");
        Pair p;
        if (mStr.length() == 0 || (p = find_line(lineNum)) == null) {
            System.out.println("Out of line number range");
        } else {
            mStr.delete(p.start, p.end);
            dispByLn();
        }
    }

    void replLn() { // Menu item 7
        int lineNum = UI.getPosInt("line number to replace? ");
        Pair p;
        if (mStr.length() == 0 || (p = find_line(lineNum)) == null) {
            System.out.println("Out of line number range");
        } else {
            String text = UI.getNextLine("input content to replace:\n");
            p.end = mStr.indexOf("\n", p.start);
            if (p.end == -1) {
                p.end = mStr.length();
            }
            mStr.replace(p.start, p.end, text);
            dispByLn();
        }
    }

    void scrollUp() { // Menu item 8
        if (mStr.length() == 0) {
            dispByLn();
            return;
        } else if (mStr.charAt(mStr.length() - 1) != '\n') {
            mStr.append('\n');
        }
        Pair p = find_line(0);
        String text = mStr.substring(p.start, p.end);
        mStr.append(text);
        mStr.delete(p.start, p.end);
        dispByLn();
    }

    // 마지막 행의 시작 위치를 구하여 반환한다.
    private int find_last_line() {
        int start = 0, pos;
        for (; ; ) {
            pos = mStr.indexOf("\n", start);
            if (pos == -1 || pos + 1 == mStr.length()) {
                break;
            }
            start = pos + 1;
        }
        return start;
    }

    void scrollDown() { // Menu item 9
        if (mStr.length() == 0) {
            dispByLn();
            return;
        }
        if (mStr.charAt(mStr.length() - 1) != '\n') {
            mStr.append('\n');
        }
        int last_line_start = find_last_line();
        int last_line_end = mStr.length();
        String text = mStr.substring(last_line_start, last_line_end);
        mStr.delete(last_line_start, last_line_end);
        mStr.insert(0, text);
        dispByLn();
    }

    void inputMemo() { // Menu item 10
        mStr.setLength(0);
        System.out.println("--- input memo lines, and then input empty line at the end ---");
        while (true) {
            String text = UI.getNextLine("");
            if (text.equals("")) {
                break;
            }
            mStr.append(text).append("\n");
        }
    }

    // 이 함수는 Memo::mStr에 들어 있는 문자열을 빼내 이를 단어 단위로 토큰을 자른 후
    // 각 단어별 출현 횟수를 Map에 저장한 후 해당 Map을 반환한다.
    // 리턴 데이터 타입인 Map< String, Integer >에서 String은 단어이며,
    // Integer는 해당 단어의 출현 횟수이다. 반환하는 Map으로는 TreeMap 또는 HashMap 중에서
    // 단어별 정렬이 미리 되어 있는 적절한 Map을 생성해서 리턴해야 한다.
    private Map<String, Integer> getWordCountMap() { // ch7_3
        String[] words = mStr.toString().split("\\s");
        var wordCountMap = new TreeMap<String, Integer>();
        for (String w : words) {
            wordCountMap.put(w, 0);
        }

        for (String w : words) {
            if (wordCountMap.get(w) == null && !w.equals("")) {
                wordCountMap.put(w, 1);
            } else if (wordCountMap.get(w) != null && !w.equals("")) {
                wordCountMap.put(w, wordCountMap.get(w) + 1);
            }
        }
        return wordCountMap; // Map으로 자동 업 케스팅되어 리턴됨
    }

    void wordCount() { // Menu item 11
        var wordCountMap = getWordCountMap();
        System.out.println("----------------");
        System.out.println("Word      Count");
        System.out.println("----------------");

        Set<Map.Entry<String, Integer>> wcEntries = wordCountMap.entrySet();
        for (var wc : wcEntries) {
            var word = wc.getKey();
            var count = wc.getValue();
            if (count > 1)  // %-7s: 문자열을 7 칸 안에 출력하되 좌 맞춤
                System.out.printf("%-7s    %2d\n", word, count);
        }
        System.out.println("----------------");
    }

    void countWordList() {
        var wordCountMap = getWordCountMap();
        var wcEntries = wordCountMap.entrySet();
        var countWordsMap = new TreeMap<Integer, Vector<String>>();
        for (var wc : wcEntries) {
            var word = wc.getKey();
            var count = wc.getValue();
            var wordVct = countWordsMap.get(count);
            if (wordVct == null) {
                wordVct = new Vector<>(Arrays.asList(word));
                countWordsMap.put(count, wordVct);
            } else wordVct.add(word);
        }

        System.out.println("----------------");
        System.out.println("Count  Words");
        System.out.println("----------------");

        // 아래는 키의 순서를 역순으로 재배치한 새로운 맵 reverseCWMap을 생성한다.
        // 이 맵은 단어의 출현 횟수가 큰 수부터 작은 수 순서로 횟수를 저장하고 있다.
        var reverseCWMap = countWordsMap.descendingMap();
        var cwEntries = reverseCWMap.entrySet();
        for (var cw : cwEntries) {
            var count = cw.getKey();
            if (count > 1) {
                System.out.printf("%2d     ", count);
                for (var w : cw.getValue()) {
                    System.out.print(w + " ");
                }
                System.out.println();
            }
        }
        System.out.println("----------------");
    }
}   // Memo class: ch6_2

class CurrentUser {
    Person user;

    CurrentUser(Person user) {
        this.user = user;
    }

    public void run() {
        String menuStr =
                "++++++++++++++++++++++ Current User Menu ++++++++++++++++++++++++\n" +
                        "+ 0.logout 1.display 2.getter 3.setter 4.copy 5.whatAreYouDoing +\n" +
                        "+ 6.isSame 7.update 8.chPasswd(4_2) 9.chSmartPhone(5_3)         +\n" +
                        "+ 10.clone(5_3) 11.calc(5_3) 12.phoneCall(5_3) 13.chWeight(6_1) +\n" +
                        "+ 14.calcString(6_2) 15.memo(6_2)                               +\n" +
                        "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
        final int MENU_COUNT = 16;
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
                case 8:
                    chPasswd();
                    break;
                case 9:
                    chSmartPhone();
                    break;
                case 10:
                    userClone();
                    break;
                case 11:
                    calc();
                    break;
                case 12:
                    phoneCall();
                    break;
                case 13:
                    chWeight();
                    break;
                case 14:
                    calcString();
                    break;
                case 15:
                    memo();
                    break;
                case 0:
                    return;
            }
        }
    }

    void chPasswd() {
        String passwd = UI.getNext("new password: ");
        user.setPasswd(passwd);
        System.out.println("password changed");
    }

    void chSmartPhone() { // Menu item 9
        String maker = UI.getNext("maker of phone to change(ex: Samsung or Apple)? ");
        if (maker.equals("Samsung")) {
            var s = new GalaxyPhone(user.getName());
            user.setSmartPhone(s);

        } else if (maker.equals("Apple")) {
            var a = new IPhone(user.getName(), "14");
            user.setSmartPhone(a);
        } else {
            System.out.println(maker + ": WRONG phone's maker");
            return;
        }
        display();
    }

    void userClone() { // Menu item 10
        display();
        Person c = user.clone();
        System.out.println("------------------\nclone:");
        System.out.println(c);
        System.out.println(c.getSmartPhone());
        System.out.println("\nchange clone's name " + c.getName() + " to c1\n");
        c.set("c1"); // clone의 이름을 c1으로 변경함: 스마트폰의 소유주도 c1으로 변경됨
        display();
        System.out.println("------------------\nclone:");
        System.out.println(c);
        System.out.println(c.getSmartPhone());
    }

    void calc() { // Menu item 11: 연산자와 피연산자는 스페이스로 분리되어 있어야 함
        System.out.print("expression: ");
        String op;
        double d1, d2;
        d1 = UI.scan.nextDouble();
        op = UI.scan.next();
        d2 = UI.scan.nextDouble();
        if (UI.echo_input) System.out.println(d1 + " " + op + " " + d2); // 자동오류체크시 출력됨
        user.getCalculator().calculate(d1, op, d2);
    }

    void phoneCall() { // Menu item 12
        // PersonManager에 등록되어 있는 사용자 중 한명의 이름을 입력하라.
        String callee = UI.getNext("name to call? ");
        user.getPhone().sendCall(callee);
    }

    void chWeight() { // Menu item 13
        double sr = Math.sqrt(user.getWeight());
        System.out.println("weight:" + user.getWeight() + " sqrt:" + sr + " ceil:" + Math.ceil(sr) + " floor:" + Math.floor(sr) + " round:" + Math.round(sr));
        user.set(Math.ceil(sr) * Math.floor(sr));
        System.out.println(user);
    }

    // Menu item 14:ch6_2: "2+3", "2+ 3"처럼 연산자와 피연산자가 붙어 있어도 됨
    void calcString() {
        String line = UI.getNextLine("expression: ");
        user.getCalculator().calculate(line);
    }

    void memo() { // Menu item 15: ch6_2
        Memo m = new Memo(user.getMemo());
        m.run();
        user.setMemo(m.toString());
    }

    void display() {
        System.out.println(user);
        System.out.println(user.getSmartPhone());

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
        System.out.println("p.set(): " + p);
    }

    void copy() { // Menu item 4
        System.out.println("user: " + user);
        var p = user.clone();
        System.out.println("p: " + p);
    }

    void whatAreYouDoing() {  // Menu item 5
        user.whatAreYouDoing();
    }

    void equals() { // Menu item 6
        System.out.println("user: " + user);
        var p = new Person("user");
        p.set(1);
        System.out.println("p: " + p);
        System.out.println("p.equals(user): " + p.equals(user));
        p.assign(user);
        System.out.println("p.assign(user): " + p);
        System.out.println("p.equals(user): " + p.equals(user));
    }

    void update() { // Menu item 7
        System.out.println("input person information:");
        user.input(UI.scan); // user 100 65 true :426 hakdong-ro, Gangnam-gu, Seoul:
        if (UI.echo_input) System.out.println(user); // 자동오류체크시 출력됨
        display();
    }
}

class Factory {
    public void printInputNotice(String preMsg, String postMsg) {
        System.out.println("input" + preMsg + " [delimiter(P,S,or W)]" +
                " [person information]" + postMsg + ":");
    }

    public Person inputPerson(Scanner sc) {
        Person p = null;
        String delimiter = sc.next();
        switch (delimiter) {
            case "S":
                p = new Student(sc);
                break;
            case "W":
                p = new Worker(sc);
                break;
            case "P":
                p = new Person(sc);
                break;
            default:
                String nextLn = sc.nextLine();
                if (UI.echo_input) System.out.println(delimiter + nextLn);
                System.out.println(delimiter + ": WRONG delimiter");
                return null;
        }
        if (UI.echo_input) System.out.println(delimiter.equals("") ? "" : delimiter + " " + p);
        return p;
    }
}

class PersonManager implements BaseStation {

    //    private final VectorPerson pVector;
    int cpCount = 0;
    private Vector<Person> pVector;
    private final Factory factory;
    private final Person[] array;
    private Random rand; // 7_1 추가

    public PersonManager(Person[] array, Factory factory) {
//        System.out.println("PersonManager(array[])");
        pVector = new Vector<>();
        this.factory = factory;
        this.array = array;
        addArray();
        display();
        SmartPhone.setBaseStation(this);
        cpCount = 0;                     // 7_1 추가
        rand = new Random(0);  // 0: seed 값임, 7_1 추가
    }

    public void run() {
        String menuStr =
                "=================== Person Management Menu =====================\n" +
                        "= 0.exit 1.display 2.clear 3.reset 4.remove 5.copy 6.append    =\n" +
                        "= 7.insert 8.login 9.dispStudent(5_3) 10.dispPhone(5_3)        =\n" +
                        "= 11.find(6_1) 12.wrapper(6_1) 13.shuffle(6_1) 14.setDate(6_1) =\n" +
                        "= 15.chAddress(6_2) 16.collections(7_1) 17.fileManager(8_1)    =\n" +
                        "================================================================\n";
        final int MENU_COUNT = 18; // 상수 정의
        while (true) {
            int menuItem = UI.selectMenu(menuStr, MENU_COUNT);
            switch (menuItem) {
                case 1:
                    display();
                    break;
                case 2:
                    clear();
                    break;
                case 3:
                    reset();
                    break;
                case 4:
                    remove();
                    break;
                case 5:
                    copy();
                    break;
                case 6:
                    append();
                    break;
                case 7:
                    insert();
                    break;
                case 8:
                    login();
                    break;
                case 9:
                    dispStudent();
                    break;
                case 10:
                    dispPhone();
                    break;
                case 11:
                    find();
                    break;
                case 12:
                    wrapper();
                    break;
                case 13:
                    shuffle();
                    break;
                case 14:
                    setDate();
                    break;
                case 15:
                    chAddress();
                    break;
                case 16:
                    collections();
                    break;
                case 17:
                    fileManager();
                    break;   // ch8_1
                case 0:
                    return;
            }
        }
    }

    public void display() { // Menu item 1
        int count = pVector.size();
        //System.out.println("display(): count " + count);
        for (int i = 0; i < count; ++i)
            System.out.println("[" + i + "] " + pVector.get(i));
        //System.out.println("empty():" + pVector.isEmpty() + ", size():" + pVector.size()
        //     + ", capacity():" + pVector.capacity());
    }

    public void clear() {  // Menu item 2
        pVector.clear();
        display();
    }

    public void reset() { // Menu item 3
        pVector.clear();
        addArray();
        display();
    }

    public void remove() { // Menu item 4
        if (pVector.isEmpty()) {
            System.out.println("no entry to remove");
            return;
        }
        int index = UI.getIndex("index to delete? ", pVector.size());
        pVector.remove(index);
        display();
    }

    public void copy() { // Menu item 5
        cpCount++;
        for (int i = 0, size = pVector.size(); i < size; ++i) {
            Person p = pVector.get(i).clone();
            String name = p.getName();
            for (int j = 0; j < cpCount; ++j)
                name = name.charAt(0) + name;
            p.set(name);
            p.set(p.getId() + 20 * cpCount);
            p.set(p.getWeight() + cpCount);
            if (cpCount % 2 == 1)
                p.set(!p.getMarried());
            pVector.add(p);
        }
        display();
    }

    public void append() { // Menu item 6
        int count = UI.getPosInt("number of persons to append? ");
        factory.printInputNotice(" " + Integer.toString(count), " to append");
        for (int i = 0; i < count; ++i) {
            Person p = factory.inputPerson(UI.scan);
            if (p != null) pVector.add(p);
            else i--;
        }
        display();
    }

    public void insert() { // Menu item 7
        int index = 0;
        if (pVector.size() > 0) {
            index = UI.getIndex("index to insert in front? ", pVector.size() + 1);
            if (index < 0) return;
        }
        factory.printInputNotice("", " to insert");
        Person p = factory.inputPerson(UI.scan);
        if (p == null) {
            return;
        }
        pVector.add(index, p);
        display();
    }

    // 사용자로부터 VectorPerson pVector에 저장된 사람들 중에서 로그인할 사람의 이름(name)과 비번을 입력받고
    // 해당 비번이 맞으면 CurrentUser의 객체를 생성하고 이 객체의 run() 멤버 함수를 호출한다.
    // 초기 비번은 설정되어 있지 않기에 그냥 엔터치면 된다.
    public void login() {  // Menu item 8
        String name = UI.getNext("user name: ");
        Person p = findByName(name);
        if (p == null) return;
        String passwd = UI.getNextLine("password: ");
        //passwd.strip();
        if (passwd.equals(p.getPasswd()))
            new CurrentUser(p).run();
        else
            System.out.println("WRONG password!!");
    }

    public void dispStudent() { // Menu item 9: ch5_3
        int count = pVector.size();
        System.out.println("dispStudent():");
        for (int i = 0; i < count; ++i) {
            if (pVector.get(i) instanceof Student) {
                System.out.print("[" + i + "] ");
                System.out.println(pVector.get(i));
            }
        }
    }

    public void dispPhone() { // Menu item 10: ch5_3
        int count = pVector.size();
        System.out.println("dispPhones(): count " + count);
        for (int i = 0; i < count; ++i) {
            System.out.print("[" + i + "] ");
            System.out.println(pVector.get(i).getSmartPhone());
        }
    }

    public void find() { // Menu item 11: ch6_1
        boolean found = false;
        factory.printInputNotice("", " to find by equals()");
        Person p = factory.inputPerson(UI.scan); // 한 사람의 정보를 입력 받음
        if (p == null) return;
        for (int i = 0; i < pVector.size(); i++) {
            if (p.getClass() == pVector.get(i).getClass()) {
                if (pVector.get(i).equals(p)) {
                    System.out.print("[" + i + "] ");
                    System.out.println(pVector.get(i));
                    found = true;
                    return;
                }
            }
        }
        if (!found) {
            System.out.println("NOT found by equals()");
        }
    }

    void wrapper() { // Menu item 12: ch6_1
        for (int i = 0; i < pVector.size(); i++) {
            Person p = pVector.get(i);
            System.out.print("[" + i + "] ");
            dispPersonInfo(p.getName(), String.valueOf(p.getId()), String.valueOf(p.getWeight()), String.valueOf(p.getMarried()));
        }
    }

    private void dispPersonInfo(String sname, String sid, String sweight, String smarried) {
        char first = sname.charAt(0);
        char last = sname.charAt(sname.length() - 1);
        String add = sname.substring(1, sname.length() - 1);
        sname = last + add + first;
        if (sid.charAt(sid.length() - 1) == '0') {
            sid = sid.substring(0, sid.length() - 1) + '1';
        }
        if (smarried.equals("true")) {
            smarried = "false";
        } else {
            smarried = "true";
        }
        dispPersonInfo(sname, Integer.parseInt(sid), Double.parseDouble(sweight), Boolean.parseBoolean(smarried));
    }

    private void dispPersonInfo(String sname, int id, double weight, boolean married) {
        System.out.println(sname + " " + id + " 0x" + Integer.toHexString(id) + " 0" + Integer.toOctalString(id) + " 0b" + Integer.toBinaryString(id) + " " + weight + " " + married);
    }

    public void shuffle() { // Menu item 13: ch6_1
        int count = pVector.size();
        for (int i = 0; i < count; i++) {
//            int j = (int) (Math.random() * count);
            int j = rand.nextInt(pVector.size()); // [0 ~ (pVector.size()-1)]
            pVector.set(j, pVector.set(i, pVector.get(j)));
        }
        display();
    }

    public void setDate() { // Menu item 14: ch6_1
        String line = UI.getNextLine("date and time(ex: 2021 10 1 18 24 30)? ");
        SmartPhone.setDate(line);
    }

    void chAddress() { // Menu item 14: ch6_2
        for (int i = 0; i < pVector.size(); ++i) {
            Person p = pVector.get(i);
            p.setAddress(newAddress(p.getAddress()));
        }
        display();
    }

    public void collections() { // Menu item 16: ch7_1
        new CollectionsByList(pVector).run();
    }

    public void fileManager() {
        new FileManager(pVector).run();
    }

    private String newAddress(String address) {
        address = address.toLowerCase().replaceAll("-gu", "_gu");
        String[] arr = address.split(",");
        StringBuilder addressBuilder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1) {
                addressBuilder.append(arr[i].trim());
            } else {
                addressBuilder.append(arr[i].trim()).append(" ");
            }
        }
        return addressBuilder.toString();
    }

    @Override
    public boolean connectTo(String caller, String callee) {
        Person p = findByName(callee);
        if (p == null) return false;
        else {
            System.out.println("base station: sends a call signal of " + caller + " to " + callee);
            p.getPhone().receiveCall(caller);
            return true;
        }
    }

    // pVector에 삽입되어 있는 Person 객체들 중 사용자가 입력한 이름 name과
    // 동일한 이름을 가진 객체를 찾아 리턴한다.
    private Person findByName(String name) {
        int i, count = pVector.size();
        for (i = 0; i < count; ++i)
            if (name.equals(pVector.get(i).getName()))
                return pVector.get(i);
        System.out.println(name + ": NOT found");
        return null;
    }

    private void addArray() {
        for (Person p : array)
            pVector.add(p.clone()); // 배열의 각 원소를 복사한 후 pVector에  삽입함
    }
}

class MultiManager {
    private final Person[] persons = {        // ch6_2: p0, p1 주소 변경되었음(공백문자와 ,를 의도적으로 띄우거나 붙여 놓았음)
            new Person("p0", 10, 70.0, false, "Gwangju ,Nam-gu , Bongseon-dong 21"),
            new Person("p1", 11, 61.1, true, "Jong-ro 1-gil,Jongno-gu,   Seoul"),
            new Person("p2", 12, 52.2, false, "1001, Jungang-daero, Yeonje-gu, Busan"),
            new Person("p3", 13, 83.3, true, "100 Dunsan-ro Seo-gu Daejeon"),
            new Person("p4", 14, 64.4, false, "88 Gongpyeong-ro, Jung-gu, Daegu"),
    };

    private final Student[] students = {
            new Student("s1", 1, 65.4, true, "Jongno-gu Seoul", "Physics", 3.8, 1),
            new Student("s2", 2, 54.3, false, "Yeonje-gu Busan", "Electronics", 2.5, 4),
    };

    private final Worker[] workers = {
            new Worker("w1", 3, 33.3, false, "Kangnam-gu Seoul", "Samsung", "Director"),
            new Worker("w2", 4, 44.4, true, "Dobong-gu Kwangju", "Hyundai", "Manager"),
    };

    private final Person[] allPersons = {
            persons[0], persons[1], students[0], students[1], workers[0], workers[1],
    };

    public void run() {
//        System.out.println("PersonManager::run() starts");
        var pm = new PersonManager(allPersons, new Factory());
        pm.run();
//        System.out.println("PersonManager::run() returned");
    }
}

abstract class BaseManager {
    protected int ADD_SIZE = 10;
    protected int REPLACE_SIZE = 5;
    private Random rand;
    protected int count;

    public BaseManager() {
        rand = new Random(0);
        count = 10;
    }

    public void run() {
        String menuStr =
                "======= Base Person Management Menu ========\n" +
                        "= 0.exit 1.display 2.clear 3.add 4.replace =\n" +
                        "= 5.remove 6.find 7.collections            =\n" +
                        "============================================\n";
        final int MENU_COUNT = 9; // 상수 정의
        while (true) {
            int menuItem = UI.selectMenu(menuStr, MENU_COUNT);
            switch (menuItem) {
                case 1:
                    display();
                    break;
                case 2:
                    clear();
                    break;
                case 3:
                    add();
                    break;
                case 4:
                    replace();
                    break;
                case 5:
                    remove();
                    break;
                case 6:
                    find();
                    break;
                case 7:
                    collections();
                    break;
                case 0:
                    return;
            }
        }
    }

    abstract public void display(); // Menu item 1

    public void clear() {  // Menu item 2
        clearAllElements(); // 아래 추상함수 설명 참고
        display();
    }

    public void add() { // Menu item 3
        addElements();
        display();
    }

    abstract public void replace(); // Menu item 4

    abstract public void remove(); // Menu item 5

    abstract public void find(); // Menu item 6

    abstract public void collections(); // Menu item 7

    //-------------------------------------------------------------------------------------------
    // 기타 abstract functions
    //-------------------------------------------------------------------------------------------
    abstract public void clearAllElements(); // 컬렉션의 모든 원소를 삭제함

    // Person 객체들을 자동으로 생성하여 컬렉션에 추가함; 이때 아래 getXXX() 함수를 활용한다.
    abstract public void addElements();

    //-------------------------------------------------------------------------------------------
    // Person 객체를 자동으로 생성할 때 아래 함수들을 사용하라.
    //-------------------------------------------------------------------------------------------
    protected int getNewId() {
        return 1000 + rand.nextInt(1000);
    }

    protected String getNewName() {
        return families[rand.nextInt(families.length)] + (count++);
    }

    protected double getNewWeight() {
        double weight = 40 + rand.nextDouble() * 60; // 40 ~ 100 사이의 몸무게 생성
        return (int) (weight * 10) / (double) 10; // 소수점 한자리까지만 사용함
    }

    protected boolean getNewMarried(int id) {
        return (id % 2) == 1;
    }

    protected String getNewAddress() {
        return cities[rand.nextInt(cities.length)] + " " +
                gus[rand.nextInt(gus.length)];
    }

    private String families[] =
            {"Kimm", "Leem", "Park", "Choi", "Jeon", "Shim", "Shin", "Kang", "Yang", "Yoon",
                    "Baek", "Ryoo", "Seoh", "Johh", "Baeh", "Moon", "Nahh", "Jooh", "Song", "Hong"};
    private String cities[] =
            {"Seoul", "Busan", "Gwangju", "Daejeon", "Incheon", "Daegu", "Ulsan"};
    private String gus[] =
            {"Jung-gu", "Nam-gu", "Buk-gu", "Dong-gu", "Seo-gu",};

}   // ch7_2: BaseManager class

class PMbyMap extends BaseManager { // ch7_2
    // Map은 HashMap과 TreeMap의 수퍼 클래스(인터페이스)이며 map = hashMap으로 업캐스팅 가능함
    private Map<String, Person> map;
    private HashMap<String, Person> hashMap = null;
    private TreeMap<String, Person> treeMap = null;

    public PMbyMap() {
        String menuStr =
                "====== Map Menu =======\n" +
                        "= 0.HashMap 1.TreeMap =\n" +
                        "=======================\n";
        int mapType = UI.selectMenu(menuStr, 2);
        if (mapType == 0)
            map = hashMap = new HashMap<String, Person>();
        else if (mapType == 1)
            map = treeMap = new TreeMap<String, Person>();

        // 위에서 선택된 Map의 종류에 상관없이 이 이후부터는 map을 교재의 예제에서 보여준
        // HashMap이라 생각하고 멤버 함수들을 사용하여 코딩하면 된다.
        add(); // BaseManager 멤버 함수
    }

    static public void display(Map<String, Person> map) {
        System.out.println("Map Size: " + map.size());
        System.out.println("---------------------------------------------");
        // 교재에서 소개된 이 방식은 간단하기는 하나 각 키에 대해 매번 다시 map.get(name)을 통해
        // map 검색을 다시 해야 하는 오버헤드가 있다.
        /*
        Set< String > keySet = map.keySet();
        for (var name: keySet)
            System.out.println(map.get(name));
        */
        // 아래 방식은 EntrySet을 구하는데 map의 모든 entry를 다 가지고 있는 집합이다.
        // 각 entry는 { 키, 값 }으로 구성된다.
        // 각 entry e에 저장된 키와 값은 e.getKey(), e.getValue()를 통해 얻을 수 있다.
        // 이 방식을 사용하면 매번 map을 다시 검색하지 않아도 된다.
        Set<Map.Entry<String, Person>> entrySet = map.entrySet();
        for (var e : entrySet)
            System.out.println(e.getValue());
    }

    public void display() { // Menu item 1
        display(map);
    }

    // 모든 키 값에 대해 새로운 객체를 생성한 후 이름은 기존과 동일하게 설정한다.
    // 그런 후 동일한 키에 대해 값(객체)만 교체한다.
    public void replace() { // Menu item 4
        int i = 0;
        Set<String> keySet = map.keySet();
        for (var name : keySet) {
            var p = getNewPerson();
            p.set(name);
            map.put(name, p);
            if (++i == REPLACE_SIZE) break; // 초반의 REPLACE_SIZE 개수만큼만 교체함
        }
        display();
    }

    public void remove() { // Menu item 5
        if (map.isEmpty()) {
            System.out.println("no entry to remove");
            return;
        }
        String name = UI.getNext("name to delete? ");
        map.remove(name);
        display();
    }

    public void find() { // Menu item 6
        String name = UI.getNext("name to find? ");
        Person p = map.get(name);
        if (p != null) {
            System.out.println(p);
            return;
        }
        System.out.println(name + ": NOT found");
    }

    public void collections() { // Menu item 7
        if (hashMap == null)
            new CollectionsByTreeMap(treeMap).run();
        else
            new CollectionsByHashMap(hashMap).run();
    }

    public void clearAllElements() {  // BaseManager::clear()에서 호출됨
        map.clear();
    }

    public void addElements() {  // BaseManager::add()에서 호출됨
        for (int i = 0; i < ADD_SIZE; ++i) {
            var p = getNewPerson();
            map.put(p.getName(), p);
        }
    }

    public Person getNewPerson() {
        int id = getNewId();
        return new Person(getNewName(), id, getNewWeight(), getNewMarried(id), getNewAddress());
    }
}   // ch7_2: PMbyMap class

abstract class CollectionsMenu { // ch7_1
    public void run() {
        String menuStr =
                "======================= Collections Menu =======================\n" +
                        "= 0.exit 1.display 2.min 3.max 4.sort 5.reverse 6.binarySearch =\n" +
                        "================================================================\n";
        final int MENU_COUNT = 7;
        while (true) {
            int menuItem = UI.selectMenu(menuStr, MENU_COUNT);
            switch (menuItem) {
                case 1:
                    display();
                    break;
                case 2:
                    min();
                    break;
                case 3:
                    max();
                    break;
                case 4:
                    sort();
                    break;
                case 5:
                    reverse();
                    break;
                case 6:
                    binarySearch();
                    break;
                case 0:
                    return;
            }
        }
    }

    abstract public void display(); // Menu item 1

    abstract public void min(); // Menu item 2

    abstract public void max(); // Menu item 3

    abstract public void sort(); // Menu item 4

    abstract public void reverse(); // Menu item 5

    abstract public void binarySearch(); // Menu item 6
}

class CollectionsByList extends CollectionsMenu { // ch7_1
    // List는 인터페이스로 ArrayList, Vector, LinkedList의 수퍼 클래스이다.
    private List<Person> list; // list는 벡터라 생각하고 사용하면 된다.

    public CollectionsByList(List<Person> list) {
        this.list = list; // list는 PersonManager의 pVector가 업캐스팅된 것이다.
    }

    @Override
    public void display() { // Menu item 1
        for (int i = 0, count = list.size(); i < count; ++i)
            System.out.println("[" + i + "] " + list.get(i));
    }

    @Override
    public void min() { // Menu item 2
        if (list.isEmpty()) {
            return;
        }
        Collections.sort(list);
        System.out.println(list.get(0));
    }

    @Override
    public void max() { // Menu item 3
        if (list.isEmpty()) {
            return;
        }
        Collections.sort(list);
        System.out.println(list.get(list.size() - 1));
    }

    @Override
    public void sort() { // Menu item 4
        Collections.sort(list);
        display();
    }

    @Override
    public void reverse() { // Menu item 5
        Collections.reverse(list);
        display();
    }

    @Override
    public void binarySearch() {  // Menu item 6
        String name = UI.getNext("For binary search, it's needed to sort in advance. Name to search? ");
        Person p = new Person(name);
        int index;
        if ((index = Collections.binarySearch(list, p)) < 0) {
            System.out.println(name + " is NOT found.");
            return;
        }
        System.out.println(list.get(index));
        // 주의: 이진 검색하기 전에 먼저 list가 정렬이 되어 있어야 한다.
    }
}   // ch7_1: CollectionsByList class

abstract class CollectionsByMap extends CollectionsMenu { // ch7_2
    public void display(Map<String, Person> map) {
        PMbyMap.display(map);
    }

    public void searchMap(Map<String, Person> map) {
        String name = UI.getNext("Name to search? ");
        Person p = map.get(name);
        if (p != null) {
            System.out.println(p);
            return;
        }
        System.out.println(name + ": NOT found");
    }
}   // ch7_2: CollectionsByMap class

class CollectionsByTreeMap extends CollectionsByMap { // ch7_2
    private TreeMap<String, Person> map;

    public CollectionsByTreeMap(TreeMap<String, Person> map) {
        this.map = map;
    }

    public void display() {
        display(map);
    }

    public void min() {
        // 첫번째 entry가 키가 가장 작은 엔트리이다. 이름 순서상 가장 앞쪽 이름임
        Map.Entry<String, Person> e = map.firstEntry();
        if (e != null) System.out.println(e.getValue());
    }

    public void max() {
        // 마지막 entry가 키가 가장 큰 엔트리이다. 이름 순서상 가장 뒤쪽 이름임
        Map.Entry<String, Person> e = map.lastEntry();
        if (e != null) System.out.println(e.getValue());
    }

    public void sort() {
        display();
    } // 키가 이미 정렬되어 있으므로 바로 보여 줌

    // descendingMap()을 통해 키의 역순으로 된 map를 구할 수 있음
    public void reverse() {
        display(map.descendingMap());
    }

    public void binarySearch() {
        searchMap(map);
    } // 맵에서 바로 검색함

}   // ch7_2: CollectionsByTreeMap class

class CollectionsByHashMap extends CollectionsByMap { // ch7_2
    private HashMap<String, Person> map;

    public CollectionsByHashMap(HashMap<String, Person> map) {
        this.map = map;
    }

    public void display() {
        display(map);
    }

    public void min() {
        if (map.isEmpty()) {
            return;
        }

        System.out.println(map.get(Collections.min(map.keySet())));
    }

    public void max() {
        if (map.isEmpty()) {
            return;
        }
        System.out.println(map.get(Collections.max(map.keySet())));
    }

    public void sort() {
        // map의 keySet을 이용하여 벡터를 생성함
        var keyList = new Vector<String>(map.keySet());
        Collections.sort(keyList);
        for (var key : keyList) {
            System.out.println(map.get(key));
        }
    }

    public void reverse() {
        var keyList = new Vector<String>(map.keySet());
        Collections.reverse(keyList);
        for (var key : keyList) {
            System.out.println(map.get(key));
        }
    }

    public void binarySearch() {
        searchMap(map);
    }  // 맵에서 바로 검색함

}   // ch7_2: CollectionsByHashMap class

class MyVector<E> {
    static final int DEFAULT_SIZE = 10;

    private Object[] persons; // Person 객체 참조들의 배열, 즉 배열에 저장된 값이 Person 객체의 주소이다.
    private int count;        // persons 배열에 현재 삽입된 객체의 개수

    public MyVector() {
        this(DEFAULT_SIZE);
    }

    public MyVector(int capacity) {
        count = 0;
        persons = new Object[capacity];
    }

    @SuppressWarnings("unchecked")
    public E get(int index) {
        return (E) persons[index];
    }

    @SuppressWarnings("unchecked")
    public E set(int index, E p) {
        E a = (E) persons[index];
        persons[index] = p;
        return a;
    }

    public int capacity() {
        return persons.length;
    }

    public void clear() {
        count = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    void remove(int index) {
        for (int i = index; i < count; i++) {
            if (i + 1 == count) break;
            persons[i] = persons[i + 1];
        }
        count--;
    }

    public void add(E p) {
        if (count >= persons.length)
            extend_capacity();
        persons[count++] = p;
    }

    public void add(int index, E p) {
        for (int i = count; i >= index; i--) {
            if (count >= persons.length) {
                extend_capacity();
            }
            persons[i + 1] = persons[i];
        }
        count++;
        persons[index] = p;
    }

    public void extend_capacity() {
        int personsLength = persons.length;
        Object[] tmp = new Object[personsLength * 2];
        System.arraycopy(persons, 0, tmp, 0, personsLength);
        persons = new Object[tmp.length];
        System.arraycopy(tmp, 0, persons, 0, personsLength);
    }
}

class MyVectorTest extends BaseManager // ch7_3
{
    private MyVector<String> nameVct;    // Person::name 멤버들을 저장하는 벡터
    private MyVector<Integer> idVct;      // Person::id 멤버들을 저장하는 벡터
    private MyVector<Double> weightVct;  // Person::weight 멤버들을 저장하는 벡터
    private MyVector<Boolean> marriedVct; // Person::married 멤버들을 저장하는 벡터
    private MyVector<String> addressVct; // Person::address 멤버들을 저장하는 벡터

    public MyVectorTest() {
        nameVct = new MyVector<>();
        idVct = new MyVector<>();
        weightVct = new MyVector<>();
        marriedVct = new MyVector<>();
        addressVct = new MyVector<>();

        // 아래 add()는 각 벡터에 ADD_SIZE개의 원소를 자동 삽입함
        add();  // BaseManager의 멤버 함수임; 이 함수는 다시 아래의 addElements()를 호출함
    }

    public void displayPerson(int i) { // 한 사람의 정보를 출력함
        System.out.println("[" + i + "] " + nameVct.get(i) + " " + idVct.get(i) +
                " " + weightVct.get(i) + " " + marriedVct.get(i) + " :" + addressVct.get(i) + ":");
    }

    public void display() { // Menu item 1: 모든 사람들의 정보를 출력함
        for (int i = 0, count = idVct.size(); i < count; ++i)
            displayPerson(i);
    }

    public void replace() { // Menu item 4
        // 각 벡터의 총 원소 중 초반의 REPLACE_SIZE개의 사람 정보를 모두 수정함
        int count = Math.min(REPLACE_SIZE, idVct.size());
        for (int i = 0; i < count; ++i) {
            nameVct.set(i, getNewName());
            idVct.set(i, getNewId());
            weightVct.set(i, getNewWeight());
            marriedVct.set(i, getNewMarried(idVct.get(i)));
            addressVct.set(i, getNewAddress());
        }
        display();
    }

    public void remove() { // Menu item 5
        if (idVct.size() == 0) {
            System.out.println("no entry to remove");
            return;
        }
        int index = UI.getIndex("index to delete? ", idVct.size());
        nameVct.remove(index);
        idVct.remove(index);
        weightVct.remove(index);
        marriedVct.remove(index);
        addressVct.remove(index);
        display();
    }

    public void find() { // Menu item 6
        String name = UI.getNext("name to find? ");
        for (int i = 0; i < nameVct.size(); i++) {
            if (name.equals(nameVct.get(i))) {
                System.out.println("[" + i + "] " + nameVct.get(i) + " " + idVct.get(i) + " " + weightVct.get(i) + " " + marriedVct.get(i) + " :" + addressVct.get(i) + ":");
                return;
            }
        }
        System.out.println(name + ": NOT found");
    }

    public void collections() { // Menu item 7
        System.out.println("not supported by MyVector");
    }

    public void clearAllElements() {  // BaseManager::clear()에 의해 호출됨
        nameVct.clear();
        idVct.clear();
        weightVct.clear();
        marriedVct.clear();
        addressVct.clear();
    }

    public void addElements() {   // BaseManager::add()에 의해 호출됨
        // ADD_SIZE개의 Person 정보를 자동 생성하여 별개의 벡터에 보관함
        for (int i = 0; i < ADD_SIZE; ++i) {
            // 각각의 벡터의 끝에 자동 생성된 Person 멤버들을 추가한다.
            int id = getNewId();
            nameVct.add(getNewName());
            idVct.add(id);
            weightVct.add(getNewWeight());
            marriedVct.add(getNewMarried(id));
            addressVct.add(getNewAddress());
        }
    }
}   // ch7_3: MyVectorTest class

class FileManager { // ch8_1
    static final String HOME_DIR = "data"; // 상수 정의: 파일들을 생성할 폴더 이름

    FileManager(List list) {
        var dir = new File(HOME_DIR);
        if (!dir.exists()) dir.mkdir(); // 프로젝트 폴더에 "data" 폴더가 없을 경우 새로 생성
    }

    public void run() {
        String menuStr =
                "====================== File Management Menu =====================\n" +
                        "= 0.exit 1.fileList 2.delete 3.rename 4.addFiles 5.addDir       =\n" +
                        "= 6.deleteAll                                                   =\n" +
                        "=================================================================\n";
        final int MENU_COUNT = 7; // 상수 정의
        while (true) {
            int menuItem = UI.selectMenu(menuStr, MENU_COUNT);
            switch (menuItem) {
                case 1:
                    fileList();
                    break;
                case 2:
                    delete();
                    break;
                case 3:
                    rename();
                    break;
                case 4:
                    addFiles();
                    break;
                case 5:
                    addDir();
                    break;
                case 6:
                    deleteAll();
                    break;
                case 0:
                    return;
            }
        }
    }

    void printFileInfo(File f) {
        long t = f.lastModified();
//        t = 1700000000000L;  // 2023-11-15 오전 07:13; 자동 체크 때 사용할 예정임
        System.out.printf("%-20s %c %tF %tH:%tM %9d\n",
                f.getName(), f.isDirectory() ? 'D' : 'F', t, t, t, f.length());
    }

    File[] fileList() { // menu item 1
        var dir = new File(HOME_DIR);
        File files[] = dir.listFiles();
        if (files == null) return null; // HOME_DIR이 존재하지 않을 경우 null
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            System.out.printf("[%d] ", i);
            printFileInfo(f);
        }
        System.out.println("-----------------");
        System.out.println("[" + HOME_DIR + "] directory: " + files.length + " files");
        return files;
    }

    File selectFile(String preMsg, String postMsg) {
        File files[] = fileList();
        if (preMsg.length() != 0) preMsg += " ";
        while (true) {
            String msg = "index number of a " + preMsg + "file to " + postMsg + " [-1:stop]? ";
            int val = UI.getInt(msg);
            if (val >= 0 && val < files.length) {
                return files[val];
            } else if (val == -1) {
                return null;
            } else {
                System.out.println("invalid index number: " + val);
            }
        }
    }

    void delete() { // menu item 2
        File f;
        if ((f = selectFile("", "delete")) == null) return;
        f.delete();
        fileList();
    }

    void rename() { // menu item 3
        File src = selectFile("source", "rename");
        if (src == null) return; // 사용자가 파일 선택을 취소했을 경우
        String text = UI.getNext("target file name? ");
        var dst = new File(HOME_DIR + "/" + text);
        if (dst.exists()) {
            System.out.println(text + " already exists");
            return;
        }
        src.renameTo(dst);
        fileList();
    }

    void addFiles() { // menu item 4: 4개의 text 파일을 자동 생성함
        for (int i = 0, count = 0; count < 4; ++i, ++count) {
            var f = new File(HOME_DIR + "/t" + i + ".txt"); // 파일 이름이 t0부터 시작함
            if (f.exists()) {
                --count;
                continue;
            }  // 동일한 이름이 있을 경우 건너 뜀
            try {
                var fout = new PrintStream(f);
                for (int j = 0; j <= i; ++j)
                    fout.println("This is a text file to test File Management Menu.");
                fout.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        fileList();
    }

    void addDir() { // menu item 5: 2개의 디렉터리를 자동으로 생성함
        for (int i = 0, count = 0; count < 2; ++i, ++count) {
            var f = new File(HOME_DIR + "/d" + i);
            if (f.exists()) {
                --count;
                continue;
            }          // 동일한 이름이 있을 경우 건너 뜀
            f.mkdir();
        }
        fileList();
    }

    void deleteAll() { // menu item 6: 모든 파일 및 빈 디렉터리들을 삭제함
        var dir = new File(HOME_DIR);
        File files[] = dir.listFiles();
        if (files == null) return;
        for (int i = 0; i < files.length; i++) {
            File f = files[i];
            f.delete();
        }
        System.out.println("-----------------");
        System.out.println("[" + HOME_DIR + "] directory: " + 0 + " files");
    }
}  // ch8_1: FileManager class

class Inheritance {
    Student s;
    Worker w;

    public Inheritance() {
        s = new Student("s1", 1, 65.4, true, "Jongno-gu Seoul", "Physics", 3.8, 1);
        w = new Worker("w1", 3, 33.3, false, "Kangnam-gu Seoul", "Samsung", "Director");
    }

    public void run() {
        String menuStr =
                "***** Inheritance Menu ******\n" +
                        "* 0.exit 1.Student 2.Worker *\n" +
                        "*****************************\n";
        final int MENU_COUNT = 3; // 상수 정의
        while (true) {
            int menuItem = UI.selectMenu(menuStr, MENU_COUNT);
            switch (menuItem) {
                case 1:
                    student();
                    break;
                case 2:
                    worker();
                    break;
                case 0:
                    return;
            }
        }
    }

    void compare(Person p1, Person p2) {
        System.out.println("p1: " + p1);
        System.out.println("p2: " + p2);
        System.out.println("p1.equals(p2) : " + p1.equals(p2));
        System.out.println("--------------------");
    }

    Person whatAreYouDoing(Person p) {
        p.whatAreYouDoing();
        return p;
    }

    void input(Person p, String msg) {
        System.out.print("input " + msg + ": ");
        p.input(UI.scan);
        if (UI.echo_input) System.out.println(p); // 자동체크에서 사용됨
    }

    Person clone(Person p) {
        Person c = p.clone();
        return c;
    }

    void assign(Person d, Person s) {
        d.assign(s); // s를 d에 복사
    }

    Person newInput(Boolean isStudent, String msg) {
        Person p = null;
        System.out.print("input new " + msg + ": ");
        if (isStudent)
            p = new Student(UI.scan);
        else
            p = new Worker(UI.scan);
        if (UI.echo_input) System.out.println(p); // 자동체크에서 사용됨
        return p;
    }

    void student() {
        var s1 = new Student(s);
        var s2 = new Student(s1);
        System.out.println("--------------------");
        s2.set("s2");
        compare(s1, s2); // 업캐스팅
        s2.set(s1.getName());
        s2.setGPA(s2.getGPA() - 1.0);
        compare(s1, s2);

        s2.setDepartment(s1.getDepartment() + "-Electronics");
        compare(s1, s2);

        s2.setDepartment(s1.getDepartment());
        s2.setYear(s1.getYear() + 1);
        compare(s1, s2);

        s2.setYear(s1.getYear());
        compare(s1, s2);

        s2.set("s2");
        Student s3 = (Student) whatAreYouDoing(s2); // 함수호출:다운캐스팅 & 리턴:업캐스팅
        System.out.println();
        s3.whatAreYouDoing();

        s3 = (Student) clone(s2);
        System.out.println("s3: " + s3);
        System.out.println("--------------------");

        System.out.println("s2: " + s2);
        s1 = new Student("", 0, 0.0, false, "", "", 0.0, 0);
        assign(s2, s1); // (destination, source): destination = source
        System.out.println("s2: " + s2);
        System.out.println("--------------------");

        input(s2, "student"); // s2 1 56.9 false :Gangnam-gu Seoul: Physics 2.0 1
        System.out.println("s2: " + s2);
        System.out.println("--------------------");

        Student s4 = (Student) newInput(true, "student");
        // s4 1 56.9 false :Gangnam-gu Seoul: Physics 2.0 1
        System.out.println("s4: " + s4);
    }

    void worker() {
        var w1 = new Worker(w);
        var w2 = new Worker(w1);

        System.out.println("--------------------");
        w2.set("w2");
        compare(w1, w2); // 업캐스팅

        w2.set(w1.getName());
        w2.setCompany(w1.getCompany() + "-Hyundai");
        w2.setPosition(w1.getPosition());
        compare(w1, w2);
        w2.setCompany(w1.getCompany());
        w2.setPosition(w1.getPosition() + "-Manager");
        compare(w1, w2);
        w2.setPosition(w1.getPosition());
        compare(w1, w2);

        w2.set("w2");
        Worker w3 = (Worker) whatAreYouDoing(w2);  // 다운캐스팅
        System.out.println();
        w3.whatAreYouDoing();

        w3 = (Worker) clone(w2);
        System.out.println("w3    : " + w3);
        System.out.println("--------------------");

        System.out.println("w2: " + w2);
        w1 = new Worker("", 0, 0.0, false, "", "", "");
        assign(w2, w1); // (destination, source): destination = source
        System.out.println("w2: " + w2);
        System.out.println("--------------------");

        input(w2, "worker"); // w2 3 44.4 true :Jongno-gu Seoul: Samsung Director
        System.out.println("w2: " + w2);
        System.out.println("--------------------");

        Worker w4 = (Worker) newInput(false, "worker");
        // w4 3 44.4 true :Jongno-gu Seoul: Samsung Director
        System.out.println("w4: " + w4);
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
        int[] arr;
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
        String[] MJBarray = {"m", "j", "b"}; // 묵(m) 찌(j) 빠(b) 문자열을 가진 배열
        System.out.println("Start the MUK-JJI-BBA game.");
        // 난수 발생기
        random = new Random(UI.getInt("seed for random number? "));
        // 누가 우선권을 가졌는지 저장하고 있음, USER:사용자 우선권, COMPUTER:computer 우선권
        int priority = USER;
        String[] priStr = {"USER", "COMPUTER"}; // 우선권을 화면에 출력할 때 사용할 문자열
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
        System.out.println(String.valueOf(i1) + i2 + i3);
        System.out.println(i1 + i2 + i3 + " " + i1 + i2 + i3);

        boolean b = true;
        double d = 1.2;

        System.out.println(b + " " + !b + " " + d);
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
        String address = UI.getNextLine("address? ");// "address? " 출력 후 한줄 전체 입력받음
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