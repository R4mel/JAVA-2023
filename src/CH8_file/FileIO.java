package CH8_file;

import java.io.*;

public class FileIO {
    public static void main(String[] args) {
        try {
            int i = 1234;
            double d = 567.89;
            boolean b = true;
            String s = "String";
            var fout = new PrintStream("data\\data.txt");
            fout.println(i + " " + d + " " + b + " " + s);
            fout.close();
        } catch (FileNotFoundException e) {
            System.out.println("파일을 열 수 없음: " + e);
        }
    }
}
