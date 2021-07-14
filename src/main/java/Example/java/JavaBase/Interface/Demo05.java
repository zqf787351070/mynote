package Example.java.JavaBase.Interface;

@MyAnnotation(code = "Java")
public class Demo05 {
    /**
     * 当不添加注解时，控制台什么都不会输出
     * 当添加注解之后，控制台输出如下：
     * id：ZQF
     * age：25
     * code：Java
     * 需要注意的是，如果一个注解要在运行时被成功提取，那么 @Retention(RetentionPolicy.RUNTIME) 是必须的
     */
    public static void main(String[] args) {
        boolean hasAnnotation = Demo05.class.isAnnotationPresent(MyAnnotation.class);
        if (hasAnnotation) {
            MyAnnotation annotation = Demo05.class.getAnnotation(MyAnnotation.class);
            System.out.println("id：" + annotation.name);
            System.out.println("age：" + annotation.age);
            System.out.println("code：" + annotation.code());
        }
    }

}
