package run.ciusyan.动态规划;

import java.util.Map;

/**
 * https://leetcode.cn/problems/li-wu-de-zui-da-jie-zhi-lcof/
 */
public class 剑指Offer47_礼物的最大价值 {

    /**
     * 一维数组求解
     */
    public int maxValue(int[][] grid) {
        if (grid == null) return 0;
        int rowLen = grid.length;
        if (rowLen == 0) return 0;
        int colLen = grid[0].length;

        // 一行一行的计算
        int dp[] = new int[colLen];
        dp[0] = grid[0][0];
        // 初始化第一行的数据
        for (int col = 1; col < colLen; col++) {
            dp[col] = dp[col - 1] + grid[0][col];
        }

        for (int row = 1; row < rowLen; row++) {
            // 初始化第一个数据
            dp[0] = dp[0] + grid[row][0];

            for (int col = 1; col < colLen; col++) {
                dp[col] = Math.max(dp[col - 1], dp[col]) + grid[row][col];
            }
        }

        return dp[colLen - 1];
    }

    public int maxValue1(int[][] grid) {
        if (grid == null) return 0;
        int rowLen = grid.length;
        if (rowLen == 0) return 0;
        int colLen = grid[0].length;

        // 定义 dp
        int[][] dp = new int[rowLen][colLen];
        dp[0][0] = grid[0][0];

        // 最终结果
        int max = dp[0][0];

        // 初始化 dp(0, j)
        for (int j = 1; j < colLen; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];

            max = Math.max(max, dp[0][j]);
        }

        // 初始化 dp(i, 0)
        for (int i = 1; i < rowLen; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];

            max = Math.max(max, dp[i][0]);
        }

        // 遍历求解：
        for (int row = 1; row < rowLen; row++) {
            for (int col = 1; col < colLen; col++) {
                dp[row][col] = Math.max(dp[row - 1][col], dp[row][col - 1]) + grid[row][col];

                max = Math.max(max, dp[row][col]);
            }
        }

        return max;
    }
}
