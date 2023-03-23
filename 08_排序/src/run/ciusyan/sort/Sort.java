package run.ciusyan.sort;

import java.text.DecimalFormat;

public abstract class Sort implements Comparable<Sort> {
    /**
     * 排序的数组
     */
    protected Integer[] array;
    /**
     * 用于统计排序用时
     */
    private long time;
    /**
     * 该算法进行比较的次数
     */
    private int cmpCount;
    /**
     * 该算法进行交换的次数
     */
    private int swapCount;
    private DecimalFormat fmt = new DecimalFormat("#.00");

    /**
     * 给外界使用的方法
     * @param array：需要排序的元素
     */
    public void sort(Integer[] array) {
        if (array == null || array.length <= 2) return;

        this.array = array;

        long begin = System.currentTimeMillis();
        // 真正的排序在子类中实现
        sort();
        this.time = System.currentTimeMillis() - begin;
    }

    /**
     * 用于子类实现的排序算法
     */
    protected abstract void sort();


    @Override
    public int compareTo(Sort o) {
        int res = (int) (time - o.time);
        if (res != 0) return res;

        res = cmpCount - o.cmpCount;
        if (res != 0) return res;

        return swapCount - o.swapCount;
    }

    /**
     * 用于比较两个索引位置元素的大小
     * @return
     *  0：  array[i1] = array[i2]
     * 正数：array[i1] > array[i2]
     * 负数：array[i1] < array[i2]
     */
    protected int cmp(int i1, int i2) {
        cmpCount++;

        return array[i1] - array[i2];
    }

    /**
     * 用于比较两个元素的大小
     */
    protected int cmpEle(Integer e1, Integer e2) {
        cmpCount++;

        return e1 - e2;
    }

    /**
     * 交换两个元素的位置
     */
    protected void swap(int i1, int i2) {
        swapCount++;

        Integer temp = array[i1];
        array[i1] = array[i2];
        array[i2] = temp;
    }

    @Override
    public String toString() {
        String timeStr = "耗时：" + (time / 1000.0) + "s(" + time + "ms)";
        String compareCountStr = "比较：" + numberString(cmpCount);
        String swapCountStr = "交换：" + numberString(swapCount);
        return "【" + getClass().getSimpleName() + "】\n"
            + timeStr + " \t"
            + compareCountStr + "\t "
            + swapCountStr + "\n"
            + "------------------------------------------------------------------";

    }

    private String numberString(int number) {
        if (number < 10000) return "" + number;

        if (number < 100000000) return fmt.format(number / 10000.0) + "万";

        return fmt.format(number / 100000000.0) + "亿";
    }

}
