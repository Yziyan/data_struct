package run.ciusyan.sort.cmp;

import run.ciusyan.Main;
import run.ciusyan.sort.Sort;

public class Quick<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {

        // 对 [0, array.length) 进行快速排序
        sort(0, array.length);
    }

    /**
     * 对 [begin, end) 进行快速排序
     */
    private void sort(int begin, int end) {
        if (end - begin < 2) return;

        // 找出轴点元素的位置
        int pivotIndex = pivotIndex(begin, end);

        // 对子序列进行快速排序
        sort(begin, pivotIndex);
        sort(pivotIndex+1, end);
    }

    /**
     * 构造出 [begin, end) 范围的轴点元素
     * @return ：轴点元素的最终位置
     */
    private int pivotIndex(int begin, int end) {
        // 为了减少最坏时间复杂度，我们随机选择一个序列中的元素作为轴点
        // [0, 1) -> [0, end - begin) -> [begin, end)
        swap(begin, (int) (begin + Math.random() * (end - begin)));

        // 将轴点元素备份
        E pivot = array[begin];

        // 将end指向最后一个元素，因为传入的是[begin, end)，不包括end
        end--;

        // 不断循环构建轴点左右的元素
        while (begin < end) {

            // 为了左右交替，嵌套 while 循环
            while (begin < end) {
                if (cmp(pivot, array[end]) < 0) { // 右边 > 轴点
                    end--;
                } else { // 右边 <= 轴点
                    array[begin++] = array[end];

                    // 这个break 代表要换方向了
                    break;
                }
            }

            // 为了左右交替，嵌套 while 循环
            while (begin < end) {
                if (cmp(pivot, array[begin]) > 0) { // 左边 < 轴点
                    begin++;
                } else { // 左边 >= 轴点
                    array[end--] = array[begin];

                    // 这个break 代表要换方向了
                    break;
                }
            }
        }

        // 来到这里，说明 begin = end = pivotIndex
        // 将轴点元素赋值给轴点的真正位置
        array[begin] = pivot;

        // 返回轴点元素的最终位置
        return begin;
    }
}
