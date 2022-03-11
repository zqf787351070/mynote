package JAVA.Example.java.Java8;

public class LambdaDemo01 {

    class MyThread01 implements Runnable {
        @Override
        public void run() {
            System.out.println("我是“局部内部类”创建的线程...");
        }
    }

    static class MyThread02 implements Runnable {
        @Override
        public void run() {
            System.out.println("我是“静态内部类”创建的线程...");
        }
    }

    public static void main(String[] args) {
        // 1. 使用匿名内部类创建线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("我是“匿名内部类“创建的线程...");
            }
        }).start(); // 我是“匿名内部类“创建的线程...

        // 2. 使用局部内部类创建线程
        LambdaDemo01 lambdaDemo01 = new LambdaDemo01();
        MyThread01 myThread01 = lambdaDemo01.new MyThread01();
        new Thread(myThread01).start(); // 我是“局部内部类”创建的线程...

        // 3. 使用静态内部类创建线程
        MyThread02 myThread02 = new MyThread02();
        new Thread(myThread02).start(); // 我是“静态内部类”创建的线程...

        // 4. 使用 Lambda 表达式创建线程
        new Thread(() -> {
            System.out.println("我是”Lambda表达式“创建的线程...");
        }).start(); // 我是”Lambda表达式“创建的线程...
    }
}
