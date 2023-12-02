package CH8_file;

import java.io.*;

public class FileInputStreamEx {
    public static void main(String[] args) {
        byte b[] = new byte[6];
        try {
            var fin = new FileInputStream("data\\test.out");
            int n = fin.read(b);
            // 한번에 6bytes를 읽어 b[]에 저장하고,
            // 실제 읽은 문자수 리턴
//            int n = 0, c;
//            while ((c = fin.read()) != -1) {
//                b[n] = (byte) c;
//                n++;
//            }
            System.out.println("test.out에서 읽은 배열을 출력합니다.");
            for (int i = 0; i < b.length; i++) {
                System.out.print(b[i] + " ");
                System.out.println();
                fin.close();
            }
        } catch (IOException e) {
            System.out.println("읽지 못했습니다. 경로를 확인하세요.");
        }
    }
}
