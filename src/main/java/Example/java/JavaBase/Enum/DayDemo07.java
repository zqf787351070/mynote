package Example.java.JavaBase.Enum;

import java.util.Arrays;

public class DayDemo07 {

    public static void main(String[] args) {
        // 未向上转型时，正常调用
        Day[] days = Day.values();
        Enum<Day> e = Day.MONDAY;
        // e.values() 无法调用，无此方法。
        Class<?> declaringClass = e.getDeclaringClass();
        if (declaringClass.isEnum()) {
            Day[] dsz = (Day[]) declaringClass.getEnumConstants();
            System.out.println("dsz：" + Arrays.toString(dsz));
        }
    }

}
