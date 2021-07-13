package Example.java.JavaBase.Enum;

public class DayDemo08 {

    public enum Person implements Eat, Sport{
        EAT, SPORT, PLAY, SLEEP;

        @Override
        public void eat() {
            System.out.println("eat......");
        }

        @Override
        public void sport() {
            System.out.println("sport......");
        }
    }

}

interface Eat {
    void eat();
}

interface Sport {
    void sport();
}

