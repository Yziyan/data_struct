package run.ciusyan.Top;

/**
 * https://leetcode.cn/problems/powx-n/
 */
public class _50_Pow {


    /**
     * 非递归实现快速幂
     */
    public double myPow(double x, int n) {
        if (x == 0 || x == 1) return x;

        // 如果是 负次幂，将其按照正次幂算，返回的结果倒一下即可
        //  这里使用 long，是害怕 负的 int，会溢出
        long y = (n < 0) ? -((long)n) : n;
        double res = 1.0;

        // 如果最前面一位二进制都右移完了，说明 y < 0
        while (y > 0) {
            if ((y & 1) == 1) {
                // 来到这里，说明此次的结果是
                res *=  x;
            }

            // 利用此次的结果，计算下一个幂次
            // x -> x^2 -> x^4 -> x^8 -> x^(2^n)
            x *= x;

            // 每次都用最后一位来判断，是否对结果有贡献
            y >>= 1;
        }

        // 如果是负次幂，结果导一下
        return (n < 0) ? 1 / res : res;
    }


    /**
     * 递归实现快速幂
     */
    public double myPow1(double x, int n) {
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
