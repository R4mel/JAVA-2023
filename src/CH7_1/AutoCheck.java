package CH7_1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

/******************************************************************************
 * 자동 체크 소스 코드
 ******************************************************************************/

/******************************************************************************
 * class AutoCheck
 ******************************************************************************/
class AutoCheck {
    private boolean isTrace;
    private int autoCheck;
    private Scanner inScan;
    private String output;
    private final String TEST_DIR = "test/test_ch7_1";
    private final String OJ_DIR = "oj";


    public AutoCheck(int autoCheck, boolean isTrace) {
        this.autoCheck = autoCheck;
        this.isTrace = isTrace;
        if (autoCheck != 2) UI.echo_input = true;
    }

    void run() {
        inScan = new Scanner(System.in);
        File testDir = new File(TEST_DIR);
        String fileNames[] = testDir.list();

        for (var fn : fileNames) {
            if (!fn.endsWith(".in")) continue;
            String testName = fn.substring(0, fn.length() - 3);
            String path = TEST_DIR + "/" + testName;
            String inPath = path + ".in";
            String outPath = path + ".out";
            try {
                simulate(inPath);

                if (autoCheck == 1)
                    evaluate(testName, inPath, outPath);
                else if (autoCheck == 2) {
                    copyFile(inPath, null, OJ_DIR);
                    copyFile(outPath, output, OJ_DIR);
                } else if (autoCheck == 3)
                    copyFile(outPath, output, TEST_DIR);

            } catch (IOException e) {
                System.out.println(e);
            }
        }
        inScan.close();
        System.out.println("Good bye!!");
    }

    void simulate(String inPath) throws IOException {
        var outStream = new ByteArrayOutputStream();
        var curOut = new PrintStream(outStream);
        var out = System.out;
        System.setOut(curOut);
        Main.run(new Scanner(new FileInputStream(inPath)));
        curOut.flush();
        output = outStream.toString();
        curOut.close(); // outStream.close();
        System.setOut(out);
    }

    void evaluate(String prompt, String inPath, String correctPath) throws IOException {
        String correct = Files.readString(Paths.get(correctPath));
        String corStr = correct.replaceAll("\\s", "");
        String outStr = output.replaceAll("\\s", "");
        boolean isCorrect = corStr.equals(outStr);
        System.out.println(prompt + " : " + (isCorrect ? "O" : "X"));
        System.out.flush();
        if (!isCorrect && isTrace)
            diff(output, correct);
    }

    void copyFile(String srcPath, String content, String newDir) throws IOException {
        String dstPath = srcPath.replace(TEST_DIR + "/", newDir + "/");
        System.out.println("copy file: " + srcPath + "\t-->    " + dstPath);
        if (content == null)
            content = Files.readString(Paths.get(srcPath));
        Files.writeString(Paths.get(dstPath), content, StandardOpenOption.WRITE,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    void diff(String outputStr, String correctStr) {
        var outputTR = new TokenReader(outputStr, true);
        var correctTR = new TokenReader(correctStr, false);
        String outputNext, correctNext;
        while (true) {
            outputNext = outputTR.next();
            correctNext = correctTR.next();
            if ((outputNext == null) || (correctNext == null))
                break;
            if (!outputNext.equals(correctNext)) {
                printTraceMessage(correctTR.getLine(), outputNext, correctNext);
                outputTR.skipCurLine();
                correctTR.skipCurLine();
            }
        }
        if ((outputNext == null) && (correctNext == null))
            return;
        if (outputNext != null)
            outputTR.printRemainingLines("User output", "correct answer");
        else if (correctNext != null)
            correctTR.printRemainingLines("Correct answer", "user output");
    }

    void printTraceMessage(String line, String outputToken, String correctToken) {
        System.out.println("^--- [WRONG line]");
        System.out.println(line + "\n" + "^--- [CORRECT line]");
        System.out.println("output(\"" + outputToken +
                "\") != correct(\"" + correctToken + "\")");
        System.out.print("continue[y/n]? ");
        String next = inScan.next();
        if (!next.equals("y") && !next.equals("Y")) {
            System.out.println("\nGood bye!!");
            System.exit(0);
        }
    }

}

class TokenReader {
    private String lines[];
    private String line;
    private String tokens[];
    private int idx, curLine;
    private boolean printMode;

    public TokenReader(String fileStr, boolean printMode) {
        this.printMode = printMode;
        idx = curLine = 0;
        lines = fileStr.split("\\n");
        nextLine();
    }

    public void skipCurLine() {
        idx = tokens.length;
    }

    public String getLine() {
        return line;
    }

    public String[] nextLine() {
        idx = 0;
        while (curLine < lines.length) {
            line = lines[curLine++];
            // 끝의 '\r' 문자 제거
            if (printMode) System.out.println(line.stripTrailing());
            if (line.isEmpty()) continue;
            tokens = line.split("\\s");
            if ((tokens.length == 0) ||
                    ((tokens.length == 1) && (tokens[0].isEmpty())))
                continue;
            return tokens;
        }
        return null;
    }

    public String next() {
        if ((idx == tokens.length) && (nextLine() == null))
            return null;
        return tokens[idx++];
    }

    public void printRemainingLines(String preMsg, String postMsg) {
        System.out.println(preMsg + " has more lines than " + postMsg + " as follows.");
        System.out.println(line);
        while (nextLine() != null)
            System.out.println(line);
    }
}