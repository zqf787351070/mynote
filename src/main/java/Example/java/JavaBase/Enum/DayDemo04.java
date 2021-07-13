package Example.java.JavaBase.Enum;

/**
 * 向 enum 类中添加方法和自定义构造函数
 */
public class DayDemo04 {

    public enum Year {
        SPRING("春"), SUMMER("夏"), AUTUMN("秋"), WINTER("冬");

        // 定义属性：中文描述
        private String desc;

        // 私有化构造函数，禁止外部调用
        private Year(String desc) {
            this.desc = desc;
        }

        // desc 的 get() 方法
        public String getDesc() {
            return desc;
        }
    }

    public static void main(String[] args) {
        for (Year year: Year.values()) {
            System.out.println("name：" + year.name() + "；desc：" + year.getDesc());
        }
    }

}
