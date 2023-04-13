package run.ciusyan.array;

/**
 * https://leetcode.cn/problems/sub-sort-lcci/
 */
public class 部分排序 {
    public int[] subSort(int[] array) {
        if (array == null) return null;
        if (array.length < 2) return new int[]{-1, -1};

        int max = array[0];
        int post = -1;
        for (int i = 1; i < array.length; i++) {

            // 从前往后，找到最后一个逆序对
            if (array[i] >= max) {
                max = array[i];
            } else {
                post = i;
            }
        }

        int min = array[array.length - 1];
        int prev = -1;
        for (int i = array.length - 2; i >= 0 ; i--) {
            if (array[i] <= min) {
                min = array[i];
            } else {
                prev = i;
            }
        }

        return new int[]{prev, post};
    }
}
