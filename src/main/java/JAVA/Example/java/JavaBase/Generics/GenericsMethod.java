package JAVA.Example.java.JavaBase.Generics;

public class GenericsMethod {

    public static void main(String[] args) {
        try {
            Object test01 = genericMethod(GenericsTest01.class);
            System.out.println(test01);
            Object test02 = genericMethod(GenericsTest02.class);
            System.out.println(test02);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static <T> T genericMethod(Class<T> tClass) throws InstantiationException, IllegalAccessException {
        T instance = tClass.newInstance();
        return instance;
    }
}
