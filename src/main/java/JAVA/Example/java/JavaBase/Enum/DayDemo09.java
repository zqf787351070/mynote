package JAVA.Example.java.JavaBase.Enum;

/**
 * 枚举与 switch
 */
public class DayDemo09 {

    public static void printName(DayDemo05.Color color) {
        switch (color) {
            case RED:
                System.out.println("红色");
                break;
            case BLUE:
                System.out.println("蓝色");
                break;
            case YELLOW:
                System.out.println("黄色");
                break;
        }
    }

    public static void main(String[] args) {
        printName(DayDemo05.Color.BLUE); // 蓝色
        printName(DayDemo05.Color.RED); // 红色
        printName(DayDemo05.Color.YELLOW); // 黄色
    }

}
