package run.ciusyan.dp;

/**
 * 0-1 背包问题
 */
public class Knapsack {


    /**
     * 0-1背包 + 恰好装满
     */
    public int selectExactly(int[] values, int[] weights, int capacity) {
        if (values == null || values.length == 0) return 0;
        if (weights == null || weights.length == 0) return 0;
        if (values.length != weights.length || capacity < 1) return 0;

        // 定义状态
        int dp[] = new int[capacity + 1];
        // 定义初始值
        for (int i = 1; i < dp.length; i++) {
            // 将每一项都设置为负无穷
            dp[i] = Integer.MIN_VALUE;
        }

        for (int i = 1; i <= values.length; i++) {
            for (int j = capacity; j >= weights[i - 1] ; j--) {
                dp[j] = Math.max(dp[j], values[i - 1] + dp[j - weights[i - 1]]);
            }
        }

        return dp[capacity] < 0 ? -1 : dp[capacity];
    }


    /**
     * 可以提前退出循环
     */
    public int select(int[] values, int[] weights, int capacity) {
        if (values == null || values.length == 0) return 0;
        if (weights == null || weights.length == 0) return 0;
        if (values.length != weights.length || capacity < 1) return 0;

        // 只需要定义一维的即可，但是这里只能以容量为长度
        int[] dp = new int[capacity + 1];
        for (int i = 1; i <= values.length; i++) {
            // 需要倒叙求解
            // 说明此件物品太重了，装不了，只能选前 i - 1项装，也就是 dp[j] = dp[j]
            // 所以如果当 j 开始小于第 i 件物品重量时，之后在 j--，之后的重量肯定也会小，所以也会是跳过循环
            // 即可以提前离开循环的
            for (int j = capacity; j >= weights[i - 1]; j--) {
                // 不选的价值：dp[j]
                // 选了的价值：values[i - 1] + dp[j - weights[i - 1]]
                dp[j] = Math.max(dp[j], values[i - 1] + dp[j - weights[i - 1]]);
            }
        }

        return dp[capacity];
    }

    /**
     * 动态规划 + 一维数组
     */
    public int select2(int[] values, int[] weights, int capacity) {
        if (values == null || values.length == 0) return 0;
        if (weights == null || weights.length == 0) return 0;
        if (values.length != weights.length || capacity < 1) return 0;

        // 只需要定义一维的即可，但是这里只能以容量为长度
        int[] dp = new int[capacity + 1];
        for (int i = 1; i <= values.length; i++) {

            // 需要倒叙求解
            for (int j = capacity; j >= 1; j--) {
                // 说明此件物品太重了，装不了，只能选前 i - 1项装，也就是 dp[j] = dp[j]
                // 所以可以跳过
                if (j < weights[i - 1]) continue;

                // 不选的价值：dp[j]
                // 选了的价值：values[i - 1] + dp[j - weights[i - 1]]
                dp[j] = Math.max(dp[j], values[i - 1] + dp[j - weights[i - 1]]);
            }
        }

        return dp[capacity];
    }

        /**
         * 找出最大价值
         * @param values：价值
         * @param weights：重量
         * @param capacity：总容量
         */
    public int select1(int[] values, int[] weights, int capacity) {
        if (values == null || values.length == 0) return 0;
        if (weights == null || weights.length == 0) return 0;
        if (values.length != weights.length || capacity < 1) return 0;

        // 定义状态
        int[][] dp = new int[values.length + 1][capacity + 1];

        for (int i = 1; i <= values.length; i++) {
            for (int j = 1; j <= capacity; j++) {
                if (j < weights[i - 1]) {
                    // 说明这件物品已经超出最大承重了，只能看前 i - 1 项怎么装了
                    dp[i][j] = dp[i - 1][j];
                } else {
                    // 说明能装，看选了值不值
                    //      dp[i - 1][j]：这是代表不选，直接看前 i - 1 项的价值
                    //      values[i] + dp[i - 1][j - weights[i]]：这是代表选了，
                    //      看前 i - 1 项的价值 + 这件物品的价值
                    dp[i][j] = Math.max(dp[i - 1][j], values[i - 1] + dp[i - 1][j - weights[i - 1]]);
                }
            }
        }

        // 返回 n 件物品中，最大承重为 Capacity 的最大价值
        return dp[values.length][capacity];
    }
}
