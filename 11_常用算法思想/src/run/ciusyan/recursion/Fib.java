package run.ciusyan.recursion;

/**
 * 斐波那契数列相关
 * 1 1 2 3 5 8 ... f(n - 1) + f(n - 2)
 */
public class Fib {

    /**
     * 暴力递归
     */
    public int fib0(int n) {
        if (n <= 2) return 1;

        return fib0(n - 1) + fib0(n - 2);
    }

    /**
     * 递归 + 记忆化
     */
    public int fib1(int n) {
        // 开辟 n + 1 大小的数组，用于记忆 以求的值
        // fib(1) = arr[i] 、 fib(n) = arr[n]，所以开辟 n + 1
        int[] arr = new int[n + 1];

        // 初始化 fib(1) fib(2)
        arr[1] = arr[2] = 1;

        return fib1(n, arr);
    }

    private int fib1(int n, int[] arr) {

        if (arr[n] == 0) {
            // 来到这里说明还未计算过
            arr[n] = fib1(n - 1, arr) + fib1(n - 2, arr);
        }

        return arr[n];
    }

    /**
     * 记忆化 、不递归
     */
    public int fib2(int n) {
        if (n <= 2) return 1;
        int[] arr = new int[n + 1];
        arr[1] = arr[2] = 1;

        // 先求 arr[3]、再求 arr[4]、再求
        for (int i = 3; i <= n; i++) {
            arr[i] = arr[i - 1] + arr[i - 2];
        }

        return arr[n];
    }

    /**
     * 滚动数组
     */
    public int fib3(int n) {
        if (n <= 2) return 1;
        // 只需要两个元素
        int[] arr = new int[2];

        // 初始化 fib(1) 和 fib(2)
        arr[0] = arr[1] = 1;

        for (int i = 3; i <= n; i++) {
            // arr[0] = 1 、fib(1) = 1
            // arr[1] = 1 、fib(2) = 1
            // arr[1] = arr[0] + arr[1] 、fib(3) = fib(1) + fib(2)
            // arr[0] = arr[1] + arr[0] 、fib(4) = fib(3) + fib(2)
            arr[i % 2] = arr[(i - 1) % 2 ] + arr[(i - 2) % 2 ];
        }

        return arr[n % 2];
    }

    /**
     * 滚动数组 、优化取模运算
     */
    public int fib4(int n) {
        if (n <= 2) return 1;

        int[] arr = new int[2];
        arr[0] = arr[1] = 1;

        for (int i = 3; i <= n; i++) {
            // 当 i % 2 时，可以将 % 2 -> & 1
            // 4 -> 0b 0100 & 0001 = 0000 -> 0
            // 3 -> 0b 0011 & 0001 = 0001 -> 1
            // 2 -> 0b 0010 & 0001 = 0000 -> 0
            arr[i & 1] = arr[(i - 1) & 1] + arr[(i - 2) & 1];
        }

        return arr[n & 1];
    }


    /**
     * 使用 2 个局部变量
     */
    public int fib5(int n) {
        if (n <= 2) return 1;

        int first = 1;
        int second = 1;

        for (int i = 3; i <= n; i++) {
            // first = fib(1)
            // second = fib(2)
            // second = fib(3) = first + second
            // first = fib(2) = fib(3) - fib(1) = second - first
            second = first + second;

            // 将 first = second 原先的值
            first = second - first;
        }

        return second;
    }

}
