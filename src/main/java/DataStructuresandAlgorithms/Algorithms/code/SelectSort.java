package DataStructuresandAlgorithms.Algorithms.code;

import java.util.Arrays;

/**
 * 选择排序
 */
public class SelectSort {
    public static void main(String[] args) {
        int[] array = new int[]{1, 3, 5, 0, 6, -1, -3, -6, 9};
        selectSort(array);
        System.out.println(Arrays.toString(array));
    }

    public static void selectSort(int[] array) {
        int minIndex, minValue, temp;
        for (int i = 0; i < array.length - 1; i++) {
            minValue = array[i];
            minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < minValue) {
                    minValue = array[j];
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                array[minIndex] = array[i];
                array[i] = minValue;
            }
        }
    }

}
