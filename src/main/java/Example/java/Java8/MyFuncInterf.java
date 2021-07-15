package Example.java.Java8;

/**
 * 自定义函数式接口
 */
@FunctionalInterface
public interface MyFuncInterf<T> {
    public T getValue(String origin);
}
