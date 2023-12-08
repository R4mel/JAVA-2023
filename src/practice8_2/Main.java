package practice8_2;

import java.io.*;
import java.util.*;

class MyBuffer {
    private byte[] buffer;
    private int position, limit, capacity, mark;
    private boolean isWriting;

    private MyBuffer() {
    }

    private MyBuffer(byte[] array, boolean isWriting) {
        buffer = array; // 데이타를 저장할 공간
        this.isWriting = isWriting; // true:쓰기용 버퍼, false:읽기용 버퍼
        position = 0; // 다음에 쓰거나 또는 읽어 낼 버퍼의 위치
        capacity = array.length; // 버퍼의 용량
        limit = capacity; // 읽기용:마지막으로 기록된(읽어낼 마지막) 데이타의 다음 위치, 쓰기용:버퍼의 용량
    }

    // 리스트의 from에서 to 전까지의 원소를 buffer에 저장
    public void put(List<Byte> list, int from, int to) {
        if (to > list.size())
            to = list.size();
        for (int i = from; i < to && position < limit; i++)
            buffer[position++] = list.get(i);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[");
        int from, to;
        if (isWriting) {
            from = 0;
            to = position;
        } else {
            from = position;
            to = limit;
        }
        if (from < to)
            sb.append(buffer[from]);
        for (int i = from + 1; i < to; i++)
            sb.append(", " + buffer[i]);
        sb.append("]\nisWriting:" + isWriting + "  position:" + position);
        sb.append("  limit:" + limit + "  capacity:" + capacity + "\n");
        return sb.toString();
    }

    public static MyBuffer wrap(byte[] array) {
        // 문제 3-1: 기존 MyBuffer의 적절한 생성자를 이용하여 [읽기용]으로 버퍼 객체를 생성하여 리턴하라.
        // 인자인 array가 MyBuffer::buffer로 사용되게 하라.
        return new MyBuffer(array, false); // 구현 시 수정할 것
    }

    public static MyBuffer create(int size) {
        // 문제 3-2: 기존 MyBuffer의 적절한 생성자를 이용하여 [쓰기용]으로 버퍼 객체를 생성하여 리턴하라.
        // size 크기를 가진 배열을 직접 생성하여 MyBuffer::buffer로 사용되게 하라.
        return new MyBuffer(new byte[size], true); // 구현 시 수정할 것
    }

    public void flip() {
        if (isWriting == true) {
            isWriting = false;
            limit = position;
            position = 0;
        } else {
            isWriting = true;
            limit = buffer.length;
            position = 0;
        }
        // 문제 3-3: isWriting 값을 읽기용이면 쓰기용으로, 쓰기용이면 읽기용으로 변환하라.
        // limit은 읽기용으로 변경된 경우 마지막으로 기록된 데이타의 다음 위치, 즉 position이며,
        // 쓰기용으로 변경된 경우 버퍼의 용량임
        // position은 버퍼의 처음부터 다시 시작한다.
    }

    public int get() {
        // 문제 3-4: position 위치의 버퍼 데이타를 반환하라. 읽은 후 position 위치를 증가시켜야 함
        // 만약 버퍼에서 읽어 낼 데이타를 이미 다 읽었다면 -1를 반환하라.
        if (position == capacity)
            return -1;
        return buffer[position++];
    }

    public void get(byte[] array) {
        // 문제 3-4: 버퍼의 position 인덱스 위치 데이타부터 연속하여 배열 array에 기록하라.
        // 배열의 크기를 초과하여 기록하지 말아야 하며 또한
        // 버퍼의 마지막 데이터까지만 배열에 기록해야 한다.
        // position 인덱스 위치를 적절히 증가시켜야 함
        for (int i = 0; i < array.length; i++) {
            array[i] = buffer[position];
            position++;
        }
        if (position + 1 == capacity) {
            position = limit;
        }
    }

    public void mark() {
        mark = position;
        // 문제 3-5: 다음 읽을 위치 position을 mark에 저장해 둠
    }

    public void reset() {
        position = mark;
        if (position > limit) {
            position = limit;
        }
        // 문제 3-5: mark에 저장된 값을 다음 읽을 위치 position으로 복원함
        // 만약 복원된 다음 읽을 위치가 limit보다 클 경우 limit으로 설정함
    }
}

class Application {
    private static MyBuffer buffer;

