package run.ciusyan.Top;

/**
 * https://leetcode.cn/problems/reverse-integer/
 */
public class _7_整数反转 {
    public int reverse(int x) {
        // 构建一个新的数字
        int res = 0;

        while (x != 0) {
            int oldRes = res;

            // 每次都取个位数
            int unit = x % 10;

            // 挨次构建新数字的基数
            res = (res * 10) + unit;

            // 如果翻转前后，res 可互推，说明没有溢出，否则说明已经溢出了
            if (oldRes != (res - unit) / 10) return 0;

            // 使用完，就去掉最后一个数字
            x /= 10;
        }

        return res;
    }

    public static void main(String[] args) {
        _7_整数反转 o = new _7_整数反转();
        System.out.println(o.reverse(-123));
    }
}
