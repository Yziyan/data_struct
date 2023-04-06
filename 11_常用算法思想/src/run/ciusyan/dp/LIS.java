package run.ciusyan.dp;

/**
 * 最长递增子序列
 */
public class LIS {

    /**
     * 动态规划
     */
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int[] dp = new int[nums.length];

        // 设置默认值
        dp[0] = 1;
        int max = dp[0];

        for (int i = 1; i < dp.length; i++) {
            // 为了方便，将其默认值设置为 1
            dp[i] = 1;

            // 还得遍历 dp(i) 前面的序列
            for (int j = 0; j < i; j++) {
                // 说明前面的数，比 i 位置的大，不满足递增序列，跳过看下一个
                if (nums[j] >= nums[i]) continue;

                // 用默认长度 与 是 dp(j) 的 LIS + 1 比较
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }

            // 选择所有 dp(i) 中，LIS 最大的那个
            max = Math.max(max, dp[i]);
        }

        return max;
    }
}
