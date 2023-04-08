package run.ciusyan.dp;

/**
 * 最长公共子串
 */
public class LCSubstring {

    /**
     * 动态规划 + 二维数组
     */
    public int lcs(String str1, String str2) {
        if (str1 == null || str2 == null) return 0;
        char[] chars1 = str1.toCharArray();
        if (chars1.length == 0) return 0;
        char[] chars2 = str2.toCharArray();
        if (chars2.length == 0) return 0;

        char[] rows = chars1; // 行数组
        char[] cols = chars2; // 列数组

        // 用长度较小的串当作列
        if (cols.length > rows.length) {
            rows = chars2;
            cols = chars1;
        }

        // 动态规划状态
        int[] dp = new int[cols.length + 1];
        int max = 0;

        for (int row = 1; row <= rows.length; row++) {

            int cur = 0;
            for (int col = 1; col <= cols.length; col++) {
                // 一会可能用到的leftTop
                int leftTop = cur;
                cur = dp[col]; // 覆盖前，先保存，下一轮可能需要使用
                if (cols[col - 1] != rows[row - 1]) {
                    // 因为是一维数组了，需要覆盖
                    dp[col] = 0;
                    continue;
                }

                // 要用保存下来的 leftTop
                dp[col] = leftTop + 1;

                // 来到这里才可能比 max 还大
                max = Math.max(max, dp[col]);
            }
        }

        return max;
    }

    /**
     * 动态规划 + 二维数组
     */
    public int lcs1(String str1, String str2) {
        if (str1 == null || str2 == null) return 0;
        char[] chars1 = str1.toCharArray();
        if (chars1.length == 0) return 0;
        char[] chars2 = str2.toCharArray();
        if (chars2.length == 0) return 0;

        // 长度从 [1, length]
        int dp[][] = new int[chars1.length + 1][chars2.length + 1];
        int max = 0;

        for (int i = 1; i <= chars1.length; i++) {
            for (int j = 1; j <= chars2.length; j++) {
                // 必须从 i - 1开始比较，才能包括 char[0]
                if (chars1[i - 1] != chars2[j - 1]) continue; // 直接使用默认值即可，也就是 dp[i][j] = 0

                // 来到这里说明此次比较的最后一个字符相等
                dp[i][j] = dp[i - 1][j - 1] + 1;

                // 取 dp(i, j) 中的最大值
                max = Math.max(max, dp[i][j]);
            }
        }

        return max;
    }

}