    public static void wrapTest(List<Byte> list) {
        byte[] array = new byte[2];
        MyBuffer buffer1 = MyBuffer.wrap(array); // 문제 3-1 호출
        array[0] = list.get(0); // 3 // 리스트 두개 원소들을 버퍼에 삽입
        array[1] = list.get(1); // 4
        System.out.println("wrap(array[2]):\t" + buffer1); // 버퍼 내용 출력 [3, 4]
        // 버퍼가 내부 buffer로 array[]를 사용하기 때문에
        // 버퍼를 생성한 후 array[]를 수정하면 수정한 값(3, 4)으로 버퍼의 내용이 출력되어야 함
    }

    public static void createTest(List<Byte> list) {
        buffer = MyBuffer.create(8); // 문제 3-2 호출
        System.out.println("create(8):\t" + buffer);

        buffer.put(list, 2, 7); // [10, 11, 12, 13, 14] // 리스트 원소들을 버퍼에 삽입
        System.out.println("put() 5 items:\t" + buffer); // 버퍼 내용 출력
    }

    public static void flipTest() {
        buffer.flip(); // 문제 3-3 호출
        System.out.println("flip():\t\t" + buffer); // 쓰기용에서 읽기용으로 변경
    }

    public static void getTest() {
        int b = buffer.get(); // 문제 3-4 호출
        byte[] array = new byte[2];
        buffer.get(array); // 문제 3-4 호출
        System.out.println("get() 3 items:\t" + b + " " + array[0] + " " + array[1]);
        System.out.println("buffer:\t\t" + buffer);
    }

    public static void markResetTest() {
        buffer.mark(); // 문제 3-5 호출
        System.out.println("mark() pos:\t" + buffer);

        byte[] array = new byte[4];
        buffer.get(array);
        System.out.println("get(array[3]):\t" + array[0] + " " + array[1] + " " + array[2] + " " + array[3]);
        System.out.println("buffer:\t\t" + buffer);

        buffer.reset(); // 문제 3-5 호출
        System.out.println("reset() pos:\t" + buffer);
    }

    public static void run(List<Byte> list) {
        wrapTest(list); // 문제 3-1 호출
        createTest(list); // 문제 3-2 호출
        flipTest(); // 문제 3-3 호출
        getTest(); // 문제 3-4 호출
        markResetTest(); // 문제 3-5 호출
    }
}

class Executor {
    public static Scanner scanner = new Scanner(System.in);
    public static boolean autoCheck = false;
    private static int seed;
    private static List<Byte> data;

    static public void swap(List<Byte> list, int i, int j) {
        Byte tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }

    public static void run() {
        data = new ArrayList<Byte>();
        data.add((byte) 3);
        data.add((byte) 4);
        data.add((byte) 10);
        data.add((byte) 11);
        data.add((byte) 12);
        data.add((byte) 13);
        data.add((byte) 14);
        data.add((byte) 21);
        data.add((byte) 22);

        List<Byte> dataList = new ArrayList<>(data);
        if (seed != 0) {
            Random rnd = new Random(seed);
            for (int i = 0; i < dataList.size(); i++)
                swap(dataList, i, rnd.nextInt(dataList.size()));
        }
        System.out.println("Data List:\t" + dataList + "\n");

        Application.run(dataList); // 사용자 프로그램 호출
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
            if (InputOutput.eof()) break;
        }
        restoreStreams();
        //System.out.println("score: "+scores[0]+" / "+InputOutput.getScore());
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

