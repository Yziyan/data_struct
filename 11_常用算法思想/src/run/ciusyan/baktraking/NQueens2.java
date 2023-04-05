package run.ciusyan.baktraking;

import java.util.LinkedList;
import java.util.List;

/**
 * n 皇后问题
 */
public class NQueens2 {

    /**
     * 需要放置多少皇后
     */
    private int qCounts;


    /**
     * 记录是如何放置的 queens[row] = col
     *  (row, col) 有皇后
     */
    private int[] queens;

    /**
     * 记录某一列，是否已经有皇后了
     */
    private boolean[] cols;

    /**
     * 记录某一对角线是否有皇后了（从左上角 -> 右下角） row - col + n - 1
     */
    private boolean[] leftTop;

    /**
     * 记录某一对角线是否有皇后了（从右上角 -> 左下角） row + col
     */
    private boolean[] rightTop;

    /**
     * 多少种放置方法
     */
    private int ways;

    private List<List<String>> result = new LinkedList<>();

    public NQueens2(int n) {
        this.qCounts = n;
        queens = new int[n];

        // 初始化 n 列
        cols = new boolean[n];
        // 有 2n - 1 条 对角线
        leftTop = new boolean[(n << 1) - 1];
        rightTop = new boolean[leftTop.length];
    }

    /**
     * 放置皇后
     * @return ：有多少种放法
     */
    public int placeQueues() {
        if (qCounts < 1) return 0;
        // 从 0 行开始放置
        plate(0);

        return ways;
    }

    /**
     * 在第几行，放置皇后
     */
    private void plate(int row) {
        if (row == qCounts) {
            // 说明最后一行的皇后，都已经放置了
            ways++;
            show();
            return;
        }

        // 尝试将皇后放入 第 col 列
        for (int col = 0; col < qCounts; col++) {
            // 列有皇后
            if (cols[col]) continue;

            // 左斜线有皇后
            int leftIndex = row - col + qCounts - 1;
            if (leftTop[leftIndex]) continue;
            // 左斜线有皇后
            int rightIndex = row + col;
            if (rightTop[rightIndex]) continue;

            // 来到这里，说明能放置皇后，将三个标志位置初始化
            cols[col] = true;
            leftTop[leftIndex] = true;
            rightTop[rightIndex] = true;
            queens[row] =col;

            // 然后直接去下一行放置皇后
            // 下一行也是做一样的操作，所以直接递归即可
            plate(row + 1);

            // 但是在回溯的时候，要将其状态重置
            cols[col] = false;
            leftTop[leftIndex] = false;
            rightTop[rightIndex] = false;
        }
    }

    public List<List<String>> getResult() {
        return result;
    }

    private void show() {

        for (int row = 0; row < qCounts; row++) {
            List<String> resCols = new LinkedList<>();
            for (int col = 0; col < qCounts; col++) {
                if (queens[row] != col) {
                    // 说明没有皇后
                    System.out.print("0     ");
                    resCols.add(".");
                } else {
                    System.out.print("1     ");
                    resCols.add("Q");
                }
            }
            System.out.println();
            result.add(resCols);
        }
        System.out.println();
    }

}
