package Example.java.JavaBase.Interface;

@SuppressWarnings("deprecation")
public class Demo03 {
    public static void main(String[] args) {
        Student student = new Student();
        student.studyWithPaper(); // 出现删除线指示
        student.studyWithComputer();
    }
}

class Student {
    @Deprecated
    public void studyWithPaper() {
        System.out.println("使用纸笔学习！");
    }

    public void studyWithComputer() {
        System.out.println("使用电脑学习！");
    }
}
