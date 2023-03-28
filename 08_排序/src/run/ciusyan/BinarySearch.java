package run.ciusyan;

/**
 * 二分搜索
 */
public class BinarySearch {

    /**
     * 查找 v 在有序数组中的位置
     */
    public static int indexOf(int[] array, int v) {
        if (array == null || array.length == 0) return -1;
        int begin = 0;
        int end = array.length;
        int mid;
        int midEle;
        while (begin < end) {
            mid = (begin + end) >> 1;
            midEle = array[mid];
            if (v == midEle) return mid;

            if (v < midEle) {
                end = mid;
            } else { // v > midEle
                begin = mid + 1;
            }
        }

        return -1;
    }
}
