package run.ciusyan.Top;

/**
 * https://leetcode.cn/problems/powx-n/
 */
public class _50_Pow {
    public double myPow(double x, int n) {
        if (x == 0 || x == 1) return x;

        // 递归基
        // 任何数的 0 次幂 = 1
        if (n == 0) return 1;
        // 任何数的 1 次幂 = 它本身
        if (n == 1) return x;
        // 但是是负数的话，最多只能到 -1 次幂
        if (n == -1) return 1 / x;

        // 先计算出 x ^ n/2
        double half = myPow(x, n >> 1);

        // 肯定是需要使用 x ^ n/2 乘 x ^ n/2 的
        half *= half;

        // 如果是奇次幂，那么还需要 * x
        return ((n & 1) == 1) ? half * x : half;
    }
}
