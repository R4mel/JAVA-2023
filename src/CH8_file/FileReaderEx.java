package CH8_file;

import java.io.*;

public class FileReaderEx {
    public static void main(String[] args) {
        FileReader fin = null;
        try {
            fin = new FileReader("");
            int c;
            while ((c = fin.read()) != -1) { // 한 문자씩 파일 끝까지 읽기
                System.out.print((char) c);
            }
            fin.close();
        } catch (FileNotFoundException e) {
            System.out.println("파일을 열 수 없음");
        } catch (IOException e) {
            System.out.println("입출력 오류");
        }
    }
}
