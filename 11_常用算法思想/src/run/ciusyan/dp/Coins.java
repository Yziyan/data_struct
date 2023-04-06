package run.ciusyan.dp;

/**
 * 零钱兑换
 */
public class Coins {


    public int coinsChange(int n, int[] faces) {
        if (n == 0) return 0;
        if (n < 1 || faces == null || faces.length == 0) return -1;

        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {

            int min = Integer.MAX_VALUE;
            // 尝试用每一个面值找零
            for (int face : faces) {
                // 说明此面值太大了，换一个面值
                if (face > i) continue;

                // 如果 dp[i - face] 是 -1，那么也没必要用这个找零了
                if (dp[i - face] < 0 || dp[i - face] >= min) continue;
                min = dp[i - face];
            }

            if (min == Integer.MAX_VALUE) {
                // 来到这里，说明上面根本每一零钱可以用于找零，那就标为 -1 吧
                dp[i] = -1;
            } else {
                dp[i] = min + 1;
            }
        }

        return dp[n];
    }

    /**
     * 递推，顺带打印是如何找零的
     */
    public int coinsChange5(int n) {
        if (n < 1) return -1;

        // 用于记录每一零钱，需要几枚硬币 dp[n] = 3 -> 代表，找零n元钱，需要 3 枚硬币
        int[] dp = new int[n + 1];
        // 用于记录零钱是如何找的， faces[n] = 25 -> 代表，找零n元钱，最后一枚硬币是 25
        int[] faces = new int[dp.length];
        for (int i = 1; i <= n; i++) {
            int min = dp[i - 1];
            faces[i] = 1;

            if (i >= 5 && dp[i - 5] < min) {
                min = dp[i - 5];
                faces[i] = 5;
            }

            if (i >= 20 && dp[i - 20] < min) {
                min = dp[i - 20];
                faces[i] = 20;
            }

            if (i >= 25 && dp[i - 25] < min) {
                min = dp[i - 25];
                faces[i] = 25;
            }

            // print(i, faces);
            dp[i] = min + 1;
        }
        print(n, faces);

        return dp[n];
    }

    private void print(int n, int[] faces) {
        System.out.println("【" + n + "】所需");
        while (n > 0) {
            System.out.print(faces[n] + "、");
            n -= faces[n];
        }
        System.out.println();
    }

    /**
     * 递推，（自底向上）
     */
    public int coinsChange4(int n) {
        if (n < 1) return -1;

        int[] dp = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            // dp[i] = min{dp[i-25], dp[i-20], dp[i-5], dp[i-1]} + 1
            // 先这样算出前面的每一项，就可以用这些小的项，去递推算出更大的项
            // 但是得对数组索引做边界处理

            // int min = Integer.MAX_VALUE;
            // if (i >= 1) min = Math.min(min, dp[i - 1]);
            // 这两句可以写成下面的形式，因为第一次上来，肯定是 dp[i - 1] 要小
            int min = dp[i - 1];

            if (i >= 5) min = Math.min(min, dp[i - 5]);
            if (i >= 20) min = Math.min(min, dp[i - 20]);
            if (i >= 25) min = Math.min(min, dp[i - 25]);

            dp[i] = min + 1;
        }

        return dp[n];
    }

    /**
     * 记忆化搜索，递归，但尽量不暴力
     */
    public int coinsChange2(int n) {
        // 这是防止外界乱传的边界处理
        if (n < 1) return -1;

        // 用于找零的面值
        int[] faces = {1, 5, 20, 25};

        // 用于记忆已经计算出来的值：
        //      比如：dp[25] = 1 -> 需要找零 25 分的时候，只需要找零 1 枚硬币即可
        int[] dp = new int[n + 1];
        // 初始化几个已知值
        for (int face : faces) {
            // 如果找零的 都没有 face这么大，那么就不需要初始化这么大的零钱了
            if (face > n) break;

            dp[face] = 1;
        }

        return coinsChange2(n, dp);
    }

    private int coinsChange2(int n, int[] dp) {
        // 这是为了递归调用时，如果 n-xx = 负数时，说明此结果不可取
        // 也就是递归终止条件
        if (n < 1) return Integer.MAX_VALUE;

        if (dp[n] == 0) {
            // 说明还没计算过，计算并且记忆化
            int min1 = Math.min(coinsChange2(n - 25, dp), coinsChange2(n - 20, dp));
            int min2 = Math.min(coinsChange2(n - 5, dp), coinsChange2(n - 1, dp));
            dp[n] = Math.min(min1, min2) + 1;
        }

        // 说明已经记忆过了，可以直接取了
        return dp[n];
    }

    /**
     * 暴力递归，（自顶向下的计算）
     */
    public int coinsChange1(int n) {
        // 如果 n - xx = 负数，说明不能用这个面值，返回一个最大值，到时候 min(MAX, xx) -> xx
        if (n < 1) return Integer.MAX_VALUE;
        if (n == 25 || n == 20 || n == 5 || n == 1) return 1;

        int min1 = Math.min(coinsChange1(n - 25), coinsChange1(n - 20));
        int min2 = Math.min(coinsChange1(n - 5), coinsChange1(n - 1));

        // dp(n) = min {dp(n-25), dp(n-20), dp(n-5), dp(n-1)} + 1
        return Math.min(min1, min2) + 1;
    }

}
