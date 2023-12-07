package practice7_3;

import java.util.*;
import java.io.*;

class Student {
    public int id;
    public String name;

    public Student(int id, String name) {
        this.name = name;
        this.id = id;
    }

    @Override
    public String toString() {
        return id + "(" + name + ")";
    }
}

class StudentManager {
    // TreeMap은 HashMap과 사용방법이 똑 같다.

    TreeMap<Integer, Student> map; // 키 값은 학생 id임
    // 주의: 위 map 외에 다른 멤버는 선언하지 마라.

    // id 값을 가진 학생 원소를 멤버 map에서 제거함
    public void remove(int id) {
        if (map != null)
            map.remove(id);
    }

    // 멤버 map을 리턴함
    public Map<Integer, Student> getMap() {
        return map;
    }

    // 멤버 map만 초기화하면 된다.
    public StudentManager() {
        map = new TreeMap<Integer, Student>();
    } // 문제 1-1

    // 아래 List<>는 ArrayList<>, Vector<>, LinkedList<>의 슈퍼 클래스(인터페이스)인데,
    // List<> 참조자는 간단하게 Vector 객체라 생각하면 되고
    // Vector의 모든 메소드를 리스트에 적용하여 호출할 수 있다.

    // List<Student>에 저장된 모든 학생 객체들을 map에 삽입
    // 이때 키 값으로 학생 id를 사용하면 됨
    public void addList(List<Student> list) {
        for (int i = 0; i < list.size(); i++) {
            map.put(list.get(i).id, list.get(i));
        }
    } // 문제 1-2

    // HashMap과 TreeMap의 차이점은
    // TreeMap의 경우 Key 값들이 tree에 의해 미리 정렬되어 있다는 것이다.
    // 따라서 키 값들의 집합(keySet())을 구할 경우 이들은 키 값 순서로 이미 정렬되어 있다.
    //
    // 반환될 List는 Vector, ArrayList, LinkedList 중 하나 선택하여 리스트를 생성한 후
    // TreeMap에 저장된 모든 키 값을 순서대로 앞서 생성된 리스트 객체에 모두 저장한 후
    // 이 리스트의 원소들을 키 값 크기의 [역순]으로 재배치한 후 리스트를 반환하면 됨
    public List<Student> getReverseList() { // 문제 1-3
        var list = new Vector<Student>();
        for (var k : map.keySet()) {
            list.add(map.get(k));
        }
        Collections.reverse(list);
        return list;
    }

    // 새로운 TreeMap<학생이름, 학생객체> nameMap 객체를 생성한 후
    // nameList에 저장된 각각의 학생에 대해
    // 각 학생 이름 name을 멤버 map에서 찾고 (map 내의 모든 학생의 이름을 일일이 name과 비교해야 함)
    // 해당 이름이 존재할 경우 해당 학생 객체를 nameMap에 추가한다. (이때 키 값으로 학생 이름 사용)
    // 마지막에 nameMap을 반환
    public TreeMap<String, Student> getNameMap(List<String> nameList) { // 문제 1-4
        var nameMap = new TreeMap<String, Student>();
        for (int i = 0; i < nameList.size(); i++) {
            for (var a : map.keySet()) {
                if (nameList.get(i).equals(map.get(a).name)) {
                    nameMap.put(nameList.get(i), map.get(a));
                    break;
                }
            }
        }
        return nameMap;
    }
}

class Executor {
    public static Scanner scanner = new Scanner(System.in);
    public static boolean autoCheck = false;
    public static int seed;

    // 아래 List<>는 ArrayList<>, Vector<>, LinkedList<>의 슈퍼 클래스(인터페이스)인데,
    // List<> 참조자는 간단하게 Vector 객체라 생각하면
    // Vector의 모든 메소드를 리스트에 적용하여 호출할 수 있다.

    private static List<Integer> ids;
    private static List<String> names;
    private static List<Student> studentList;
    private static StudentManager sm;

    // 학생 리스트(studentList)를 자동 생성하고 StudentManager sm 객체 생성함
    public static void init() {
        ids = new ArrayList<Integer>();
        ids.add(11);
        ids.add(14);
        ids.add(13);
        ids.add(15);
        ids.add(12);
        names = new ArrayList<String>();
        names.add("bob");
        names.add("sandy");
        names.add("alice");
        names.add("nana");
        names.add("tom");

        Random rnd = new Random(seed);
        studentList = new ArrayList<Student>();
        boolean check[] = new boolean[ids.size()];
        for (int i = 0; i < ids.size(); ) {
            int idx = rnd.nextInt(ids.size());
            if (check[idx])
                continue;
            check[idx] = true;
            studentList.add(new Student(ids.get(idx) + seed, names.get(idx)));
            i++;
        }
        // 학생 List 출력: 학생id(학생이름)
        System.out.println("Student List:\t\t" + studentList);
        sm = new StudentManager(); // 문제 1-1 호출
    }

