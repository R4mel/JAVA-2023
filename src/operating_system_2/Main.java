package operating_system_2;

import java.util.*;

// 입력을 담당할 전역 함수들
class UI {
    static Scanner scan;                // 입력용 스캐너
    static boolean echo_input = false;  // 자동 오류 체크시 true

    static void setScan(Scanner sc) {
        scan = sc;
    }

    // 정수 값 하나를 입력 받음
    static int nextInt() {
        int value = scan.nextInt();
        // 자동 오류 체크시 출력 결과에 입력 값도 출력해 줌
        if (echo_input) System.out.println(value);
        return value;
    }

    // 여러 개의 정수 값들을 연속적으로 입력 받아 배열에 저장함
    static void nextInts(int arr[]) {
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = scan.nextInt();
            if (echo_input) System.out.print(arr[i] + " ");
        }   // 자동 오류 체크시 출력
        if (echo_input) System.out.println();
    }

    // 여러 개의 문자열 단어들을 연속적으로 입력 받아 배열에 저장함
    static void nexts(String arr[]) {
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = scan.next();
            if (echo_input) System.out.print(arr[i] + " "); // 자동 오류 체크시 출력
        }   // 자동 오류 체크시 출력
        if (echo_input) System.out.println();
    }
}

// 스케줄링할 작업들을 관리함
class Jobs {
    // 도착할 각 프로세스의 이름, 도착시간, 서비스시간 등을 배열로 관리함
    private String processNames[];
    private int arrivalTimes[];
    private int serviceTimes[];
    private int index; // 다음 번에 도착할 프로세스의 위 배열 인덱스

    public void printJobs() {
        for (String n : processNames)
            System.out.printf("%2s ", n);
        System.out.println();
        for (int t : arrivalTimes)
            System.out.printf("%2d ", t);
        System.out.println();
        for (int s : serviceTimes)
            System.out.printf("%2d ", s);
        System.out.println();
    }

    public Jobs(int num) {  // 생성자
        // 실행할 총 프로세스의 개수를 입력 받음
        System.out.print("The number of processes? ");
        num = UI.nextInt();

        // processNames[] 배열 생성 후
        // 프로세스들의 이름을 입력 받아 processNames[]에 저장
        processNames = new String[num];
        System.out.print("input " + num + " process names: ");
        UI.nexts(processNames);

        arrivalTimes = new int[num];
        System.out.print("input " + num + " arrival times: ");
        UI.nextInts(arrivalTimes);

        serviceTimes = new int[num];
        System.out.print("input " + num + " service times: ");
        UI.nextInts(serviceTimes);

        System.out.println();
        printJobs();
    }

    // 처음부터 다시 스케줄링을 시작하고자 하는 경우 호출
    public void reset() {
        index = 0;
    }

    // 아직 도착하지 않은 프로세스가 더 있는지 조사
    public boolean hasNextProcess() {
        return index < arrivalTimes.length;
    }

}

// 메뉴를 화면에 보여 주고 선택된 메뉴 항목을 실행한다.
class MainMenu {
    public static void run() {
        Jobs jobs = null;

        while (true) {
            System.out.println("************************ Main Menu *******************");
            System.out.println("* 0.Exit  1.Jobs 2.Process                           *");
            System.out.println("* 3.FCFS  4.SPN  5.HRRN  6.SRT  7.RR(q=1)  8.RR(q=4) *");
            System.out.println("******************************************************");
            System.out.print("Menu item number? ");
            int idx = UI.nextInt();
            if (idx == 0) break;

            switch (idx) {
                case 1:
                    jobs = new Jobs(0); // 함수 인자 0은 특별한 의미 없음
                    break;
                default:
                    System.out.println("WRONG menu item\n");
                    break;
            }
            System.out.println();
        }
        System.out.println("Good bye.");
    }
}

public class Main {
    public static void run(Scanner sc) {
        UI.setScan(sc);
        MainMenu.run();
        sc.close();
    }

    public static void main(String[] args) {
        int chk = 1;
        if (chk != 0) new AutoCheck(chk, true).run();
        else
            run(new Scanner(System.in));
    }
}