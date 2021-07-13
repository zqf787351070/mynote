package Example.java.JavaBase.Enum;

/**
 * 枚举类型定义变量
 */
public class DayDemo02 {

    public static void main(String[] args) {
        // 创建枚举数组
        Day[] days = new Day[]{Day.MONDAY, Day.TUESDAY, Day.WEDNESDAY, Day.THURSDAY,
                Day.FRIDAY, Day.SATURDAY, Day.SUNDAY};
        // ordinal() 方法
        System.out.println("======== ordinal() 方法");
        for (int i = 0; i < days.length; i ++ ) {
            System.out.println("day[" + i + "].ordinal():" + days[i].ordinal());
        }
        // compareTo() 方法
        System.out.println("======== compareTo() 方法");
        System.out.println("days[0].compareTo(days[1]):" + days[0].compareTo(days[1]));
        System.out.println("days[0].compareTo(days[1]):" + days[0].compareTo(days[2]));
        // getDeclaringClass() 方法
        System.out.println("======== getDeclaringClass() 方法");
        Class<Day> clazz = days[3].getDeclaringClass();
        System.out.println("class:" + clazz);
        // name() 方法，toString() 方法
        System.out.println("======== name() 方法，toString() 方法");
        System.out.println(days[4].name());
        System.out.println(days[5].name());
        System.out.println(days[6].toString());
        // valueOf() 方法
        System.out.println("======== valueOf() 方法");
        Day d1 = Enum.valueOf(Day.class, days[1].name());
        Day d2 = Day.valueOf(days[2].name());
        System.out.println("d1:" + d1);
        System.out.println("d2:" + d2);
    }
}

// 定义枚举类型
enum Day {
    MONDAY, TUESDAY, WEDNESDAY, THURSDAY,
    FRIDAY, SATURDAY, SUNDAY
}