    // id 순서로 정렬된 TreeMap 출력
    public static void addList() {
        sm.addList(studentList); // 문제 1-2 호출
        System.out.println("Student Map:\t\t" + sm.getMap());
    }

    // id의 역순으로 배치된 학생 List 출력
    public static void reverseList() {
        System.out.println("Student List(reverse):\t" + sm.getReverseList()); // 문제 1-3 호출
    }

    // names 리스트의 각 이름으로 검색하여 찾은 학생들의 TreeMap 출력
    public static void nameMap() {
        sm.remove(studentList.get(0).id);
        sm.remove(studentList.get(1).id);
        System.out.println("Student Name Map:\t" + sm.getNameMap(names) + "\n");// 문제 1-4 호출
    }

    public static void run() {
        init(); // 문제 1-1 호출
        addList(); // 문제 1-2 호출
        reverseList(); // 문제 1-3 호출
        nameMap(); // 문제 1-4 호출
    }

    // 난수 발생을 위한 임의의 seed 값 하나를 입력 받음
    public static boolean getInput() {
        System.out.println("***********************");
        System.out.print("random numer seed? "); // 난수 발생을 위한 seed 값 입력 받음: -1이면 종료
        seed = scanner.nextInt();
        if (autoCheck)
            System.out.println(seed);
        System.out.println("***********************");
        return seed != -1;
    }

}

public class Main {
    public static void main(String[] args) {
        while (Executor.getInput()) // -1 입력한 경우, 프로그램 종료
            Executor.run();
        new AutoCheck(false);
    }
}

class AutoCheck {
    private ByteArrayOutputStream outStream;
    private Scanner scanner;
    private PrintStream out;
    private PrintStream outBackup = System.out;
    private Scanner scannerBackup = Executor.scanner;
    private Diff diff;

    public AutoCheck(boolean debug) {
        System.out.println();
        Executor.autoCheck = true;
        diff = new Diff(debug);
        InputStream in = new ByteArrayInputStream(InputOutput.getInput());
        scanner = new Scanner(in);
        outStream = new ByteArrayOutputStream();
        out = new PrintStream(outStream);
        simaulate();
        out.close();
    }

    private void simaulate() {
        int scores[] = {0};
        setStreams();
        while (Executor.getInput()) {
            Executor.run();
            scores[0] += evaluate();
            if (InputOutput.eof())
                break;
        }
        restoreStreams();
        // System.out.println("score: "+scores[0]+" / "+InputOutput.getScore());
        boolean result = (scores[0] == InputOutput.getScore());
        System.out.println("auto check: " + (result ? "OK" : "X"));
    }

    private void setStreams() {
        Executor.scanner = scanner;
        outStream.reset();
        System.setOut(out);
    }

    private void restoreStreams() {
        Executor.scanner = scannerBackup;
        System.setOut(outBackup);
    }

    private int evaluate() {
        restoreStreams();
        int score = InputOutput.getScore();
        String answer = new String(InputOutput.getAnswer());
        boolean error = diff.check(outStream.toString(), answer);
        setStreams();
        InputOutput.next();
        return error ? 0 : score;
    }
}

class Diff {
    private String userLines[], correctLines[];
    private String userLine, correctLine;
    private String userTokens[], correctTokens[];
    private int userLnNum, correctLnNum;
    private int userIdx, correctIdx;
    private int lastErrorLine;
    private boolean debug;

    public Diff(boolean debug) {
        this.debug = debug;
    }

    private void reset() {
        userLine = "";
        correctLine = "";
        userTokens = new String[0];
        correctTokens = new String[0];
        userLnNum = correctLnNum = userIdx = correctIdx = lastErrorLine = 0;
    }

    public boolean check(String output, String correctString) { // true: error, false: ok
        if (printOutput) {
            printUserOutput(output);
            return false;
        }
        reset();
        userLines = output.split("\n");
        // for (int i = 0; i < userLines.length; i++)
        // userLines[i] = userLines[i].stripTrailing();
        correctLines = correctString.split("\n");
        return diff();
    }

