package JAVA.Example.java.JavaBase.Interface;

public class Sparrow extends Bird{
    private Desc.Color color;

    public Sparrow() {
        this.color = Desc.Color.GRAYISH; // 无参构造默认为灰色
    }

    public Sparrow(Desc.Color color) {
        this.color = color;
    }

    @Override
    public Desc.Color getColor() {
        return color;
    }

    public static void main(String[] args) {
        Bird bird = BirdNest.SPARROW.reproduce();
        System.out.println("Bird's color is：" + bird.getColor());
    }
}
