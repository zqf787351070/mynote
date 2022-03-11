package JAVA.Example.java.JavaBase.Interface;

public enum BirdNest {
    SPARROW;

    // 鸟类生成
    public Bird reproduce() {
        Desc desc = Sparrow.class.getAnnotation(Desc.class);
        return desc == null ? new Sparrow() : new Sparrow(desc.color());
    }
}
