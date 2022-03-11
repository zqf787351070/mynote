package JAVA.Example.java.JavaBase.Enum;

/**
 * 覆盖 Enum 类方法
 */
public class DayDemo05 {

    public enum Color {
        RED("红"), YELLOW("黄"), BLUE("蓝");

        private String desc;

        private Color(String desc) {
            this.desc = desc;
        }

        // 重写父类 Enum 的 toString() 方法
        @Override
        public String toString() {
            return this.desc;
        }
    }

    public static void main(String[] args) {
        for (Color color: Color.values()) {
            System.out.println("toString：" + color.toString());
        }
    }

}
