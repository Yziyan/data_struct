package run.ciusyan.sort;

import java.util.ArrayList;
import java.util.List;

public class Shell<E extends Comparable<E>> extends Sort<E> {
    @Override
    protected void sort() {
        // 生成步长序列
        List<Integer> stepSequence = shellStepSequence();

        // 对每一列进行插入排序
        for (Integer step : stepSequence) {
            sort(step);
        }
    }

    /**
     * 对 step 列，分别进行插入排序
     * @param step：有多少列
     */
    private void sort(int step) {
        // col：代表每一列
        // 对每一列进行插入排序
        for (int col = 0; col < step; col++) {

            // 0, 1, 2, 3, 4 ...
            // col, col + step, col + 2step, col + 3step ...
            for (int begin = col + step; begin < array.length; begin += step) {
                int current = begin;
                while (current > col && cmp(array[current], array[current - step]) < 0) {
                    swap(current, current - step);
                    current -= step;
                }
            }
        }
    }

    private List<Integer> shellStepSequence() {
        List<Integer> steps = new ArrayList<>();
        int step = array.length;

        while ((step >>= 1) > 0) {
            steps.add(step);
        }

        return steps;
    }

}
