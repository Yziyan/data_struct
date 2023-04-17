package run.ciusyan.字符串;

/**
 * https://leetcode.cn/problems/string-rotation-lcci/
 */
public class 面试题01_09_字符串轮转 {
    public boolean isFlipedString(String s1, String s2) {
        if (s1 == null || s2 == null) return false;
        if (s1.length() != s2.length()) return false;

        // 这里可以换成 KMP 算法，可能会有一定的优化
        return (s1 + s2).contains(s2);
    }
}
