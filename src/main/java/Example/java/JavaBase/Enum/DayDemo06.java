package Example.java.JavaBase.Enum;

/**
 * enum 类中定义抽象方法
 */
public class DayDemo06 {

    public enum Plant {
        GREEN {
            @Override
            public String getDesc() {
                return "小草";
            }
        }, TREE {
            @Override
            public String getDesc() {
                return "小树";
            }
        }, FLOWER {
            @Override
            public String getDesc() {
                return "小花";
            }
        };

        // 定义抽象方法
        public abstract String getDesc();
    }

    public static void main(String[] args) {
        for (Plant plant: Plant.values()) {
            System.out.println("desc：" + plant.getDesc());
        }
    }

}
