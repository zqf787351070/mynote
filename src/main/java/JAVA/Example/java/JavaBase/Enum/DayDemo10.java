package JAVA.Example.java.JavaBase.Enum;

import java.util.*;

/**
 * EnumMap
 */
public class DayDemo10 {

    public static void main(String[] args) {
        List<Clothes> clothesList = new ArrayList<>();
        clothesList.add(new Clothes("C001", DayDemo05.Color.BLUE));
        clothesList.add(new Clothes("C002", DayDemo05.Color.RED));
        clothesList.add(new Clothes("C003", DayDemo05.Color.YELLOW));
        clothesList.add(new Clothes("C004", DayDemo05.Color.BLUE));
        clothesList.add(new Clothes("C005", DayDemo05.Color.YELLOW));
        clothesList.add(new Clothes("C006", DayDemo05.Color.BLUE));
        clothesList.add(new Clothes("C007", DayDemo05.Color.RED));
        clothesList.add(new Clothes("C008", DayDemo05.Color.BLUE));
        clothesList.add(new Clothes("C009", DayDemo05.Color.RED));
        clothesList.add(new Clothes("C010", DayDemo05.Color.RED));
        // 方案1：HashMap
        Map<String, Integer> HashMap = new HashMap<>();
        for (Clothes clothes: clothesList) {
            String color = clothes.getColor().name();
            Integer count = HashMap.get(color);
            if (count != null) {
                HashMap.put(color, count + 1);
            } else {
                HashMap.put(color, 1);
            }
        }
        System.out.println("==========方案1：HashMap");
        System.out.println(HashMap.toString()); // {RED=4, BLUE=4, YELLOW=2}
        // 方案2：EnumMap
        Map<DayDemo05.Color, Integer> EnumMap = new EnumMap<>(DayDemo05.Color.class);
        for (Clothes clothes: clothesList) {
            DayDemo05.Color color = clothes.getColor();
            Integer count = EnumMap.get(color);
            if (count != null) {
                EnumMap.put(color, count + 1);
            } else {
                EnumMap.put(color, 1);
            }
        }
        System.out.println("==========方案2：EnumMap");
        System.out.println(HashMap.toString()); // {RED=4, BLUE=4, YELLOW=2}
    }

}

class Clothes {

    private String name;
    private DayDemo05.Color color;

    public Clothes(String name, DayDemo05.Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public DayDemo05.Color getColor() {
        return color;
    }
}
