package DataStructuresandAlgorithms.Algorithms.code;

import java.util.Arrays;

/**
 * 插入排序
 */
public class InsertSort {
    public static void main(String[] args) {
        int[] array = new int[]{1, 3, 5, 0, 6, -1, -3, -6, 9};
        insertSort(array);
        System.out.println(Arrays.toString(array));
    }

    public static void insertSort(int[] array) {
        int insertVal, insertPreIdx;
        for (int i = 1; i < array.length - 1; i ++) {
            insertPreIdx = i - 1;
            insertVal = array[i];
            while (insertPreIdx >= 0 && insertVal < array[insertPreIdx]) {
                array[insertPreIdx + 1] = array[insertPreIdx];
                insertPreIdx --;
            }
            if (insertPreIdx + 1 != i)
                array[insertPreIdx + 1] = insertVal;
        }
    }
}