    private void printRemainingLines(String lines[], String msg, int lnNum, String ln) {
        System.out.println(msg + ":" + (++lnNum) + ":" + ln);
        for (; lnNum < lines.length; lnNum++)
            System.out.println(msg + ":" + (lnNum + 1) + ":" + lines[lnNum]);
    }

    private void printUserOutput(String output) {
        byte bytes[] = output.getBytes();
        for (int i = 0; i < bytes.length; ) {
            System.out.print("        ");
            for (int cnt = 0; cnt < 18 && i < bytes.length; cnt++, ++i)
                System.out.printf("%3d,", bytes[i]);
            System.out.println();
        }
        System.out.println("    }, {");
        //System.out.println(output);
    }

    private boolean printOutput = false;
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
    private static String input = "0 1 -1";
    private static byte answers[][] = {
            {
                    42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42,
                    42, 42, 42, 42, 42, 13, 10, 114, 97, 110, 100, 111, 109, 32, 110, 117, 109, 98,
                    101, 114, 32, 115, 101, 101, 100, 63, 32, 48, 13, 10, 42, 42, 42, 42, 42, 42,
                    42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 13,
                    10, 68, 97, 116, 97, 32, 76, 105, 115, 116, 58, 9, 91, 51, 44, 32, 52, 44,
                    32, 49, 48, 44, 32, 49, 49, 44, 32, 49, 50, 44, 32, 49, 51, 44, 32, 49,
                    52, 44, 32, 50, 49, 44, 32, 50, 50, 93, 10, 13, 10, 119, 114, 97, 112, 40,
                    97, 114, 114, 97, 121, 91, 50, 93, 41, 58, 9, 91, 51, 44, 32, 52, 93, 10,
                    105, 115, 87, 114, 105, 116, 105, 110, 103, 58, 102, 97, 108, 115, 101, 32, 32, 112,
                    111, 115, 105, 116, 105, 111, 110, 58, 48, 32, 32, 108, 105, 109, 105, 116, 58, 50,
                    32, 32, 99, 97, 112, 97, 99, 105, 116, 121, 58, 50, 10, 13, 10, 99, 114, 101,
                    97, 116, 101, 40, 56, 41, 58, 9, 91, 93, 10, 105, 115, 87, 114, 105, 116, 105,
                    110, 103, 58, 116, 114, 117, 101, 32, 32, 112, 111, 115, 105, 116, 105, 111, 110, 58,
                    48, 32, 32, 108, 105, 109, 105, 116, 58, 56, 32, 32, 99, 97, 112, 97, 99, 105,
                    116, 121, 58, 56, 10, 13, 10, 112, 117, 116, 40, 41, 32, 53, 32, 105, 116, 101,
                    109, 115, 58, 9, 91, 49, 48, 44, 32, 49, 49, 44, 32, 49, 50, 44, 32, 49,
                    51, 44, 32, 49, 52, 93, 10, 105, 115, 87, 114, 105, 116, 105, 110, 103, 58, 116,
                    114, 117, 101, 32, 32, 112, 111, 115, 105, 116, 105, 111, 110, 58, 53, 32, 32, 108,
                    105, 109, 105, 116, 58, 56, 32, 32, 99, 97, 112, 97, 99, 105, 116, 121, 58, 56,
                    10, 13, 10, 102, 108, 105, 112, 40, 41, 58, 9, 9, 91, 49, 48, 44, 32, 49,
                    49, 44, 32, 49, 50, 44, 32, 49, 51, 44, 32, 49, 52, 93, 10, 105, 115, 87,
                    114, 105, 116, 105, 110, 103, 58, 102, 97, 108, 115, 101, 32, 32, 112, 111, 115, 105,
                    116, 105, 111, 110, 58, 48, 32, 32, 108, 105, 109, 105, 116, 58, 53, 32, 32, 99,
                    97, 112, 97, 99, 105, 116, 121, 58, 56, 10, 13, 10, 103, 101, 116, 40, 41, 32,
                    51, 32, 105, 116, 101, 109, 115, 58, 9, 49, 48, 32, 49, 49, 32, 49, 50, 13,
                    10, 98, 117, 102, 102, 101, 114, 58, 9, 9, 91, 49, 51, 44, 32, 49, 52, 93,
                    10, 105, 115, 87, 114, 105, 116, 105, 110, 103, 58, 102, 97, 108, 115, 101, 32, 32,
                    112, 111, 115, 105, 116, 105, 111, 110, 58, 51, 32, 32, 108, 105, 109, 105, 116, 58,
                    53, 32, 32, 99, 97, 112, 97, 99, 105, 116, 121, 58, 56, 10, 13, 10, 109, 97,
                    114, 107, 40, 41, 32, 112, 111, 115, 58, 9, 91, 49, 51, 44, 32, 49, 52, 93,
                    10, 105, 115, 87, 114, 105, 116, 105, 110, 103, 58, 102, 97, 108, 115, 101, 32, 32,
                    112, 111, 115, 105, 116, 105, 111, 110, 58, 51, 32, 32, 108, 105, 109, 105, 116, 58,
                    53, 32, 32, 99, 97, 112, 97, 99, 105, 116, 121, 58, 56, 10, 13, 10, 103, 101,
                    116, 40, 97, 114, 114, 97, 121, 91, 51, 93, 41, 58, 9, 49, 51, 32, 49, 52,
                    32, 48, 32, 48, 13, 10, 98, 117, 102, 102, 101, 114, 58, 9, 9, 91, 93, 10,
                    105, 115, 87, 114, 105, 116, 105, 110, 103, 58, 102, 97, 108, 115, 101, 32, 32, 112,
                    111, 115, 105, 116, 105, 111, 110, 58, 53, 32, 32, 108, 105, 109, 105, 116, 58, 53,
                    32, 32, 99, 97, 112, 97, 99, 105, 116, 121, 58, 56, 10, 13, 10, 114, 101, 115,
                    101, 116, 40, 41, 32, 112, 111, 115, 58, 9, 91, 49, 51, 44, 32, 49, 52, 93,
                    10, 105, 115, 87, 114, 105, 116, 105, 110, 103, 58, 102, 97, 108, 115, 101, 32, 32,
                    112, 111, 115, 105, 116, 105, 111, 110, 58, 51, 32, 32, 108, 105, 109, 105, 116, 58,
                    53, 32, 32, 99, 97, 112, 97, 99, 105, 116, 121, 58, 56, 10, 13, 10,
            }, {
            42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42,
            42, 42, 42, 42, 42, 13, 10, 114, 97, 110, 100, 111, 109, 32, 110, 117, 109, 98,
            101, 114, 32, 115, 101, 101, 100, 63, 32, 49, 13, 10, 42, 42, 42, 42, 42, 42,
            42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 13,
            10, 68, 97, 116, 97, 32, 76, 105, 115, 116, 58, 9, 91, 49, 52, 44, 32, 49,
            50, 44, 32, 52, 44, 32, 51, 44, 32, 49, 51, 44, 32, 49, 49, 44, 32, 50,
            50, 44, 32, 49, 48, 44, 32, 50, 49, 93, 10, 13, 10, 119, 114, 97, 112, 40,
            97, 114, 114, 97, 121, 91, 50, 93, 41, 58, 9, 91, 49, 52, 44, 32, 49, 50,
            93, 10, 105, 115, 87, 114, 105, 116, 105, 110, 103, 58, 102, 97, 108, 115, 101, 32,
            32, 112, 111, 115, 105, 116, 105, 111, 110, 58, 48, 32, 32, 108, 105, 109, 105, 116,
            58, 50, 32, 32, 99, 97, 112, 97, 99, 105, 116, 121, 58, 50, 10, 13, 10, 99,
            114, 101, 97, 116, 101, 40, 56, 41, 58, 9, 91, 93, 10, 105, 115, 87, 114, 105,
            116, 105, 110, 103, 58, 116, 114, 117, 101, 32, 32, 112, 111, 115, 105, 116, 105, 111,
            110, 58, 48, 32, 32, 108, 105, 109, 105, 116, 58, 56, 32, 32, 99, 97, 112, 97,
            99, 105, 116, 121, 58, 56, 10, 13, 10, 112, 117, 116, 40, 41, 32, 53, 32, 105,
            116, 101, 109, 115, 58, 9, 91, 52, 44, 32, 51, 44, 32, 49, 51, 44, 32, 49,
            49, 44, 32, 50, 50, 93, 10, 105, 115, 87, 114, 105, 116, 105, 110, 103, 58, 116,
            114, 117, 101, 32, 32, 112, 111, 115, 105, 116, 105, 111, 110, 58, 53, 32, 32, 108,
            105, 109, 105, 116, 58, 56, 32, 32, 99, 97, 112, 97, 99, 105, 116, 121, 58, 56,
            10, 13, 10, 102, 108, 105, 112, 40, 41, 58, 9, 9, 91, 52, 44, 32, 51, 44,
            32, 49, 51, 44, 32, 49, 49, 44, 32, 50, 50, 93, 10, 105, 115, 87, 114, 105,
            116, 105, 110, 103, 58, 102, 97, 108, 115, 101, 32, 32, 112, 111, 115, 105, 116, 105,
            111, 110, 58, 48, 32, 32, 108, 105, 109, 105, 116, 58, 53, 32, 32, 99, 97, 112,
            97, 99, 105, 116, 121, 58, 56, 10, 13, 10, 103, 101, 116, 40, 41, 32, 51, 32,
            105, 116, 101, 109, 115, 58, 9, 52, 32, 51, 32, 49, 51, 13, 10, 98, 117, 102,
            102, 101, 114, 58, 9, 9, 91, 49, 49, 44, 32, 50, 50, 93, 10, 105, 115, 87,
            114, 105, 116, 105, 110, 103, 58, 102, 97, 108, 115, 101, 32, 32, 112, 111, 115, 105,
            116, 105, 111, 110, 58, 51, 32, 32, 108, 105, 109, 105, 116, 58, 53, 32, 32, 99,
            97, 112, 97, 99, 105, 116, 121, 58, 56, 10, 13, 10, 109, 97, 114, 107, 40, 41,
            32, 112, 111, 115, 58, 9, 91, 49, 49, 44, 32, 50, 50, 93, 10, 105, 115, 87,
            114, 105, 116, 105, 110, 103, 58, 102, 97, 108, 115, 101, 32, 32, 112, 111, 115, 105,
            116, 105, 111, 110, 58, 51, 32, 32, 108, 105, 109, 105, 116, 58, 53, 32, 32, 99,
            97, 112, 97, 99, 105, 116, 121, 58, 56, 10, 13, 10, 103, 101, 116, 40, 97, 114,
            114, 97, 121, 91, 51, 93, 41, 58, 9, 49, 49, 32, 50, 50, 32, 48, 32, 48,
            13, 10, 98, 117, 102, 102, 101, 114, 58, 9, 9, 91, 93, 10, 105, 115, 87, 114,
            105, 116, 105, 110, 103, 58, 102, 97, 108, 115, 101, 32, 32, 112, 111, 115, 105, 116,
            105, 111, 110, 58, 53, 32, 32, 108, 105, 109, 105, 116, 58, 53, 32, 32, 99, 97,
            112, 97, 99, 105, 116, 121, 58, 56, 10, 13, 10, 114, 101, 115, 101, 116, 40, 41,
            32, 112, 111, 115, 58, 9, 91, 49, 49, 44, 32, 50, 50, 93, 10, 105, 115, 87,
            114, 105, 116, 105, 110, 103, 58, 102, 97, 108, 115, 101, 32, 32, 112, 111, 115, 105,
            116, 105, 111, 110, 58, 51, 32, 32, 108, 105, 109, 105, 116, 58, 53, 32, 32, 99,
            97, 112, 97, 99, 105, 116, 121, 58, 56, 10, 13, 10,
    },
    };
}