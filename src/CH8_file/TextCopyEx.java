package CH8_file;

import java.io.*;

public class TextCopyEx {
    public static void main(String[] args) {
        File src = new File("c:\\windows\\system.ini");
        File dest = new File("data\\system.txt");
        int c;
        try {
            FileReader fr = new FileReader(src); // 파일 이름을 직접 주지 않고 File 객체를 지정하였음
            FileWriter fw = new FileWriter(dest);
            while ((c = fr.read()) != -1) { // 문자 하나 읽고
                fw.write((char) c); // 문자 하나 쓰고
            }
            fr.close();
            fw.close();
            System.out.println(src.getPath() + "를 " + dest.getPath() + "로 복사하였습니다.");
        } catch (IOException e) {
            System.out.println("파일 복사 오류");
        }
    }
}
