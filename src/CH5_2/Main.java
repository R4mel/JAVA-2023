package CH5_2;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        //--------------------------------
        // AutoCheck(chk, trace)
        // chk: 1(자동 오류 체크), 0(키보드에서 직접 입력하여 프로그램 실행)
        // trace: true(오류발생한 곳 출력), false(단순히 O, X만 표시)
        //--------------------------------
//        int chk = 1; if (chk != 0) new AutoCheck(chk, true).run(); else
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
                        "* 2.ch2 3.ch3 4.ch5      *\n" +
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
                case 4:
                    new Inheritance().run();
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
    private String passwd = ""; // 비밀번호

    // 생성자 함수들
    public Person(String name, int id, double weight, boolean married, String address) {
        set(name, "", id, weight, married, address);
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

    public void set(String name, String passwd, int id, double weight, boolean married, String address) {
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

    public void setPasswd(String passwd) {
        this.passwd = passwd;
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
        set(user.getName(), user.getPasswd(), user.getId(), user.getWeight(), user.getMarried(), user.getAddress());
    }

    // print(), clone(), whatAreYouDoing(), equals(), input() 함수
    public boolean equals(Person user) {
        return (user.getName() == getName() && user.getId() == getId());
    }

    void print() {
        printMembers();
    }

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
        set(name, "", id, weight, married, address);
    }

    private void printMembers() {
        System.out.print(name + " " + id + " " + weight + " " + married + " :" + address + ": ");
    }
}

class Student extends Person {
    private String department = ""; // 학과
    private double GPA;        // 평균평점
    private int year;          // 학년

