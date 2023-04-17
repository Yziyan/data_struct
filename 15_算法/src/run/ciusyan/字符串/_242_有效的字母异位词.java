package run.ciusyan.字符串;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.cn/problems/valid-anagram/
 */
public class _242_有效的字母异位词 {

    /**
     * 应对 26 个小写字符
     */
    public boolean isAnagram(String s, String t) {
        if (s == null || t == null) return false;
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        if (sChars.length != tChars.length) return false;

        // 准备一个数组，来存放次数
        int[] counts = new int[26];

        // 遍历第一个字符串数组
        for (int i = 0; i < sChars.length; i++) {
            char c = sChars[i];
            // 直接将字符放到对应的索引位置
            counts[c - 'a']++;
        }

        // 遍历第二个字符数组，将次数减回去
        for (int i = 0; i < tChars.length; i++) {
            char c = tChars[i];
            // 如果某个位置 减完之后 小于 0 了，说明次数不相等
            if (--counts[c - 'a'] < 0) return false;
        }

        return true;
    }

    /**
     * 通用解法
     */
    public boolean isAnagram1(String s, String t) {
        if (s == null || t == null) return false;
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        if (sChars.length != tChars.length) return false;

        // 准备一个哈希Map，用于存放次数
        Map<Character, Integer> counts = new HashMap<>();

        // 遍历第一个字符串数组
        for (int i = 0; i < sChars.length; i++) {
            char c = sChars[i];
            if (counts.containsKey(c)) {
                // 说明存在，次数 + 1即可
                counts.put(c, counts.get(c) + 1);
            } else {
                counts.put(c, 1);
            }
        }

        // 遍历第二个字符串数组
        for (int i = 0; i < tChars.length; i++) {
            char c = tChars[i];

            // 都不存在这个 key ，说明肯定不是异构的
            if (!counts.containsKey(c)) return false;

            // 说明存在，次数 - 1即可
            int newCounts = counts.get(c) - 1;
            if (newCounts < 0) return false;
            counts.put(c,  newCounts);
        }

        return true;
    }
}
