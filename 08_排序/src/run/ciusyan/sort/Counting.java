package run.ciusyan.sort;

/**
 * 计数排序 —— 最简实现
 */
public class Counting extends Sort<Integer> {
    @Override
    protected void sort() {

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
