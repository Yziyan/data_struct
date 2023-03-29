package run.ciusyan.sort;

/**
 * 基数排序
 */
public class Radix extends Sort<Integer> {

    @Override
    protected void sort() {
        // 1、找最大值
        int max = array[0];
        for (int i = 0; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        // 2、对每一个基数进行计数排序
        for (int i = 1; i < max; i *= 10) {
            // 挨次传入 1 、 10、 100 ...
            // 对应着对 个位数、 十位数、 百位数 ... 进行计数排序
            countingSort(i);
        }
    }

    /**
     * 对每一个基数，进行计数排序
     * @param divider：某基数 = 数 / divider % 10
     */
    private void countingSort(int divider) {

        // 基数的范围就定在 [0, 9]
        int[] counts = new int[10];

        // 构建 counts
        for (int i = 0; i < array.length; i++) {
            // array[i] / divider % 10 ：取每一元素对应的基数
            counts[array[i] / divider % 10]++;
        }

        // 重构 counts
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }

        // 构建 newArr
        int[] newArr = new int[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            // array[i] / divider % 10 ：取每一元素对应的基数
            // array[i] / divider % 10 - 0 ：是arr元素在 counts 中的索引
            // --counts[array[i] / divider % 10] ：是 arr 元素在 newArr 中的索引
            newArr[--counts[array[i] / divider % 10]] = array[i];
        }

        // 重构 arr 数组
        for (int i = 0; i < newArr.length; i++) {
            array[i] = newArr[i];
        }
    }

}
