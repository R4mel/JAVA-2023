package practice8_3;

import java.util.*;
import java.io.*;
import java.nio.file.*;

class User {
    private String id, password;

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String toString() {
        return id + "(" + password + ")";
    }
}

class UserManager {
    private final String FILEPATH = "userdb.txt";
    private ArrayList<User> userList = new ArrayList<>();

    public void add(User user) {
        userList.add(user);
    } // 사용자를 userList에 추가

    public void remove(int index) { // 인덱스 위치의 사용자를 userList에서 제거
        if (index >= userList.size()) return;
        System.out.println(userList.get(index).getId() + " removed from userList");
        userList.remove(index);
    }

    public void displayUserList() { // userList 보여주기
        System.out.println("userList: " + userList + "\n");
    }

    public void clearUserList() {   // userList의 모든 원소 삭제
        userList.clear();
        System.out.println("userList cleared: " + userList);
    }

    public void displayFile() {     // "userdb.txt" 파일 내용 보여주기
        System.out.println("--------------------------------");
        System.out.println(FILEPATH + ":");
        System.out.println("-----------");
        try {
            File file = new File(FILEPATH);
            if (!file.exists()) {
                System.out.println("not found");
                System.out.println("--------------------------------");
                return;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String l;
            while ((l = reader.readLine()) != null) {
                System.out.println(l);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("not found");
        }
        System.out.println("--------------------------------");
    }

    public void deleteFile() {     // "userdb.txt" 파일 삭제
        // 문제 4-1: FILEPATH를 삭제하는 코드를 삽입하라.
        File file = new File(FILEPATH);
        file.delete();
        System.out.println(FILEPATH + ": deleted");
    }

    public void saveFile() {     // userList의 모든 사용자를 "userdb.txt" 파일에 저장
        try {
            var fout = new PrintStream(FILEPATH);
            for (int i = 0; i < userList.size(); i++) {
                fout.println(userList.get(i).getId() + "," + userList.get(i).getPassword());
            }
            fout.close();
        } catch (IOException e) {
            System.out.println(e);
        }

        System.out.println("saved to " + FILEPATH);
        displayFile();
        System.out.println();
    }

    public void loadFile() { // "userdb.txt" 파일에서 사용자를 읽어 와 userList에 추가
        clearUserList();
        Scanner s = null;
        try {
            FileInputStream fileInputStream = new FileInputStream(FILEPATH);
            s = new Scanner(fileInputStream);
            String line;
            while ((line = s.nextLine()) != null) {
                String[] tmp = line.split(",");
                userList.add(new User(tmp[0], tmp[1]));
            }
            fileInputStream.close();
            // 문제 4-3: FILEPATH에서 사용자를 모두 읽어 와 userList에 추가하는 코드를 삽입하라.
        } catch (IOException e) {
            System.out.println(FILEPATH + ": not found");
            return;
        } catch (NoSuchElementException e) {
            s.close(); // 에러 아님: 이 예외를 통해 파일을 끝까지 다 읽었다는 것을 확인함
        }

        System.out.println("loaded from " + FILEPATH);
        displayUserList();
    }
}

class Executor {
    public static Scanner scanner = new Scanner(System.in);
    public static boolean autoCheck = false;
    public static int seed;
    private static List<String> ids;
    private static List<String> passwds;
    public static UserManager um = new UserManager();

    // 사용자을 자동생성 한 후 UserManager um내의 userList에 사용자를 추가함
    public static void addUsers() {
        ids = new ArrayList<String>();
        ids.add("bob");
        ids.add("sandy");
        ids.add("alice");
        ids.add("nana");
        ids.add("tom");
        ids.add("jane");
        ids.add("john");
        passwds = new ArrayList<String>();
        passwds.add("1234");
        passwds.add("2345");
        passwds.add("3456");
        passwds.add("4567");
        passwds.add("5678");
        passwds.add("6789");
        passwds.add("7890");

        Random rnd = new Random(seed);
        boolean check[] = new boolean[ids.size()];
        for (int i = 0; i < 4; ) {
            int idx = rnd.nextInt(ids.size());
            if (check[idx]) continue;
            check[idx] = true;
            um.add(new User(ids.get(idx), passwds.get(idx))); // um::userList에 사용자를 추가
            i++;
        }
    }

    public static void deleteTest() {
        um.clearUserList();   // 사용자 리스트의 모든 원소 삭제
        addUsers();           // 사용자 자동생성 후 사용자 리스트에 추가
        um.deleteFile();                                        // 문제 4-1 호출
        um.displayFile();     // 파일 내용 보기
        um.displayUserList(); // 사용자 리스트 보기
    }

    public static void saveTest() {
        um.saveFile();
    }           // 문제 4-2 호출

    public static void loadTest() {
        um.loadFile();
    }           // 문제 4-3 호출

    public static void removeSaveLoadTest() {
        um.remove(0);         // userList의 앞쪽 2개의 원소를 삭제함
        um.remove(0);
        um.displayUserList(); // 앞쪽 원소 2개의 삭제되었는지 확인
        um.saveFile();        // 새로 저장하고(2개 원소만 저장되었는지 확인)
        um.loadFile();        // 불러오기 (2개 원소만 존재하는지 확인)
    }

    public static void run() {
        deleteTest();
        saveTest();
        loadTest();
        removeSaveLoadTest();
    }

    // 난수 발생을 위한 임의의 seed 값 하나를 입력 받음
    public static boolean getInput() {
        System.out.println("***********************");
        System.out.print("random numer seed? "); // 난수 발생을 위한 seed 값 입력 받음: -1이면 종료
        seed = scanner.nextInt();
        if (autoCheck)
            System.out.println(seed);
        System.out.println("***********************");
        return seed != -1;                       // -1이면 프로그램 종료
    }
}

public class Main {
    public static void main(String[] args) {
        while (Executor.getInput()) // -1 입력한 경우, 프로그램 종료
            Executor.run();
        new AutoCheck(true);
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
    private static String input = "111 222 -1";
    private static byte answers[][] = {
            {
                    42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42,
                    42, 42, 42, 42, 42, 13, 10, 114, 97, 110, 100, 111, 109, 32, 110, 117, 109, 101,
                    114, 32, 115, 101, 101, 100, 63, 32, 49, 49, 49, 13, 10, 42, 42, 42, 42, 42,
                    42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42,
                    13, 10, 117, 115, 101, 114, 76, 105, 115, 116, 32, 99, 108, 101, 97, 114, 101, 100,
                    58, 32, 91, 93, 13, 10, 117, 115, 101, 114, 100, 98, 46, 116, 120, 116, 58, 32,
                    100, 101, 108, 101, 116, 101, 100, 13, 10, 45, 45, 45, 45, 45, 45, 45, 45, 45,
                    45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
                    45, 45, 45, 45, 45, 13, 10, 117, 115, 101, 114, 100, 98, 46, 116, 120, 116, 58,
                    13, 10, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 13, 10, 110, 111, 116,
                    32, 102, 111, 117, 110, 100, 13, 10, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
                    45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
                    45, 45, 45, 45, 13, 10, 117, 115, 101, 114, 76, 105, 115, 116, 58, 32, 91, 106,
                    97, 110, 101, 40, 54, 55, 56, 57, 41, 44, 32, 106, 111, 104, 110, 40, 55, 56,
                    57, 48, 41, 44, 32, 98, 111, 98, 40, 49, 50, 51, 52, 41, 44, 32, 115, 97,
                    110, 100, 121, 40, 50, 51, 52, 53, 41, 93, 10, 13, 10, 115, 97, 118, 101, 100,
                    32, 116, 111, 32, 117, 115, 101, 114, 100, 98, 46, 116, 120, 116, 13, 10, 45, 45,
                    45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
                    45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 13, 10, 117, 115, 101, 114,
                    100, 98, 46, 116, 120, 116, 58, 13, 10, 45, 45, 45, 45, 45, 45, 45, 45, 45,
                    45, 45, 13, 10, 106, 97, 110, 101, 44, 54, 55, 56, 57, 13, 10, 106, 111, 104,
                    110, 44, 55, 56, 57, 48, 13, 10, 98, 111, 98, 44, 49, 50, 51, 52, 13, 10,
                    115, 97, 110, 100, 121, 44, 50, 51, 52, 53, 13, 10, 45, 45, 45, 45, 45, 45,
                    45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
                    45, 45, 45, 45, 45, 45, 45, 45, 13, 10, 13, 10, 117, 115, 101, 114, 76, 105,
                    115, 116, 32, 99, 108, 101, 97, 114, 101, 100, 58, 32, 91, 93, 13, 10, 108, 111,
                    97, 100, 101, 100, 32, 102, 114, 111, 109, 32, 117, 115, 101, 114, 100, 98, 46, 116,
                    120, 116, 13, 10, 117, 115, 101, 114, 76, 105, 115, 116, 58, 32, 91, 106, 97, 110,
                    101, 40, 54, 55, 56, 57, 41, 44, 32, 106, 111, 104, 110, 40, 55, 56, 57, 48,
                    41, 44, 32, 98, 111, 98, 40, 49, 50, 51, 52, 41, 44, 32, 115, 97, 110, 100,
                    121, 40, 50, 51, 52, 53, 41, 93, 10, 13, 10, 106, 97, 110, 101, 32, 114, 101,
                    109, 111, 118, 101, 100, 32, 102, 114, 111, 109, 32, 117, 115, 101, 114, 76, 105, 115,
                    116, 13, 10, 106, 111, 104, 110, 32, 114, 101, 109, 111, 118, 101, 100, 32, 102, 114,
                    111, 109, 32, 117, 115, 101, 114, 76, 105, 115, 116, 13, 10, 117, 115, 101, 114, 76,
                    105, 115, 116, 58, 32, 91, 98, 111, 98, 40, 49, 50, 51, 52, 41, 44, 32, 115,
                    97, 110, 100, 121, 40, 50, 51, 52, 53, 41, 93, 10, 13, 10, 115, 97, 118, 101,
                    100, 32, 116, 111, 32, 117, 115, 101, 114, 100, 98, 46, 116, 120, 116, 13, 10, 45,
                    45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
                    45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 13, 10, 117, 115, 101,
                    114, 100, 98, 46, 116, 120, 116, 58, 13, 10, 45, 45, 45, 45, 45, 45, 45, 45,
                    45, 45, 45, 13, 10, 98, 111, 98, 44, 49, 50, 51, 52, 13, 10, 115, 97, 110,
                    100, 121, 44, 50, 51, 52, 53, 13, 10, 45, 45, 45, 45, 45, 45, 45, 45, 45,
                    45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
                    45, 45, 45, 45, 45, 13, 10, 13, 10, 117, 115, 101, 114, 76, 105, 115, 116, 32,
                    99, 108, 101, 97, 114, 101, 100, 58, 32, 91, 93, 13, 10, 108, 111, 97, 100, 101,
                    100, 32, 102, 114, 111, 109, 32, 117, 115, 101, 114, 100, 98, 46, 116, 120, 116, 13,
                    10, 117, 115, 101, 114, 76, 105, 115, 116, 58, 32, 91, 98, 111, 98, 40, 49, 50,
                    51, 52, 41, 44, 32, 115, 97, 110, 100, 121, 40, 50, 51, 52, 53, 41, 93, 10,
                    13, 10,
            }, {
            42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42,
            42, 42, 42, 42, 42, 13, 10, 114, 97, 110, 100, 111, 109, 32, 110, 117, 109, 101,
            114, 32, 115, 101, 101, 100, 63, 32, 50, 50, 50, 13, 10, 42, 42, 42, 42, 42,
            42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42, 42,
            13, 10, 117, 115, 101, 114, 76, 105, 115, 116, 32, 99, 108, 101, 97, 114, 101, 100,
            58, 32, 91, 93, 13, 10, 117, 115, 101, 114, 100, 98, 46, 116, 120, 116, 58, 32,
            100, 101, 108, 101, 116, 101, 100, 13, 10, 45, 45, 45, 45, 45, 45, 45, 45, 45,
            45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
            45, 45, 45, 45, 45, 13, 10, 117, 115, 101, 114, 100, 98, 46, 116, 120, 116, 58,
            13, 10, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 13, 10, 110, 111, 116,
            32, 102, 111, 117, 110, 100, 13, 10, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
            45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
            45, 45, 45, 45, 13, 10, 117, 115, 101, 114, 76, 105, 115, 116, 58, 32, 91, 115,
            97, 110, 100, 121, 40, 50, 51, 52, 53, 41, 44, 32, 106, 111, 104, 110, 40, 55,
            56, 57, 48, 41, 44, 32, 106, 97, 110, 101, 40, 54, 55, 56, 57, 41, 44, 32,
            97, 108, 105, 99, 101, 40, 51, 52, 53, 54, 41, 93, 10, 13, 10, 115, 97, 118,
            101, 100, 32, 116, 111, 32, 117, 115, 101, 114, 100, 98, 46, 116, 120, 116, 13, 10,
            45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
            45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 13, 10, 117, 115,
            101, 114, 100, 98, 46, 116, 120, 116, 58, 13, 10, 45, 45, 45, 45, 45, 45, 45,
            45, 45, 45, 45, 13, 10, 115, 97, 110, 100, 121, 44, 50, 51, 52, 53, 13, 10,
            106, 111, 104, 110, 44, 55, 56, 57, 48, 13, 10, 106, 97, 110, 101, 44, 54, 55,
            56, 57, 13, 10, 97, 108, 105, 99, 101, 44, 51, 52, 53, 54, 13, 10, 45, 45,
            45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
            45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 13, 10, 13, 10, 117, 115,
            101, 114, 76, 105, 115, 116, 32, 99, 108, 101, 97, 114, 101, 100, 58, 32, 91, 93,
            13, 10, 108, 111, 97, 100, 101, 100, 32, 102, 114, 111, 109, 32, 117, 115, 101, 114,
            100, 98, 46, 116, 120, 116, 13, 10, 117, 115, 101, 114, 76, 105, 115, 116, 58, 32,
            91, 115, 97, 110, 100, 121, 40, 50, 51, 52, 53, 41, 44, 32, 106, 111, 104, 110,
            40, 55, 56, 57, 48, 41, 44, 32, 106, 97, 110, 101, 40, 54, 55, 56, 57, 41,
            44, 32, 97, 108, 105, 99, 101, 40, 51, 52, 53, 54, 41, 93, 10, 13, 10, 115,
            97, 110, 100, 121, 32, 114, 101, 109, 111, 118, 101, 100, 32, 102, 114, 111, 109, 32,
            117, 115, 101, 114, 76, 105, 115, 116, 13, 10, 106, 111, 104, 110, 32, 114, 101, 109,
            111, 118, 101, 100, 32, 102, 114, 111, 109, 32, 117, 115, 101, 114, 76, 105, 115, 116,
            13, 10, 117, 115, 101, 114, 76, 105, 115, 116, 58, 32, 91, 106, 97, 110, 101, 40,
            54, 55, 56, 57, 41, 44, 32, 97, 108, 105, 99, 101, 40, 51, 52, 53, 54, 41,
            93, 10, 13, 10, 115, 97, 118, 101, 100, 32, 116, 111, 32, 117, 115, 101, 114, 100,
            98, 46, 116, 120, 116, 13, 10, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
            45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
            45, 45, 45, 13, 10, 117, 115, 101, 114, 100, 98, 46, 116, 120, 116, 58, 13, 10,
            45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 13, 10, 106, 97, 110, 101, 44,
            54, 55, 56, 57, 13, 10, 97, 108, 105, 99, 101, 44, 51, 52, 53, 54, 13, 10,
            45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45,
            45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 45, 13, 10, 13, 10,
            117, 115, 101, 114, 76, 105, 115, 116, 32, 99, 108, 101, 97, 114, 101, 100, 58, 32,
            91, 93, 13, 10, 108, 111, 97, 100, 101, 100, 32, 102, 114, 111, 109, 32, 117, 115,
            101, 114, 100, 98, 46, 116, 120, 116, 13, 10, 117, 115, 101, 114, 76, 105, 115, 116,
            58, 32, 91, 106, 97, 110, 101, 40, 54, 55, 56, 57, 41, 44, 32, 97, 108, 105,
            99, 101, 40, 51, 52, 53, 54, 41, 93, 10, 13, 10,
    },
    };
}