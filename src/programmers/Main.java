package programmers;

import java.util.Calendar;
import java.util.Scanner;

class Calen {

    protected Calendar date = Calendar.getInstance();

    public String aa() {
        int ampm = date.get(Calendar.AM_PM);
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        int day = date.get(Calendar.DAY_OF_MONTH);
        int hour = date.get(Calendar.HOUR);
        int minute = date.get(Calendar.MINUTE);
        int second = date.get(Calendar.SECOND);
        if (ampm == Calendar.AM) {
            return "(" + year + "." + month + "." + day + " AM " + hour + ":" + minute + ":" + second + ")";
        }
        return "(" + year + "." + month + "." + day + " PM " + hour + ":" + minute + ":" + second + ")";
        // (2023.10.18 PM 4:4:58)
    }
}

public class Main {
    public static void main(String[] args) {
        var test = new Calen();
        System.out.println(test.aa());
    }
}