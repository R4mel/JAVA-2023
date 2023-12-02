package CH8_file;

import java.io.*;

public class BinaryCopyEx {
    public static void main(String[] args) {
        File src = new File("data\\img1.png");
        File dest = new File("data\\copyimg.png");
        int c;
        try {
            var fi = new FileInputStream(src); // 파일 이름을 직접 주지 않고 File 객체를 지정하였음.
            var fo = new FileOutputStream(dest);
            while ((c = fi.read()) != -1) { // 한 바이트 읽고
                fo.write((byte) c); // 한 바이트 쓰고
            }
            fi.close();
            fo.close();
            System.out.println(src.getPath() + "를 " + dest.getPath() + "로 복사하였습니다.");
        } catch (IOException e) {
            System.out.println("파일 복사 오류");
        }
    }
}