    public Student(String name, int id, double weight, boolean married, String address, String department, double GPA, int year) {
        super(name, id, weight, married, address);
        // TODO: 수퍼(부모)클래스의 생성자를 호출하여 수퍼 클래스 멤버들을 초기화하라.
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
    @Override
    public void print() {
        super.print();
        printMembers();
    }

    @Override
    public boolean equals(Person p) {
        Student s = (Student) p;
        return (super.equals(s) && s.getDepartment() == getDepartment() && s.getYear() == getYear());
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
    private void printMembers() {
        System.out.print(department + " " + GPA + " " + year);
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
    public boolean equals(Person p) {
        Worker s = (Worker) p;
        return (super.equals(s) && s.getCompany() == getCompany() && s.getPosition() == getPosition());
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

    @Override
    public void print() {
        super.print();
        printMembers();
    }

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

    private void printMembers() {
        System.out.print(company + " " + position);
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

class CurrentUser {
    Person user;

    CurrentUser(Person user) {
        this.user = user;
    }

    public void run() {
        String menuStr =
                "++++++++++++++++++++++ Current User Menu ++++++++++++++++++++++++\n" +
                        "+ 0.logout 1.display 2.getter 3.setter 4.copy 5.whatAreYouDoing +\n" +
                        "+ 6.isSame 7.update 8.chPasswd(4_2)                             +\n" +
                        "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++\n";
        final int MENU_COUNT = 9;
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

class VectorPerson {
    static final int DEFAULT_SIZE = 10;

    private Person[] persons; // Person 객체 참조들의 배열, 즉 배열에 저장된 값이 Person 객체의 주소이다.
    private int count;        // persons 배열에 현재 삽입된 객체의 개수

    public VectorPerson() {
        this(DEFAULT_SIZE);
    }

    public VectorPerson(int capacity) {
        count = 0; // persons 배열에 현재 삽입된 객체의 개수는 0
//        System.out.println("VectorPerson::VectorPerson(" + capacity + ")");
        persons = new Person[capacity]; // 객체 참조 배열 할당
    }

    // persons[index]의 값을 반환
    public Person get(int index) {
        return persons[index];
    }

    // 할당 받은 persons 배열의 전체 길이를 반환함 (count가 아님)
    public int capacity() {
        return persons.length;
    }

    // persons 배열에 현재 삽입된 객체의 개수를 0으로 설정
    public void clear() {
        count = 0;
    }

    // 현재 삽입된 객체 참조가 하나도 없으면 true, 있으면 false를 반환한다.
    // if 문장을 사용하지 말고 한 문장(return 비교연산자)으로 완셩할 것
    public boolean isEmpty() {
        return count == 0;
    }

    // 현재 삽입된 객체의 개수를 반환
    public int size() {
        return count;
    }

    // index 위치의 객체 p를 삭제한다. 즉, index+1부터 끝까지 객체들을 한칸씩 왼쪽으로 밀어 주어야 한다.
    // 자바에는 객체를 삭제하는 delete 명령어가 없다. 따라서 객체를 별도로 삭제할 필요는 없고 무시하라.
    void remove(int index) {
        for (int i = index; i < count; i++) {
            persons[i] = persons[i + 1];
        }
        count--;
    }

    // persons 배열에 마지막 삽입된 원소 뒤에 새로운 원소 p를 삽입하고 현재 삽입된 객체 개수 증가
    // persons[]의 배열 크기가 작으면 extend_capacity()를 호출하여 먼저 배열을 확장한다.
    public void add(Person p) {
        if (count >= persons.length)
            extend_capacity();
        persons[count++] = p;
    }

    // 먼저 index부터 끝까지 객체들을 한칸씩 뒤로 밀어 준 후 index 위치에 객체 p를 삽입한다.
    // persons[]의 배열 크기가 작으면 extend_capacity()를 호출하여 먼저 배열을 확장한다.
    public void add(int index, Person p) {
        for (int i = count; i >= index; i--) {
            if (count >= persons.length) {
                extend_capacity();
            }
            persons[i + 1] = persons[i];
        }
        count++;
        persons[index] = p;
    }

    // persons[]의 배열 크기를 두배로 확장한다.
    // 기존 persons 변수를 다른 배열 변수에 임시로 저장한 후
    // 현재의 두배 크기의 배열을 새로 할당 받아 persons에 저장한다.
    // 임시 변수에 있던 기존 값들을 모두 persons[]에 복사한다.
    public void extend_capacity() {
        int personsLength = persons.length;
        Person[] tmp = new Person[personsLength * 2];
        for (int i = 0; i < personsLength; i++) {
            tmp[i] = persons[i];
        }
        persons = new Person[tmp.length];
        for (int i = 0; i < personsLength; i++) {
            persons[i] = tmp[i];
        }
//        System.out.println("VectorPerson: capacity extended to " + persons.length);
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
        if (UI.echo_input) p.println(delimiter.equals("") ? "" : delimiter + " ");
        return p;
    }
}

class PersonManager {
    static int cpCount = 0;
    private VectorPerson pVector;
    private Factory factory;
    private Person array[];

    public PersonManager(Person array[], Factory factory) {
//        System.out.println("PersonManager(array[])");
        pVector = new VectorPerson();
        this.factory = factory;
        this.array = array;
        addArray();
        display();
    }

    public void run() {
        String menuStr =
                "=========== Person Management Menu ==========\n" +
                        "= 0.exit 1.display 2.clear 3.reset 4.remove =\n" +
                        "= 5.copy 6.append 7.insert 8.login          =\n" +
                        "=============================================\n";
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
                case 0:
                    return;
            }
        }
    }

    public void display() { // Menu item 1
        int count = pVector.size();
//        System.out.println("display(): count " + count);
        for (int i = 0; i < count; ++i) {
            System.out.print("[" + i + "] ");
            pVector.get(i).println();
        }
//        System.out.println("empty():" + pVector.isEmpty() + ", size():" + pVector.size()
//                + ", capacity():" + pVector.capacity());
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
        if (pVector.size() == 0) {
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

    /*
    5
    K p3 11 83.3 true :100 Dunsan-ro Seo-gu Daejeon:
    P p3 11 83.3 true :100 Dunsan-ro Seo-gu Daejeon:
    S s3 12 71.5 false :Gwangju Nam-gu Bongseon-dong 21: Computer 3.3 2
    W w3 13 65 true :Jong-ro 1-gil, Jongno-gu, Seoul: Kia CEO
    S s4 15 80 true :1001, Jungang-daero, Yeonje-gu, Busan: Biology 3.8 3
    W w4 16 77 false :Buk-ro 3, Kangdong-gu, Seoul: Naver Department-Head
    */
    // 아래 함수는 사용자로부터 새로 추가할 Person 객체의 수를 입력 받고 for문을 이용하여
    // 그 개수만큼의 Person 객체를 생성하고 인적정보를 입력받은 후 (factory.inputPerson()을 통해)
    // VectorPerson pVector의 맨 끝에 추가한다.
    /* append() 실행 시 아래 항목들을 복사해서 순서적으로 입력하면 편하게 인적정보를 입력할 수 있음
    3
    HongGilDong 0 71.5 false :Gwangju Nam-gu Bongseon-dong 21:
    LeeMongRyong 1 65 true :Jong-ro 1-gil, Jongno-gu, Seoul:
    LeeSoonShin 2 80 true :1001, Jungang-daero, Yeonje-gu, Busan:
    */
    public void append() { // Menu item 6
        int count = UI.getPosInt("number of persons to append? ");
        factory.printInputNotice(" " + Integer.toString(count), " to append");
        for (int i = 0; i < count; ++i) {
            Person p = factory.inputPerson(UI.scan); // 한 사람의 정보를 입력 받음
            if (p != null) pVector.add(p);
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
        if (p == null) return;
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
    private Person persons[] = {
            new Person("p0", 10, 70.0, false, "Gwangju Nam-gu Bongseon-dong 21"),
            new Person("p1", 11, 61.1, true, "Jong-ro 1-gil, Jongno-gu, Seoul"),
            new Person("p2", 12, 52.2, false, "1001, Jungang-daero, Yeonje-gu, Busan"),
            new Person("p3", 13, 83.3, true, "100 Dunsan-ro Seo-gu Daejeon"),
            new Person("p4", 14, 64.4, false, "88 Gongpyeong-ro, Jung-gu, Daegu"),
    };

    private Student students[] = {
            new Student("s1", 1, 65.4, true, "Jongno-gu Seoul", "Physics", 3.8, 1),
            new Student("s2", 2, 54.3, false, "Yeonje-gu Busan", "Electronics", 2.5, 4),
    };

    private Worker workers[] = {
            new Worker("w1", 3, 33.3, false, "Kangnam-gu Seoul", "Samsung", "Director"),
            new Worker("w2", 4, 44.4, true, "Dobong-gu Kwangju", "Hyundai", "Manager"),
    };

    private Person allPersons[] = {
            persons[0], persons[1], students[0], students[1], workers[0], workers[1],
    };

    public void run() {
//        System.out.println("PersonManager::run() starts");
        var pm = new PersonManager(allPersons, new Factory());
        pm.run();
//        System.out.println("PersonManager::run() returned");
    }
}

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
        p1.println("p1: ");
        p2.println("p2: ");
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
        if (UI.echo_input) p.println(); // 자동체크에서 사용됨
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
        if (UI.echo_input) p.println(); // 자동체크에서 사용됨
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
        s3.println("s3: ");
        System.out.println("--------------------");

        s2.println("s2: ");
        s1 = new Student("", 0, 0.0, false, "", "", 0.0, 0);
        assign(s2, s1); // (destination, source): destination = source
        s2.println("s2: ");
        System.out.println("--------------------");

        input(s2, "student"); // s2 1 56.9 false :Gangnam-gu Seoul: Physics 2.0 1
        s2.println("s2: ");
        System.out.println("--------------------");

        Student s4 = (Student) newInput(true, "student");
        // s4 1 56.9 false :Gangnam-gu Seoul: Physics 2.0 1
        s4.println("s4: ");
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
        w3.println("w3    : ");
        System.out.println("--------------------");

        w2.println("w2: ");
        w1 = new Worker("", 0, 0.0, false, "", "", "");
        assign(w2, w1); // (destination, source): destination = source
        w2.println("w2: ");
        System.out.println("--------------------");

        input(w2, "worker"); // w2 3 44.4 true :Jongno-gu Seoul: Samsung Director
        w2.println("w2: ");
        System.out.println("--------------------");

        Worker w4 = (Worker) newInput(false, "worker");
        // w4 3 44.4 true :Jongno-gu Seoul: Samsung Director
        w4.println("w4: ");
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