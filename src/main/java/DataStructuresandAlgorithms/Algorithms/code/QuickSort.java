package DataStructuresandAlgorithms.Algorithms.code;

import java.util.Arrays;

/**
 * 快速排序
 */
public class QuickSort {
    public static void main(String[] args) {
        int[] array = new int[]{1, 3, 5, 0, 6, -1, -3, -6, 9};
        quickSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array));
    }

    public static void quickSort(int[] array, int left, int right) {
        int l = left, r = right, temp = 0;
        int midVal = array[(l + r) >> 1];
        while (l < r) {
            while (array[l] < midVal) l++;
            while (array[r] > midVal) r--;
            if (l >= r) break;
            temp = array[l];
            array[l] = array[r];
            array[r] = temp;
            if (array[l] == midVal) r--;
            if (array[r] == midVal) l++;
        }
        if (l == r) {
            l++;
            r--;
        }
        if (r > left) quickSort(array, left, r);
        if (l < right) quickSort(array, l, right);
    }
}
