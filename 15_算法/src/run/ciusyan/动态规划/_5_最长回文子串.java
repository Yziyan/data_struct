package run.ciusyan.动态规划;


/**
 * https://leetcode.cn/problems/longest-palindromic-substring/
 */
public class _5_最长回文子串 {

    public static String longestPalindrome(String s) {
        if (s == null) return null;
        char[] chars = s.toCharArray();
        if (chars.length <= 1) return s;

        // 默认起始位置
        int begin = 0;
        // 默认最长回文子串的长度
        int maxLen = 1;

        int i = 0;
        while (i < chars.length) {
            // 先计算出扩展中心左边的位置
            int l = i - 1;
            // 扩展中心右边
            int r = i;

            // 循环，找出第一个不等于 现在扩展中心的字符的位置
            while (++r < chars.length && chars[i] == chars[r]);

            // 之后的扩展中心，是 r
            i = r;

            // 这里需要循环比较，但需要保证不越界
            while (l >= 0 && r <chars.length && chars[l] == chars[r]) {
                l--;
                r++;
            }

            // 来到这里，此次能够找到的回文子串已经出来了
            int len = r - l - 1;
            if (len > maxLen) {
                maxLen = len;
                // 起始位置就是现在的 l + 1
                begin = l + 1;
            }
        }

        return new String(chars, begin, maxLen);
    }
    /**
     * 扩展中心法 1
     */
    public static String longestPalindrome4(String s) {
        if (s == null) return null;
        char[] chars = s.toCharArray();
        if (chars.length <= 1) return s;

        // 默认起始位置
        int begin = 0;
        // 默认最长回文子串的长度
        int maxLen = 1;

        // 从第一个元素没必要扩展、最后一个元素也没必要扩展
        for (int i = 1; i < chars.length - 1; i++) {
            // 此次扩展的回文子串最大长度
            int len = Math.max(
                palindromeLen(chars, i - 1, i), // 偶数个，从缝隙开始扩展
                palindromeLen(chars, i - 1, i + 1) // 奇数个，直接从元素开始扩展
            );

            // 找到最大长度了，看看有没有比原先还长
            if (len > maxLen) {
                // 说明比原先还长，更新起始索引、最大长度
                // 起始位置 = 开始扩展位置 - 扩展后的长度 / 2
                begin = i - (len >> 1);

                maxLen = len;
            }
        }
        // 但是上面，还有最后一个空隙没有扩展
        if (chars[chars.length - 1] == chars[chars.length - 2] && maxLen < 2) {
            // 最后一个空隙，扩展后最大的距离也只能是 2，

            // 表明上面扩展后，都没有第一个空隙这么长，
            begin = chars.length - 2;
            maxLen = 2;
        }

        return new String(chars, begin, maxLen);
    }

    /**
     * 求出 chars 中，从 l 和 r 开始，能向左和向右扩展的最大长度
     */
    private static int palindromeLen(char[] chars, int l, int r) {
        // 这个说明，右边不会越界 && 左边不会越界 && l 、r 位置的字符相等
        while (r < chars.length && l >= 0 && chars[l] == chars[r]) {
            l--;
            r++;
        }

        // 来到这里，要么是右边越界了，要么是左边越界了，要么是扩展到不相等的字符了，返回最终的长度
        return r - l - 1;
    }

    /**
     * 动态规划 + 一维数组
     */
    public static String longestPalindrome3(String s) {
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
        System.out.println(longestPalindrome("cbbd"));
        System.out.println(longestPalindrome("babad"));
    }
}
