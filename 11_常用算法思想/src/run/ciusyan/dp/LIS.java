package run.ciusyan.dp;

/**
 * 最长递增子序列
 */
public class LIS {

    /**
     * 牌顶法 —— 二分搜索
     */
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        // 记录牌顶元素
        int[] tops = new int[nums.length];
        int len = 0;

        // 放置每一张牌
        for (int num : nums) {
            int begin = 0;
            int end = len;
            // 说明还可以继续查找
            while (begin < end) {
                int mid = (begin + end) >> 1;
                if (tops[mid] >= num) {
                    //说明手里的牌要小一点，说明要往前摆放
                    end = mid;
                } else {
                    // 说明要往后摆放
                    begin = mid + 1;
                }
            }
            // 到达这里，说明 begin == end，这个就是此张牌，该摆放的位置
            tops[begin] = num;
            // 但是需要判断，如果此位置是最后一个，说明是新加的牌堆
            if (begin == len) {
                len++;
            }
        }

        return len;
    }

    /**
     * 牌顶法
     */
    public int lengthOfLIS2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        // 记录每一牌堆的牌堆元素
        int[] tops = new int[nums.length];
        // 记录长度
        int len = 0;

        // 将每一张牌摆到对应的牌堆
        for (int num : nums) {
            int i = 0;
            while (i < len) {
                if (num <= tops[i]) {
                    // 说明这张牌小于对应牌堆的牌顶元素，可以放入上面
                    tops[i] = num;
                    break;
                }
                // 说明这一牌堆放不了，看看下一牌堆
                i++;
            }

            // 如果 i 等于len 的长度，说明前面都没有适合自己的牌堆，新建一个
            if (i == len) {
                tops[len++] = num;
            }
        }

        return len;
    }

    /**
     * 动态规划
     */
    public int lengthOfLIS1(int[] nums) {
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