    private boolean diff() {
        String userToken, correctToken;
        boolean err = false;
        while (true) {
            userToken = getUserNextToken();
            correctToken = getCorrectNextToken();
            if ((userToken == null) || (correctToken == null))
                break;
            if (!userToken.equals(correctToken)) {
                err = true;
                if (debug)
                    printDebugMessage(userToken, correctToken);
                getUserNextLine();
                getCorrectNextLine();
            }
        }
        if ((userToken == null) && (correctToken == null))
            return err;

        if (debug && userToken != null) {
            System.out.println("User output has more lines than correct answer as follows.");
            printRemainingLines(userLines, "user line #", userLnNum - 1, userLine);
        } else if (debug && correctToken != null) {
            System.out.println("Correct answer has more lines than user output as follows.");
            printRemainingLines(correctLines, "correct line #", correctLnNum - 1, correctLine);
        }
        return true;
    }

    private void printDebugMessage(String userToken, String correctToken) {
        for (int i = lastErrorLine; i < userLnNum; i++)
            System.out.println(userLines[i]);
        lastErrorLine = userLnNum;
        System.out.println(correctLine + " [correct line]");
        System.out
                .println("User output(\"" + userToken + "\") is not equal to correct output(\"" + correctToken + "\")");
        System.out.print("continue[y/n]? ");
        String next = Executor.scanner.next();
        if (!next.equals("y") && !next.equals("Y")) {
            System.out.println("Good bye.");
            System.exit(0);
        }
    }

    private String getUserNextToken() {
        if (userLine == null)
            return null;
        if (userIdx == userTokens.length) {
            if (getUserNextLine() == null)
                return null;
        }
        return userTokens[userIdx++];
    }

    private String getCorrectNextToken() {
        if (correctLine == null)
            return null;
        if (correctIdx == correctTokens.length) {
            if (getCorrectNextLine() == null)
                return null;
        }
        return correctTokens[correctIdx++];
    }

    private String getUserNextLine() {
        for (int idx = -1; idx != 0; userLnNum++) {
            if (userLnNum == userLines.length)
                return (userLine = null);
            userLine = userLines[userLnNum].trim();
            if (userLine.equals(""))
                continue;
            userTokens = userLine.split("\\s");
            if (userTokens.length == 0)
                continue;
            idx = 0;
        }
        userIdx = 0;
        return userLine;
    }

    private String getCorrectNextLine() {
        for (correctIdx = -1; correctIdx != 0; correctLnNum++) {
            if (correctLnNum == correctLines.length)
                return (correctLine = null);
            correctLine = correctLines[correctLnNum].trim();
            if (correctLine.equals(""))
                continue;
            correctTokens = correctLine.split("\\s");
            if (correctTokens.length == 0)
                continue;
            correctIdx = 0;
        }
        return correctLine;
    }

    private boolean printOutput = false;

    private void printUserOutput(String output) {
        byte bytes[] = output.getBytes();
        for (int i = 0; i < bytes.length; ) {
            System.out.print("        ");
            for (int cnt = 0; cnt < 20 && i < bytes.length; cnt++, ++i)
                System.out.printf("%3d,", bytes[i]);
            System.out.println();
        }
        System.out.println("    }, {");
        // System.out.println(output);
    }

    private void printRemainingLines(String lines[], String msg, int lnNum, String ln) {
        System.out.println(msg + ":" + (++lnNum) + ":" + ln);
        for (; lnNum < lines.length; lnNum++)
            System.out.println(msg + ":" + (lnNum + 1) + ":" + lines[lnNum]);
    }

}

class InputOutput {
    private static int answerIdx = 0;

    public static int getScore() {
        return scores[answerIdx];
    }

    public static byte[] getInput() {
        return input.getBytes();
    }

    public static byte[] getAnswer() {
        return answers[answerIdx];
    }

    public static void next() {
        answerIdx++;
    }

    public static boolean eof() {
        return answerIdx >= answers.length;
    }

