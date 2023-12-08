package practice7_4;

import java.util.*;
import java.io.*;

class GQueue<E> { // 문제 2-2
    private int front, rear, count;
    private Object que[];

    public GQueue() {
        front = rear = count = 0;
        que = new Object[10];
    }

    public boolean enque(E t) {
        if (count == 10)
            return false;
        que[rear] = t;
        rear = (rear + 1) % 10;
        count++;
        return true;
    }

    public E deque() {
        if (count == 0)
            return null;
        @SuppressWarnings("unchecked")
        E t = (E) que[front];
        front = (front + 1) % 10;
        count--;
        return t;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        if (count > 0)
            sb.append(que[front]);
        for (int i = 1; i < count; ++i)
            sb.append(", " + que[(front + i) % 10]);
        sb.append("]");
        return sb.toString();
    }
}

class GenericMethod<T> {
    // 문제 2-2, 문제 2-3
    static public <K, V> TreeMap<K, V> toTreeMap(List<K> keyList, GQueue<V> valueQ) {
        TreeMap<K, V> map = new TreeMap<>();
        for (int i = 0; i < keyList.size(); ++i) {
            V value = (V) valueQ.deque();
            if (value == null)
                break;
            else
                map.put(keyList.get(i), value);
        }
        return map;
    }

    static public <T> void swap(List<T> list, int i, int j) { // 문제 2-1
        T tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }
}

class exe {
    public static Scanner scanner = new Scanner(System.in);
    public static boolean autoCheck = false;
    public static int seed;
    private static List<Integer> ids;
    private static List<String> names;
    private static List<Integer> idList;
    private static List<String> nameList;
    private static GQueue<Integer> idQ;
    private static GQueue<String> nameQ; // 문제 2-2

    public static void init() {
        ids = new ArrayList<Integer>();
        ids.add(15);
        ids.add(14);
        ids.add(13);
        ids.add(12);
        ids.add(11);
        names = new ArrayList<String>();
        names.add("tom");
        names.add("sandy");
        names.add("nana");
        names.add("bob");
        names.add("alice");

        idList = new ArrayList<>(ids);
        nameList = new ArrayList<>(names);
        idQ = new GQueue<>(); // 문제 2-2
        nameQ = new GQueue<>(); // 문제 2-2
    }

    public static void genericMethodSwap() {
        Random rnd = new Random(seed);
        for (int i = 0; i < idList.size(); i++) {
            GenericMethod.swap(idList, i, rnd.nextInt(idList.size()));
            GenericMethod.swap(nameList, i, rnd.nextInt(nameList.size())); // 문제 2-1 호출
        }
        System.out.println("ID   List:\t" + idList);
        System.out.println("Name List:\t" + nameList);
        System.out.println();
    }

    public static void genericClassQueue() {
        for (int i = 0; i < ids.size(); ++i) {
            idQ.enque(idList.get(i));
            nameQ.enque(nameList.get(i)); // 문제 2-2
        }
        for (int i = 0; i < 2; ++i) // 큐의 앞쪽 두개 원소 삭제
            idQ.deque();
        for (int i = 0; i < 3; ++i) // 세개 원소 삭제 // 문제 2-2
            nameQ.deque(); // 문제 2-2
        System.out.println("ID   Queue:\t" + idQ);
        System.out.println("Name Queue:\t" + nameQ); // 문제 2-2
        System.out.println();
    }

    public static void genericMethodToTreeMap() {
        TreeMap<Integer, String> mapIdName = GenericMethod.toTreeMap(idList, nameQ);
        // // 문제 2-3
        TreeMap<String, Integer> mapNameId = GenericMethod.toTreeMap(nameList, idQ);
        System.out.println("<ID, Name> Map:\t" + mapIdName); // 문제 2-3
        System.out.println("<Name, ID> Map:\t" + mapNameId);
        System.out.println();
    }

    public static void run() {
        init();
        genericMethodSwap(); // 문제 2-1 호출
        genericClassQueue(); // 문제 2-2 호출
        genericMethodToTreeMap(); // 문제 2-3 호출
    }

    public static boolean getInput() {
        System.out.println("***********************");
        System.out.print("random number seed? ");
        seed = scanner.nextInt();
        if (autoCheck)
            System.out.println(seed);
        System.out.println("***********************");
        return seed != -1;
    }
}

public class Main {
    public static void main(String[] args) {
        while (exe.getInput())
            exe.run();
		new AutoCheck(false);
    }
}

class AutoCheck {
    private ByteArrayOutputStream outStream;
    private Scanner scanner;
    private PrintStream out;
    private PrintStream outBackup = System.out;
    private Scanner scannerBackup = exe.scanner;
    private Diff diff;

    public AutoCheck(boolean debug) {
        System.out.println();
        exe.autoCheck = true;
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
        while (exe.getInput()) {
            exe.run();
            scores[0] += evaluate();
            if (InputOutput.eof()) break;
        }
        restoreStreams();
        //System.out.println("score: "+scores[0]+" / "+InputOutput.getScore());
        boolean result = (scores[0] == InputOutput.getScore());
        System.out.println("auto check: " + (result ? "OK" : "X"));
    }

