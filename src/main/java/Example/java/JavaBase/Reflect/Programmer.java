package Example.java.JavaBase.Reflect;

@ClassAnno(classDesc = "程序猿实体类")
public class Programmer {

    @FieldAnno(fieldDesc = "姓名")
    public String name;

    @FieldAnno(fieldDesc = "年龄")
    public int age;

    public Programmer() {}

    public Programmer(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @MethodAnno(methodDesc = "写代码")
    public void code() {
        System.out.println(name + " is coding...");
    }

    @MethodAnno(methodDesc = "调试代码")
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