    private static int scores[] = {25, 25, 50,};
    private static String input = "111 222 -1";
    private static byte answers[][] = {{42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42,
            42, 42, 42, 42, 13, 10, 114, 97, 110, 100, 111, 109, 32, 110, 117, 109, 101, 114, 32, 115, 101, 101, 100,
            63, 32, 49, 49, 49, 13, 10, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42,
            42, 42, 42, 13, 10, 83, 116, 117, 100, 101, 110, 116, 32, 76, 105, 115, 116, 58, 9, 9, 91, 49, 50, 54, 40,
            110, 97, 110, 97, 41, 44, 32, 49, 50, 50, 40, 98, 111, 98, 41, 44, 32, 49, 50, 52, 40, 97, 108, 105, 99,
            101, 41, 44, 32, 49, 50, 51, 40, 116, 111, 109, 41, 44, 32, 49, 50, 53, 40, 115, 97, 110, 100, 121, 41, 93,
            13, 10, 83, 116, 117, 100, 101, 110, 116, 32, 77, 97, 112, 58, 9, 9, 123, 49, 50, 50, 61, 49, 50, 50, 40,
            98, 111, 98, 41, 44, 32, 49, 50, 51, 61, 49, 50, 51, 40, 116, 111, 109, 41, 44, 32, 49, 50, 52, 61, 49, 50,
            52, 40, 97, 108, 105, 99, 101, 41, 44, 32, 49, 50, 53, 61, 49, 50, 53, 40, 115, 97, 110, 100, 121, 41, 44,
            32, 49, 50, 54, 61, 49, 50, 54, 40, 110, 97, 110, 97, 41, 125, 13, 10, 83, 116, 117, 100, 101, 110, 116, 32,
            76, 105, 115, 116, 40, 114, 101, 118, 101, 114, 115, 101, 41, 58, 9, 91, 49, 50, 54, 40, 110, 97, 110, 97,
            41, 44, 32, 49, 50, 53, 40, 115, 97, 110, 100, 121, 41, 44, 32, 49, 50, 52, 40, 97, 108, 105, 99, 101, 41,
            44, 32, 49, 50, 51, 40, 116, 111, 109, 41, 44, 32, 49, 50, 50, 40, 98, 111, 98, 41, 93, 13, 10, 83, 116,
            117, 100, 101, 110, 116, 32, 78, 97, 109, 101, 32, 77, 97, 112, 58, 9, 123, 97, 108, 105, 99, 101, 61, 49,
            50, 52, 40, 97, 108, 105, 99, 101, 41, 44, 32, 115, 97, 110, 100, 121, 61, 49, 50, 53, 40, 115, 97, 110,
            100, 121, 41, 44, 32, 116, 111, 109, 61, 49, 50, 51, 40, 116, 111, 109, 41, 125, 10, 13, 10,},
            {42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 13, 10, 114,
                    97, 110, 100, 111, 109, 32, 110, 117, 109, 101, 114, 32, 115, 101, 101, 100, 63, 32, 50, 50, 50, 13,
                    10, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 13,
                    10, 83, 116, 117, 100, 101, 110, 116, 32, 76, 105, 115, 116, 58, 9, 9, 91, 50, 51, 54, 40, 115, 97,
                    110, 100, 121, 41, 44, 32, 50, 51, 53, 40, 97, 108, 105, 99, 101, 41, 44, 32, 50, 51, 55, 40, 110,
                    97, 110, 97, 41, 44, 32, 50, 51, 52, 40, 116, 111, 109, 41, 44, 32, 50, 51, 51, 40, 98, 111, 98, 41,
                    93, 13, 10, 83, 116, 117, 100, 101, 110, 116, 32, 77, 97, 112, 58, 9, 9, 123, 50, 51, 51, 61, 50,
                    51, 51, 40, 98, 111, 98, 41, 44, 32, 50, 51, 52, 61, 50, 51, 52, 40, 116, 111, 109, 41, 44, 32, 50,
                    51, 53, 61, 50, 51, 53, 40, 97, 108, 105, 99, 101, 41, 44, 32, 50, 51, 54, 61, 50, 51, 54, 40, 115,
                    97, 110, 100, 121, 41, 44, 32, 50, 51, 55, 61, 50, 51, 55, 40, 110, 97, 110, 97, 41, 125, 13, 10,
                    83, 116, 117, 100, 101, 110, 116, 32, 76, 105, 115, 116, 40, 114, 101, 118, 101, 114, 115, 101, 41,
                    58, 9, 91, 50, 51, 55, 40, 110, 97, 110, 97, 41, 44, 32, 50, 51, 54, 40, 115, 97, 110, 100, 121, 41,
                    44, 32, 50, 51, 53, 40, 97, 108, 105, 99, 101, 41, 44, 32, 50, 51, 52, 40, 116, 111, 109, 41, 44,
                    32, 50, 51, 51, 40, 98, 111, 98, 41, 93, 13, 10, 83, 116, 117, 100, 101, 110, 116, 32, 78, 97, 109,
                    101, 32, 77, 97, 112, 58, 9, 123, 98, 111, 98, 61, 50, 51, 51, 40, 98, 111, 98, 41, 44, 32, 110, 97,
                    110, 97, 61, 50, 51, 55, 40, 110, 97, 110, 97, 41, 44, 32, 116, 111, 109, 61, 50, 51, 52, 40, 116,
                    111, 109, 41, 125, 10, 13, 10,},};
}