package run.ciusyan.baktraking;

import java.util.LinkedList;
import java.util.List;

/**
 * n 皇后问题
 */
public class NQueens {

    /**
     * 需要放置多少皇后
     */
    private int qCounts;

    /**
     * 皇后怎么放
     *      如 cols[3] = 6：在第 3 行，第 6 列，有一个皇后
     */
    private int[] cols;

    /**
     * 多少种放置方法
     */
    private int ways;

    private List<List<String>> result = new LinkedList<>();

    public NQueens(int n) {
        this.qCounts = n;

        // 初始化 n 列
        cols = new int[n];
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
            if (!isValidate(row, col)) continue;

            // 来到这里，说明 此时的 (row, col)能放置皇后
            cols[row] = col; // 将皇后放置在 (row, col)里面

            // 然后直接去下一行放置皇后
            // 下一行也是做一样的操作，所以直接递归即可
            plate(row + 1);
        }
    }

    /**
     * 查看第 row 行、 第 col 列，能否放置皇后 (row, col)
     *      这里类似与剪枝操作
     */
    private boolean isValidate(int row, int col) {

        // 尝试查看之前的列，和斜线 有没有被放置皇后
        // 因为我们是往下一行放置皇后，所以行不需要考虑

        for (int i = 0; i < row; i++) {
            // 说明 (i, col)，已经有皇后了（col 列）
            if (cols[i] == col) {
                System.out.println("(" + row + ", " + col + ") = false");
                return false;
            }

            // 再判断斜线有无皇后
            // 因为是对角线，也就是判断斜率是否为 +1 or -1 (45°)
            // 看为直角坐标系：A (i, cols[i]) 和 B (row, col)
            // 那么 AB 的斜率是 k = col - cols[i] / row - i
            // 如果是 k = -1 or +1 ，说明在一条斜线，
            // 也就是 col - cols[i] / +- (row - i) <=> row - i = -+(col - cols[i])
            // 为什么要换呢？因为 row - i 不会出现负数
            // 也就是 row - i = |col - cols[i]|
            if (row - i == Math.abs(col - cols[i])) {
                System.out.println("(" + row + ", " + col + ") = false");
                return false;
            }
        }

        System.out.println("(" + row + ", " + col + ") = true");

        // 说明行和斜线都没有皇后，不需要剪枝

        return true;
    }

    public List<List<String>> getResult() {
        return result;
    }

    private void show() {

        for (int row = 0; row < qCounts; row++) {
            List<String> resCols = new LinkedList<>();
            for (int col = 0; col < qCounts; col++) {
                if (cols[row] != col) {
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
