package Example.java.JavaBase.Enum;

import java.util.Arrays;

public class DayDemo03 {

    public static void main(String[] args) {
        Day[] days = Day.values();
        System.out.println("======== values() 方法");
        System.out.println("days: " + Arrays.toString(days));
        Day day = Day.valueOf("SUNDAY");
        System.out.println("======== valueOf() 方法");
        System.out.println("day: " + day);
    }

}
