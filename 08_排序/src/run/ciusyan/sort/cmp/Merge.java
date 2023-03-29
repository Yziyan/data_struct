package run.ciusyan.sort.cmp;

import run.ciusyan.sort.Sort;

/**
 * 归并排序
 * @param <E>
 */
public class Merge<E extends Comparable<E>> extends Sort<E> {

    private E[] leftArray;

    @Override
    protected void sort() {
        // [begin, length >> 1)
        // [length >> 1, end)
        leftArray = (E[]) new Comparable[array.length >> 1];
        sort(0, array.length);
    }


    /**
     * 将 [begin, end) 的元素进行归并排序
     */
    private void sort(int begin, int end) {

        // 当数组元素(end - begin) 小于 2 时退出递归
        if (end - begin < 2) return;

        int mid = (end + begin) >> 1;

        // 将左边进行归并排序
        sort(begin, mid); // [begin, mid)

        // 将右边进行归并排序
        sort(mid, end); // [mid, end)

        // 将最终的两个子序列合并
        merge(begin, mid, end);
    }

    /**
     * 将 [begin, mid) 和 [mid, end) 两个子序列进行合并
     */
    private void merge(int begin, int mid, int end) {
        int li = 0, le = mid - begin; // li：左子序列待操作的索引，le：左子序列结束的位置
        int ri = mid, re = end; // ri：右子序列待操作的索引，re：右子序列结束的位置
        int ai = begin; // 最终有序序列的待操作索引

        // 将左子序列备份
        for (int i = 0; i < le; i++) {
            // 注意这里是将 array[begin + i] 的元素进行挪动
            leftArray[i] = array[begin + i];
        }

        // 挨个比较，当左子序列处理完毕后，就可以结束操作了。
        // 右子序列就什么也不用做了
        while (li < le) {

            // 注意这里的写法，稍不注意，可能就是一个不稳定的排序算法，
            // 还有注意：如果先处理完毕右子序列后，直接挪动左子序列的元素即可。
            if (ri < re && cmp(array[ri], leftArray[li]) < 0) {
                array[ai++] = array[ri++];
            } else { // array[ri] >= leftArray[li]
                array[ai++] = leftArray[li++];
            }
        }
    }
}
