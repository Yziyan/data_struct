package run.ciusyan.字符串;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.cn/problems/longest-substring-without-repeating-characters/
 */
public class _3_无重复字符的最长子串 {

    /**
     * 只适用于单字节编码字符：ASCLL 码有对应的值
     *
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null) return 0;
        char[] chars = s.toCharArray();
        if (chars.length == 0) return 0;

        // ASCLL码值
        int[] preIndices = new int[128];
        for (int i = 1; i < preIndices.length; i++) {
            preIndices[i] = -1;
        }

        // 结果
        int max = 1;
        // 记录 i - 1位置的无重复最长子串的起始位置
        int frontStart = 0;
        preIndices[chars[0]] = 0; // 初始化第一个字符的无重复最长子串的起始位置

        for (int i = 1; i < chars.length; i++) {
            // 此字符在前面可能出现的位置
            int preIndex = preIndices[chars[i]];

            if (preIndex >= frontStart) {
                // 说明 preIndex 离当前字符要近一些，属于后两种情况
                frontStart = preIndex + 1;
            }

            // 来到这里，此次比较的结果，就可以统一了：i - frontStart + 1
            // 第一种情况：i - frontStart + 1
            // 后两种情况：i - preIndex -> i - (frontStart - 1) -> i - frontStart + 1
            max = Math.max(max, i - frontStart + 1);

            // 别忘了更新字符出现的位置
            preIndices[chars[i]] = i;
        }

        return max;
    }

    /**
     * 通用解法，可以解决所有字符
     */
    public int lengthOfLongestSubstring1(String s) {
        if (s == null) return 0;
        char[] chars = s.toCharArray();
        if (chars.length == 0) return 0;

        // 用于记录某一字符，在前面可能出现的位置
        Map<Character, Integer> preIndices = new HashMap<>();
        // 初始化为 -1，就是一个哨兵值，默认代表x字符，没有在前面出现过
        for (int i = 1; i < chars.length; i++) {
            // 但是这里其实可以在之后获取的时候，调用getOrDefault()，就不用先给初始值了
            preIndices.put(chars[i], -1);
        }

        // 准备两个指针：
        // 这个是记录，x字符，前面出现过的位置。这个之后再循环内部赋值（因为要往下遍历，会改变）
        int preIndex;
        // 第 i - 1 个字符，的无重复最长子串的开始位置（第一个字符就是它自己）
        int frontStart = 0;
        // 所以需要将第一个字符也放进去
        preIndices.put(chars[0], 0);

        // 准备一个结果，最大值，默认为第一个字符的结果
        int max = 1;

        // 遍历字符，其实从第二个字符开始就可以，因为第一个字符的值已经确定了
        for (int i = 1; i < chars.length; i++) {
            preIndex = preIndices.get(chars[i]); // 有了默认值，肯定能获取到值
            if (preIndex >= frontStart) {
                // 说明是后两种情况：将 frontStart 赋值
                frontStart = preIndex + 1;
            }

            // 出现过了，后面可能会使用到
            preIndices.put(chars[i], i);

            // 因为把另外两种情况处理过了，所以这里可以通用的使用：i - frontStart + 1
            max = Math.max(max, i - frontStart + 1);
        }

        return max;
    }
}
