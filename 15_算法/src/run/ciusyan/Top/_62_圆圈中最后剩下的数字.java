package run.ciusyan.Top;

/**
 * https://leetcode.cn/problems/yuan-quan-zhong-zui-hou-sheng-xia-de-shu-zi-lcof/
 */
public class _62_圆圈中最后剩下的数字 {


    /**
     * 非递归实现
     * 我们能不能自底向上的求解呢？
     * dp(1) = 0 -> dp(2) = (dp(1) + m) % 2 -> dp(3) = (dp(2) + m) % 3
     * dp(n) = (dp(n-1) + m) % n
     */
    public int lastRemaining(int n, int m) {
        // 代表不合理的情况
        if (n < 0 || m < 0) return -1;

        // dp(1) = 0
        int dp = 0;
        for (int i = 2; i <= n; i++) {
            // i 是代表有几个人玩，至少要有 2 个人玩
            dp = (dp + m) % i;
        }

        return dp;
    }

    /**
     * 递归实现
     * 这里是自顶向下的求解：
     * f(n) -> f(n-1) -> f(n-2) -> f(n-3)
     */
    public int lastRemaining1(int n, int m) {
        // 代表不合理的情况
        if (n < 0 || m < 0) return -1;

        // 如果只剩一个人玩，那么它就是能够存活，那么返回它的编号
        if (n == 1) return 0;

        // 递归调用递推式
        return (lastRemaining(n - 1, m) + m) % n;
    }
}
