package JAVA.Example.java.JavaBase.Generics;

/**
 * 此处T可以随便写为任意标识，常见的如T、E、K、V等形式的参数常用于表示泛型
 * 在实例化泛型类时，必须指定T的具体类型
 */
public class GenericsClass<T> {
    // 成员变量 key 的类型为 T，T 的类型由外部指定
    private T key;

    public GenericsClass(T key) {
        this.key = key;
    }

    public T getKey() {
        return key;
    }

    // 主方法测试
    public static void main(String[] args) {
        GenericsClass<String> genericsClass01 = new GenericsClass<>("Leo Zhu");
        GenericsClass<Integer> genericsClass02 = new GenericsClass<>(123456);
        System.out.println("泛型测试，key=" + genericsClass01.getKey());
        System.out.println("泛型测试，key=" + genericsClass02.getKey());
    }

}
