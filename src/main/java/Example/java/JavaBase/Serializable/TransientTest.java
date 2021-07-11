package Example.java.JavaBase.Serializable;

import java.io.*;

public class TransientTest {

    public static void main(String[] args) {
        try {
            SerializeUser();
            DeSerializeUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 序列化
    public static void SerializeUser() throws IOException {
        User user = new User("Leo Zhu", "epiroc123");
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/data.txt"));
        oos.writeObject(user);
        oos.close();
        System.out.println("普通字段序列化:" + user.getUsername());
        System.out.println("transient 字段序列化:" + user.getPassword());
    }

    // 反序列化
    public static void DeSerializeUser() throws IOException, ClassNotFoundException {
        File file = new File("/data.txt");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        User user = (User) ois.readObject();
        System.out.println("普通字段反序列化:" + user.getUsername());
        System.out.println("transient 字段反序列化:" + user.getPassword());
    }

}
