package run.ciusyan.DFS;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode.cn/problems/letter-combinations-of-a-phone-number/
 */
public class _17_电话号码的字母组合 {
    /**
     * 手机数字 -> 字符
     */
    private char[][] lettersArray = {
        {'a', 'b', 'c'}, {'d', 'e', 'f'}, {'g', 'h', 'i'},
        {'j', 'k', 'l'}, {'m', 'n', 'o'}, {'p', 'q', 'r', 's'},
        {'t', 'u', 'v'}, {'w', 'x', 'y', 'z'}
    };

    public List<String> letterCombinations(String digits) {
        if (digits == null) return null;
        List<String> list = new ArrayList<>();
        char[] chars = digits.toCharArray();
        if (chars.length == 0) return list;

        // 用于记录每一层已经访问过的字符
        char[] visited = new char[chars.length];

        // 从第一层开始遍历
        dfs(0, chars, visited, list);

        return list;
    }

    private void dfs(int index, char[] chars, char[] visited, List<String> list) {
        // 不能继续往下一层搜索时，保存结果
        if (index == chars.length) {
            // 因为索引是从 0 开始的，所以 index = chars.len 的时候，就不能往下走了
            // 记录已经访问过的字符串，保存结果（得到了一个正确的解）
            list.add(new String(visited));

            // 然后直接回溯到上一层
            return;
        }

        // 每一层有几个字母，就遍历几次，所以先枚举这一层的所有可能
        //      chars[index] - '2' -> 第几层的数字 - '索引从0开始 & 按键1没有字母'
        char[] letters = lettersArray[chars[index] - '2'];
        for (char letter : letters) {
            // 访问这个字符
            visited[index] = letter;

            // 往下一层钻
            dfs(index+1, chars, visited, list);
        }
    }
}
