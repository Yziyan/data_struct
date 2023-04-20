package run.ciusyan.动态规划;


/**
 * https://leetcode.cn/problems/longest-palindromic-substring/
 */
public class _5_最长回文子串 {


    /**
     * 动态规划 + 一维数组
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {
        if (s == null) return null;
        char[] chars = s.toCharArray();
        if (chars.length <= 1) return s;

        // 最长回文子串的起始位置，默认是第 1 个字符
        int begin = 0;
        // 最长回文子串的长度，默认为是 1 个字符
        int maxLen = 1;
        int n = chars.length;

        // 定义一维数组，因为每次都只是用到了下一行的结果
        boolean[] dp = new boolean[n];

        // 外层循环从最后一行开始算
        // 内层循环从最后，往前计算，到斜线那
        for (int row = n - 1; row >= 0; row--) {
            for (int col = n - 1; col >= row ; col--) {
                boolean firstAndLast = chars[row] == chars[col];
                int subStrLen = col - row + 1;

                if (subStrLen <= 2) {
                    dp[col] = firstAndLast;
                } else {
                    // 说明字符大于2个

                    dp[col] = dp[col - 1] && firstAndLast;
                }

                if (dp[col] && subStrLen > maxLen) {
                    begin = row;
                    maxLen = subStrLen;
                }
            }
        }

        return new String(chars, begin, maxLen);
    }

    /**
     * 动态规划 + 二维数组
     */
    public static String longestPalindrome2(String s) {
        if (s == null) return null;
        char[] chars = s.toCharArray();
        int n = chars.length;
        if (n <= 1) return s;

        // 最长回文子串的起始位置，默认是第 1 个字符
        int begin = 0;
        // 最长回文子串的长度，默认为是 1 个字符
        int maxLen = 1;

        boolean[][] dp = new boolean[n][n];

        // 两层 循环：
        //  外层从倒数第二行开始
        //  内层从 row 开始，相当于是从斜线开始，斜线下方的是不合理字符
        for (int row = n - 1; row >= 0 ; row--) {

            for (int col = row; col < n; col++) {

                // 首位两个字符是否相等
                boolean firstAndLast = chars[row] == chars[col];

                // 此次子串的长度
                int subStrLen = col - row + 1;
                if (subStrLen <= 2) {
                    // col - row <= 1 <-> col - row + 1 <= 2
                    // 这里说明，此次子串 只有一个字符 or 只有两个字符，两种情况
                    dp[row][col] = firstAndLast;
                } else {
                    // 来到这里说明多于两个字符
                    // 中间子串是回文子串，并且首尾两个字符要相等，才说明现在的字串是回文子串
                    dp[row][col] = dp[row + 1][col - 1] && firstAndLast;
                }

                // 说明此空格是回文子串，并且比之前最长地回文子串还长
                if (dp[row][col] && subStrLen > maxLen) {
                    // 记录起始位置、最新的长度
                    begin = row;
                    maxLen = subStrLen;
                }
            }
        }

        return new String(chars, begin, maxLen);
    }

    /**
     * 暴力解法，
     */
    public static String longestPalindrome1(String s) {
        if (s == null) return null;
        char[] chars = s.toCharArray();
        if (chars.length <= 1) return s;

        int begin = 0;
        int maxLen = 1;

        for (int i = 0; i < chars.length; i++) {
            for (int j = i + 1; j < chars.length; j++) {
                int len = j - i + 1;
                if (isPalindrome(chars, i, j) && len > maxLen) {
                    begin = i;
                    maxLen = len;
                }
            }
        }

        return new String(chars, begin, maxLen);
    }

    /**
     * 判断 chars 中，从 [begin, end] 位置。是否为回文串
     */
    private static boolean isPalindrome(char[] chars, int begin, int end) {
        while (begin < end) {
            if (chars[begin] != chars[end]) return false;

            begin++;
            end--;
        }

        return true;
    }

    public static void main(String[] args) {
        System.out.println(longestPalindrome("ccc"));
    }
}