    private void setStreams() {
        exe.scanner = scanner;
        outStream.reset();
        System.setOut(out);
    }

    private void restoreStreams() {
        exe.scanner = scannerBackup;
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
        //for (int i = 0; i < userLines.length; i++)
        //    userLines[i] = userLines[i].stripTrailing();
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
                if (debug) printDebugMessage(userToken, correctToken);
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
        System.out.println("User output(\"" + userToken + "\") is not equal to correct output(\"" + correctToken + "\")");
        System.out.print("continue[y/n]? ");
        String next = exe.scanner.next();
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
        //System.out.println(output);
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
    private static String input = "11 22 -1";
    private static byte answers[][] = {
            {
                    42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42,
                    42, 42, 42, 42, 42, 13, 10, 114, 97, 110, 100, 111, 109, 32, 110, 117, 109, 98,
                    101, 114, 32, 115, 101, 101, 100, 63, 32, 49, 49, 13, 10, 42, 42, 42, 42, 42,
                    42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42,
                    13, 10, 73, 68, 32, 32, 32, 76, 105, 115, 116, 58, 9, 91, 49, 51, 44, 32,
                    49, 52, 44, 32, 49, 53, 44, 32, 49, 49, 44, 32, 49, 50, 93, 13, 10, 78,
                    97, 109, 101, 32, 76, 105, 115, 116, 58, 9, 91, 115, 97, 110, 100, 121, 44, 32,
                    98, 111, 98, 44, 32, 97, 108, 105, 99, 101, 44, 32, 116, 111, 109, 44, 32, 110,
                    97, 110, 97, 93, 13, 10, 13, 10, 73, 68, 32, 32, 32, 81, 117, 101, 117, 101,
                    58, 9, 91, 49, 53, 44, 32, 49, 49, 44, 32, 49, 50, 93, 13, 10, 78, 97,
                    109, 101, 32, 81, 117, 101, 117, 101, 58, 9, 91, 116, 111, 109, 44, 32, 110, 97,
                    110, 97, 93, 13, 10, 13, 10, 60, 73, 68, 44, 32, 78, 97, 109, 101, 62, 32,
                    77, 97, 112, 58, 9, 123, 49, 51, 61, 116, 111, 109, 44, 32, 49, 52, 61, 110,
                    97, 110, 97, 125, 13, 10, 60, 78, 97, 109, 101, 44, 32, 73, 68, 62, 32, 77,
                    97, 112, 58, 9, 123, 97, 108, 105, 99, 101, 61, 49, 50, 44, 32, 98, 111, 98,
                    61, 49, 49, 44, 32, 115, 97, 110, 100, 121, 61, 49, 53, 125, 13, 10, 13, 10,
            }, {
            42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42,
            42, 42, 42, 42, 42, 13, 10, 114, 97, 110, 100, 111, 109, 32, 110, 117, 109, 98,
            101, 114, 32, 115, 101, 101, 100, 63, 32, 50, 50, 13, 10, 42, 42, 42, 42, 42,
            42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42,
            13, 10, 73, 68, 32, 32, 32, 76, 105, 115, 116, 58, 9, 91, 49, 51, 44, 32,
            49, 52, 44, 32, 49, 50, 44, 32, 49, 49, 44, 32, 49, 53, 93, 13, 10, 78,
            97, 109, 101, 32, 76, 105, 115, 116, 58, 9, 91, 116, 111, 109, 44, 32, 110, 97,
            110, 97, 44, 32, 97, 108, 105, 99, 101, 44, 32, 98, 111, 98, 44, 32, 115, 97,
            110, 100, 121, 93, 13, 10, 13, 10, 73, 68, 32, 32, 32, 81, 117, 101, 117, 101,
            58, 9, 91, 49, 50, 44, 32, 49, 49, 44, 32, 49, 53, 93, 13, 10, 78, 97,
            109, 101, 32, 81, 117, 101, 117, 101, 58, 9, 91, 98, 111, 98, 44, 32, 115, 97,
            110, 100, 121, 93, 13, 10, 13, 10, 60, 73, 68, 44, 32, 78, 97, 109, 101, 62,
            32, 77, 97, 112, 58, 9, 123, 49, 51, 61, 98, 111, 98, 44, 32, 49, 52, 61,
            115, 97, 110, 100, 121, 125, 13, 10, 60, 78, 97, 109, 101, 44, 32, 73, 68, 62,
            32, 77, 97, 112, 58, 9, 123, 97, 108, 105, 99, 101, 61, 49, 53, 44, 32, 110,
            97, 110, 97, 61, 49, 49, 44, 32, 116, 111, 109, 61, 49, 50, 125, 13, 10, 13,
            10,
    },
    };
}

