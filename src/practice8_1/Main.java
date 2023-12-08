package practice8_1;

import java.io.*;
import java.util.*;

class RainData {
    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    private String cityCode;
    private String cityName;

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    private String yearMonth;
    private double rain;

    public RainData(String cityCode, String cityName, String yearMonth, double rain) {
        this.rain = rain;
        this.yearMonth = yearMonth;
        this.cityCode = cityCode;
        this.cityName = cityName;
    }
}

/*
Raindata : 도시코드, 도시명, 년월, 강수량 변수를 가지는 클래스
RainDataManager : RainData 객체의 ArrayList 객체를 변수로 가지며 파일 읽기, 강수량 검색 등의 기능을 제공하는 클래스
RainDataMain : main 메소드를 가지는 클래스
 */

class RainDataManager {
    private String[][] cities = {{"108", "서울"}, {"159", "부산"}, {"133", "대전"},
            {"184", "제주"}, {"112", "인천"}, {"119", "수원"}, {"156", "광주"}, {"143", "대구"}};
    private ArrayList<RainData> data;

    public RainDataManager(ArrayList<RainData> data) {
        this.data = data;
    }


    // raindata.csv 파일을 읽어서 RainData 객체를 생성하고 data 리스트에 저장하는 메소드
    public int loadData() {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("data\\raindata.csv"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] tmp = line.split(",");
                // 년월, 도시코드, 강수량
                String cc = "";
                for (String[] s : cities) {
                    if (s[0].equals(tmp[1])) {
                        cc = s[1];
                    }
                }
                data.add(new RainData(tmp[1], cc, tmp[0], Double.parseDouble(tmp[2])));
            }
            bufferedReader.close();
        } catch (IOException e) {
            System.out.println("File not found.");
            return 0;
        }
        return 1;
    }

    // 매개변수로 받은 도시의 강수량 데이터를 출력하는 메소드. 매개변수는 강수량을 검색할 도시명이 입력된다.
    public void search(String city) {
        String a = null;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getCityName().equals(city)) {
                System.out.println(city + " => " + data.get(i).getYearMonth() + " : " + data.get(i).getRain());
                a = data.get(i).getCityName();
            }
        }
        if (a == null) {
            System.out.println("입력한 도시의 강수량 데이터는 존재하지 않습니다.");
        }
    }

    // 매개변수로 받은 도시의 최대 강수량을 반환하는 메소드. 매개변수는 강수량을 검색할 도시명이 입력된다.
    public double searchMaxRain(String city) {
        double max = 0;
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getCityName().equals(city)) {
                if (data.get(i).getRain() > max) {
                    max = data.get(i).getRain();
                }
            }
        }
        return max;
    }
}

public class Main {
    static Scanner scn = new Scanner(System.in);

    private static int menu() {
        System.out.println("[[[[ 도시별 월별 강수량 데이터 ]]]]");
        System.out.println("    1. 도시별 강수량 검색");
        System.out.println("    2. 도시별 최대 강수량 검색");
        System.out.println("    0. 종료");
        System.out.print("선택 >> ");
        int aa = scn.nextInt();
        return aa;
    }

    public static void main(String[] args) {
        // 메뉴를 보이고 입력받은 값에 따라 처리하는 구문
        while (true) {
            RainDataManager rainDataManager = new RainDataManager(new ArrayList<RainData>());
            if (rainDataManager.loadData() == 1) {
                switch (menu()) {
                    case 1:
                        System.out.print("도시명 : ");
                        String city = scn.next();
                        rainDataManager.search(city);
                        break;
                    case 2:
                        System.out.print("도시명 : ");
                        String city1 = scn.next();
                        double rain = rainDataManager.searchMaxRain(city1);
                        if (rain == 0) {
                            System.out.println("입력한 도시의 강수량 데이터는 존재하지 않습니다.");
                        } else
                            System.out.println(city1 + " 최대 강수량 = " + rain);
                        break;
                    case 0:
                        System.exit(0);
                }
            }
        }
    }
}