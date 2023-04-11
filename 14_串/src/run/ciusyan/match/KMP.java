package run.ciusyan.match;

/**
 * KMP 算法
 */
public class KMP {
    public static int indexOf(String text, String pattern) {
        if (text == null || pattern == null) return -1;
        char[] textChars = text.toCharArray();
        int tlen = textChars.length;
        if (tlen == 0) return -1;
        char[] patternChars = pattern.toCharArray();
        int plen = patternChars.length;
        if (plen == 0) return -1;
        if (plen > tlen) return -1;

        // 获取next表
        int[] next = next(patternChars);
        int ti = 0, pi = 0;

        while (pi < plen && ti < tlen) {
            if (pi < 0 || textChars[ti] == patternChars[pi]) {
                // 说明此字符相等，或者需要将 pi 回到 0 号位置
                // 取下一个字符比较
                pi++;
                ti++;
            } else{
                // 说明不相等，那么取出从next表中，取出跳跃多少个字符比较
                // 也就说明此时在 pi 位置失配了
                pi = next[pi];
            }
        }

        // 来到这里，如果是扫描完模式串，说明找到了
        return (pi == plen) ? ti - pi : -1;
    }


    /**
     * 优化next表
     */
    private static int[] next(char[] patternChars) {
        int len = patternChars.length;
        int[] dp = new int[len];

        dp[0] = -1;
        int n = dp[0];
        int i = 0;
        int iMax = len - 1;

        while (i < iMax) {

            if (n < 0 || patternChars[i] == patternChars[n]) {
                ++i;
                ++n;

                // 加完之后看，下一个是否会相等，如果下一个相等了，说明之后也还是会失配
                if (patternChars[i] == patternChars[n]) {
                    // 说明这里会跟随前面的值，因为他们都是相等的，如果后面的失配，那么一路走过来，
                    // 直接跳到最前面来比较，才可能不失配，所以直接存储前面的值即可。
                    dp[i] = dp[n];
                } else {
                    // 如果不等，下一次才可能不失配，因为与上一次比较的不同
                    dp[i] = n;
                }
            } else {
                // 说明失配了
                n = dp[n];
            }
        }

        return dp;
    }

    /**
     * 根据 pattern 获取next表
     */
    private static int[] next1(char[] patternChars) {
        int len = patternChars.length;

        int[] dp = new int[len];

        // 设置第一位为 -1
        dp[0] = -1;
        int n = dp[0];
        int i = 0;
        int iMax = len - 1;

        while (i < iMax) {
            // 如果索引合理，就查看  patternChars[i] 与前面有没有计算出来的
            if (n < 0 || patternChars[i] == patternChars[n]) {
                dp[++i] = ++n;
            } else {
                n = dp[n];
            }
        }

        return dp;
    }
}
