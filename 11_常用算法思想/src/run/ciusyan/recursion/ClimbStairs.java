package run.ciusyan.recursion;

/**
 * 跳台阶（爬楼梯）
 */
public class ClimbStairs {

    /**
     * 暴力递归做法
     */
    public int climbStairs1(int n) {
        if (n <= 2) return n;
        
        return climbStairs1(n - 1) + climbStairs1(n - 2);
    }

    /**
     * 局部变量 + 滑动 （非递归）
     */
    public int climbStairs2(int n) {
        if (n <= 2) return n;

        int first = 1;
        int second = 2;

        for (int i = 3; i <= n; i++) {
            second = second + first;
            first = second - first;
        }

        return second;
    }
}
