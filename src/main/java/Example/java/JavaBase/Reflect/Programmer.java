package Example.java.JavaBase.Reflect;

public class Programmer {

    private String name;

    private int age;

    public Programmer() {}

    public Programmer(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void code() {
        System.out.println(name + " is coding...");
    }

    public void debug() {
        System.out.println(name + " is debugging...");
    }

    @Override
    public String toString() {
        return "Programmer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
