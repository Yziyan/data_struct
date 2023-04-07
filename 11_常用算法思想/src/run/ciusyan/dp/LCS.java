package run.ciusyan.dp;

/**
 * 最长公共子序列
 */
public class LCS {

    public int longestCommonSubsequence(String text1, String text2) {
        if (text1 == null) return 0;
        if (text2 == null) return 0;
        char[] chars1 = text1.toCharArray();
        if (chars1.length == 0) return 0;
        char[] chars2 = text2.toCharArray();
        if (chars2.length == 0) return 0;

        // 行数组
        char[] rowChars = chars1;
        // 列数组
        char[] colChars = chars2;

        // 挑选长度小的为 列数组
        if (rowChars.length < colChars.length) {
            // 说明列数组要长一些，换一下
            rowChars = chars2;
            colChars = chars1;
        }

        // 动态规划的数组
        int[] dp = new int[colChars.length + 1];

        for (int i = 1; i <= rowChars.length; i++) {
            int cur = 0;
            for (int j = 1; j <= colChars.length; j++) {
                int leftTop = cur;
                cur = dp[j];

                if (rowChars[i - 1] == colChars[j - 1]) {
                    dp[j] = leftTop + 1;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
            }
        }

        return dp[colChars.length];
    }

    /**
     * 动态规划 3 + 滑动数组 （1行）+ 相对列
     */
    public int lcs(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0) return 0;
        if (nums2 == null || nums2.length == 0) return 0;

        // 行数组
        int[] rowNums = nums1;
        // 列数组
        int[] colNums = nums2;

        if (rowNums.length < colNums.length) {
            // 说明列比行多，交换一下，变成更少的行
            rowNums = nums2;
            colNums = nums1;
        }

        // 只要一维的即可
        int[] dp = new int[colNums.length + 1];

        // 来到这里，肯定是只有更少的列了
        for (int i = 1; i <= rowNums.length; i++) {
            int cur = 0;
            for (int j = 1; j <= colNums.length; j++) {
                int leftTop = cur;
                cur = dp[j]; // 保存可能给下一轮的 leftTop 使用
                if (rowNums[i - 1] == colNums[j - 1]) {
                    // 说明 行和列的最后一个元素相等，
                    dp[j] = leftTop + 1;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
            }
        }

        return dp[colNums.length];
    }

    /**
     * 动态规划 2 + 滑动数组 （1行）
     */
    public int lcs4(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0) return 0;
        if (nums2 == null || nums2.length == 0) return 0;

        // 只要一维的即可
        int[] dp = new int[nums2.length + 1];

        for (int i = 1; i <= nums1.length; i++) {
            // 用于记录 dp(i)，要不然会被覆盖了
            int cur = 0;
            for (int j = 1; j <= nums2.length; j++) {
                // 先将上一次的leftTop，左上角元素先记录下来
                int leftTop = cur;
                cur = dp[j];
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[j] = leftTop + 1;
                } else {
                    // 前一个和现在的比较
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }

            }
        }

        return dp[nums2.length];
    }



    /**
     * 动态规划 2 + 滑动数组（2行）
     */
    public int lcs3(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0) return 0;
        if (nums2 == null || nums2.length == 0) return 0;

        // 只需要两行即可
        int[][] dp = new int[2][nums2.length + 1];
        for (int i = 1; i <= nums1.length; i++) {
            for (int j = 1; j <= nums2.length; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i & 1][j] = dp[((i - 1) & 1)][j - 1] + 1;
                } else {
                    dp[i & 1][j] = Math.max(dp[i&1][j - 1], dp[(i - 1)&1][j]);
                }
            }
        }

        return dp[nums1.length & 1][nums2.length];
    }


    /**
     * 动态规划 1
     */
    public int lcs2(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0) return 0;
        if (nums2 == null || nums2.length == 0) return 0;

        // 定义状态 dp(i, j) -> nums1 前 i 个元素  与 nums2 前 j 个元素的LCS
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];

        // 一行一行的计算出
        for (int i = 1; i <= nums1.length; i++) {
            for (int j = 1; j <= nums2.length; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    // 说明两个序列最后一个元素相等
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i - 1][j]);
                }

                System.out.println("dp[" + i + "][" + j + "] = " + dp[i][j]);
            }
        }

        return dp[nums1.length][nums2.length];
    }

    /**
     * 递归实现
     */
    public int lcs1(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 0) return 0;
        if (nums2 == null || nums2.length == 0) return 0;

        // 求出 两个序列长度的LCS
        return lcs1(nums1, nums1.length, nums2, nums2.length);
    }

    /**
     * 求出 nums1 前 i 个元素 和 nums2 前 j 个元素的 LCS
     */
    private int lcs1(int[] nums1, int i, int[] nums2, int j) {
        if (i == 0 || j == 0) return 0;

        if (nums1[i - 1] == nums2[j - 1]) {
            // 说明两个序列最后一个元素相同
            return lcs1(nums1, i - 1, nums2, j - 1) + 1;
        }

        return Math.max(lcs1(nums1, i, nums2, j - 1), lcs1(nums1, i - 1, nums2, j));
    }


}
