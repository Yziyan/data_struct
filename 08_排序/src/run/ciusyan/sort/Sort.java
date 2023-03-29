package run.ciusyan.sort;

import run.ciusyan.model.Student;
import run.ciusyan.sort.cmp.Shell;

import java.text.DecimalFormat;

public abstract class Sort<E extends Comparable<E>> implements Comparable<Sort<E>> {
    /**
     * 排序的数组
     */
    protected E[] array;
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
    public void sort(E[] array) {
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
    public int compareTo(Sort<E> o) {
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

        return array[i1].compareTo(array[i2]);
    }

    /**
     * 用于比较两个元素的大小
     */
    protected int cmp(E e1, E e2) {
        cmpCount++;

        return e1.compareTo(e2);
    }

    /**
     * 交换两个元素的位置
     */
    protected void swap(int i1, int i2) {
        swapCount++;

        E temp = array[i1];
        array[i1] = array[i2];
        array[i2] = temp;
    }

    @Override
    public String toString() {
        String timeStr = "耗时：" + (time / 1000.0) + "s(" + time + "ms)";
        String compareCountStr = "比较：" + numberString(cmpCount);
        String swapCountStr = "交换：" + numberString(swapCount);
        String stableStr = "稳定性：" + isStable();
        return "【" + getClass().getSimpleName() + "】\n"
            + stableStr + " \t"
            + timeStr + " \t"
            + compareCountStr + "\t "
            + swapCountStr + "\n"
            + "------------------------------------------------------------------";

    }

    private boolean isStable() {
        if (this instanceof Radix) return true;
        if (this instanceof Counting) return true;
        if (this instanceof Shell) return false;

        Student[] students = new Student[20];
        for (int i = 0; i < students.length; i++) {
            students[i] = new Student(i * 10, 21);
        }

        // 将其排序
        sort((E[]) students);

        for (int i = 1; i < students.length; i++) {
            if (students[i].getScore() - students[i - 1].getScore() != 10) return false;
        }

        return true;
    }

    private String numberString(int number) {
        if (number < 10000) return "" + number;

        if (number < 100000000) return fmt.format(number / 10000.0) + "万";

        return fmt.format(number / 100000000.0) + "亿";
    }

}
