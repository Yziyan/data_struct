package run.ciusyan.dp;

/**
 * 最长子数组之和
 */
public class MaxSubArray {

    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int dp = nums[0];
        int max = dp;

        for (int i = 1; i < nums.length; i++) {
            if (dp <= 0) {
                dp = nums[i];
            } else {
                dp += nums[i];
            }

            max = Math.max(dp, max);
        }

        return max;
    }

    /**
     * 动态规划 1
     */
    public int maxSubArray1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int[] dp = new int[nums.length];

        dp[0] = nums[0];
        int max = dp[0];
        for (int i = 1; i < dp.length; i++) {

            if (dp[i - 1] <= 0) {
                // 说明 dp(i - 1) 对后面每贡献，还可能拉低整体的值
                dp[i] = nums[i];
            } else {
                dp[i] = dp[i - 1] + nums[i];
            }

            // dp(i) 的最大值，是结果
            max = Math.max(max, dp[i]);
        }

        return max;
    }
}
