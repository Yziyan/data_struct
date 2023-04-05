package run.ciusyan.baktraking;

import java.util.LinkedList;
import java.util.List;

/**
 * n 皇后问题
 */
public class Queens3 {

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
     * 记录某一列，是否已经有皇后了 0110 1100
     *  代表 . Q Q . Q Q . .
     */
    private byte cols;

    /**
     * 记录某一对角线是否有皇后了（从左上角 -> 右下角） row + col
     */
    private short leftTop;

    /**
     * 记录某一对角线是否有皇后了（从右上角 -> 左下角） row - col + n - 1
     */
    private short rightTop;

    /**
     * 多少种放置方法
     */
    private int ways;

    private List<List<String>> result = new LinkedList<>();

    public Queens3(int n) {
        if (n > 8) n = 8;

        this.qCounts = n;
        queens = new int[n];
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
            int cv = 1 << col;
            // 列有皇后
            if ((cols & cv) != 0) continue;

            // 左斜线有皇后
            int lv = 1 << (row - col + qCounts - 1);
            if ((leftTop & lv) != 0) continue;
            // 左斜线有皇后
            int rv = 1 << (row + col);
            if ((rightTop & rv) != 0) continue;

            // 来到这里，说明能放置皇后，将三个标志位置初始化
            //      将某一位 置为 1
            cols |= cv;
            leftTop |= lv;
            rightTop |= rv;
            queens[row] =col;

            // 然后直接去下一行放置皇后
            // 下一行也是做一样的操作，所以直接递归即可
            plate(row + 1);

            // 但是在回溯的时候，要将其状态重置
            //      将 某一位 置为 0
            cols  &= ~cv;
            leftTop &= ~lv;
            rightTop &= ~rv;
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
