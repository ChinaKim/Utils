package com.kim.imageloader.sort;

/**
 * Created by kim on 16-11-10.
 */

/**
 * 八大排序算法:
 * 插入排序
 *      -直接插入排序
 *      -希尔排序
 * 选择排序
 *      -简单选择排序
 *      -堆排序
 * 交换排序
 *      -冒泡排序
 *      -快速排序
 * 归并排序
 * 基数排序
 */
public class Sort {

    /**
     * 插入排序(不带哨所)
     *
     * @param sortList
     */
    public static Integer[] insertSort(Integer[] sortList) {
        int len = sortList.length;
        for (int i = 1; i < len; i++) {
            if (sortList[i] < sortList[i - 1]) {
                int j = i - 1;
                int temp = sortList[i];
                while (j > 0 && temp < sortList[j]) {
                    sortList[j + 1] = sortList[j];
                    j--;
                }
                sortList[j + 1] = temp;
            }
        }
        return sortList;
    }


    public static void insertSortBinary(Integer[] sortList) {

    }




    /**
     * 选择排序
     * 每次找到未排序集合中最小值放在左边
     *
     * @param sorts
     * @return
     */
    public static int[] selectionSort(int[] sorts) {
        for (int i = 0; i < sorts.length; i++) {
            //将第一个数默认为最小数
            int min_index = i;
            for (int j = i + 1; j < sorts.length; j++) {
                if (sorts[min_index] > sorts[j]) {
                    min_index = j;
                }
            }
            //一轮循环后找到了最小数下标,将最小数移到最左边
            int temp = sorts[min_index];
            sorts[min_index] = sorts[i];
            sorts[i] = temp;
        }
        return sorts;
    }


    /**
     * 冒泡排序
     *
     * @param sorts
     * @return
     */
    public static int[] bubbleSort(int[] sorts) {
        for (int i = 0; i < sorts.length; i++) {
            for (int j = i + 1; j < sorts.length; j++) {
                if (sorts[i] > sorts[j]) {
                    int temp = sorts[j];
                    sorts[j] = sorts[i];
                    sorts[i] = temp;
                }
            }
        }
        return sorts;
    }

    /**
     * 快速排序(冒泡排序的改进)
     *设定一个key值,将数组按key值分两段,左端都比key小,右边都比key大
     *
     * @param sorts
     * @param low 0
     * @param high 数组最大下标
     * @return
     */
    public static int[] quickSort(int[] sorts, int low, int high) {
        int i = low;
        int j = high;
        int key = sorts[i];
        while (i < j) {
            int temp = 0;
            while (i < j && sorts[j] >= key) {
                j--;
            }
            temp = sorts[j];
            sorts[j] = sorts[i];
            sorts[i] = temp;
            while (i < j && sorts[i] <= key) {
                i++;
            }
            temp = sorts[i];
            sorts[i] = sorts[j];
            sorts[j] = temp;
        }
        sorts[i] = key;
        if (low < i - 1) {
            quickSort(sorts, low, i - 1);
        }
        if (high > i + 1) {
            quickSort(sorts, i + 1, high);
        }
        return sorts;
    }
}
