package run.ciusyan.sort;

/**
 * 计数排序
 */
public class Counting extends Sort<Integer> {
    @Override
    protected void sort() {
        // 1、寻找最值
        int max = array[0];
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            // 找最大值
            if (array[i] > max) {
                max = array[i];
            }

            // 找最小值
            if (array[i] < min) {
                min = array[i];
            }
        }

        // 2、构建 counts 数组
        int[] counts = new int[max - min + 1];
        for (int i = 0; i < array.length; i++) {
            // 将 arr[i] - min 作为 counts 的索引，次数为 counts 的值
            counts[array[i] - min]++;
        }

        // 3、重构 counts 数组
        for (int i = 1; i < counts.length; i++) {
            // 将前面的次数累加到后面
            counts[i] += counts[i - 1];
        }

        // 4、逆序遍历 arr，给 newArr 赋值
        int[] newArr = new int[array.length];
        for (int i = array.length - 1; i >= 0 ; i--) { // 从后往前遍历 arr
            // array[i] - min] ：arr 的元素在 counts 中的索引 ci
            // counts[array[i] - min] ：应该将 arr[i] 元素 放置在 newArr 中的什么位置 ni
            newArr[--counts[array[i] - min]] = array[i];
        }

        // 5、重构 arr 序列
        for (int i = 0; i < newArr.length; i++) {
            array[i] = newArr[i];
        }
    }

    /**
     *  —— 最简实现
     */
    private void sort01() {
        // 1、寻找最大值
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }

        // 2、构建新数组
        int[] counts = new int[max + 1];
        for (int i = 0; i < array.length; i++) {
            // 将序列值作为索引，出现次数作为值
            counts[array[i]]++;
        }

        // 3、利用新数组调整序列顺序
        int curIndex = 0;
        for (int i = 0; i < counts.length; i++) {
            while (counts[i]-- > 0) {
                // 将值给序列
                array[curIndex++] = i;
            }
        }
    }
}
