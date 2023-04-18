package run.ciusyan.字符串;

import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * https://leetcode.cn/problems/reverse-words-in-a-string/
 */
public class _151_反转字符串中的单词 {

    /**
     * 手写实现（去除空格 + 逆序操作）
     */
    public static String reverseWords(String s) {
        if (s == null) return "";
        char[] chars = s.toCharArray();

        // 去除空格，找出有效字符
        int cur = 0;
        // 记录空格是否是有效的，true 代表无效的，当做 -1 位置有一个哨兵。那个空格一定是无效的
        boolean space = true;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != ' ') {
                // 说明此位置是有效字符
                chars[cur++] = chars[i];
                space = false;
            } else if (!space){
                // 代表是有效空格
                // 将空格挪过来
                chars[cur++] = ' ';

                // 之后遇到的，就是无效的空格了
                space = true;
            }
        }
        // 找出去除空格后的有效长度，
        //      如果最后面，还有很多空格，那么遇到第一个空格后， space 肯定为 true，
        //          说明字符串的有效长度就是 cur - 1 否则是 cur（代表末尾没有空格）
        int len = space ? cur - 1 : cur;
        if (len < 0) return ""; // 说明之前里面是空串，都没有有效字符

        // 反转有效字符
        // 1、先将整体逆序（调整单词的相对位置）
        reverseWords(chars, 0, len);

        // 2、再将每一个单词，逆回去（调整单词的顺序）
        // 需要准备一个哨兵，用于第一个字符前面
        int begin = -1;
        for (int i = 0; i < len; i++) {
            // 如果不是空格，直接下一个字符
            if (chars[i] != ' ') continue;
            // 来到这里，说明是空格了，要进行单词的逆序操作
            reverseWords(chars, begin + 1, i);

            // 下一次的开头，就在这个空格之后
            begin = i;
        }

        // 因为上面是原地的逆序操作，还是最开始的字符数组，所以只能遍历到有效长度
        // 那么这样就会有一个问题，最后一个单词，还没有逆序，
        // 所以我们这里，对最后一个单词，进行逆序
        reverseWords(chars, begin + 1, len);

        return new String(chars, 0, len);
    }

    /**
     * 将 [begin, end) 这个序列的字符串 逆序
     * 如：[a, b, 3] -> [3, b, a]
     */
    private static void reverseWords(char[] chars, int begin, int end) {
        // 因为不包含 end，
        end--;

        // begin 比 end 小，才交换
        while (begin < end) {
            char temp = chars[end];
            chars[end] = chars[begin];
            chars[begin] = temp;

            // 交换完后，
            end--;
            begin++;
        }
    }

    public static void main(String[] args) {
        System.out.println("666_" + reverseWords("  ") + "_666");
        System.out.println("666_" + reverseWords("  hello world!     ") + "_666");
        System.out.println("666_" + reverseWords("a good   example") + "_666");
        System.out.println("666_" + reverseWords("are you ok") + "_666");
    }

    /**
     * API调用大法1
     * @param s
     * @return
     */
    public static String reverseWords1(String s) {
        if (s == null) return "";
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(s, " ");
        while (st.hasMoreTokens()) {
            String word = st.nextToken();
            sb.insert(0, word).insert(0, " ");
        }
        return sb.toString().trim();
    }

    /**
     * API调用大法 2
     */
    public String reverseWords2(String s) {
        if (s == null) return "";
        StringBuilder sb = new StringBuilder();
        String[] words = s.trim().split("\\s+");
        for (int i = words.length - 1; i >= 0; i--) {
            sb.append(words[i]).append(" ");
        }
        return sb.toString().trim();
    }

}
