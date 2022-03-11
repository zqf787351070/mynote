package JAVA.Example.java.JavaBase.Generics;

public class GenericsInterfaceImpl implements GenericsInterface<String>{

    String[] animals = new String[]{"cat", "dog", "frog", "fish"};
    int count;
    @Override
    public String next() {
        if (count == animals.length) count = 0;
        return animals[count ++];
    }

    // 主测试程序
    public static void main(String[] args) {
        GenericsInterfaceImpl genericsInterfaceImpl = new GenericsInterfaceImpl();
        System.out.println(genericsInterfaceImpl.next());
        System.out.println(genericsInterfaceImpl.next());
        System.out.println(genericsInterfaceImpl.next());
        System.out.println(genericsInterfaceImpl.next());
        System.out.println(genericsInterfaceImpl.next());
        System.out.println(genericsInterfaceImpl.next());
    }
}
