package run.ciusyan.DFS;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/generate-parentheses/
 */
public class _22_括号生成 {
    public List<String> generateParenthesis(int n) {
        if (n < 0) return null;

        // 记录轨迹，轨迹的长度，是其中一个有效括号的长度，2n
        char[] track = new char[n << 1];
        List<String> list = new ArrayList<>();
        // 从第 0 层开始搜索，左右括号剩余的数量初始值都是 n
        dfs(0, track, n, n, list);

        return list;
    }

    /**
     * 深度优先搜索
     * @param level：所在层
     * @param track：用于记录轨迹
     * @param leftRemain：左括号剩余的数量
     * @param rightRemain：右扩好剩余的数量
     * @param list：结果列表
     */
    private void dfs(int level, char[] track, int leftRemain, int rightRemain, List<String> list) {
        // 不能往下搜索了 track.length = 2n
        if (level == track.length) {
            // 记录轨迹
            list.add(new String(track));
            // 直接回溯
            return;
        }

        // 列出可选的所有可能，这里最多有两种可能，
        //      一种是 '('
        //      另一种是 ')'

        // 左括号的情况，
        //  什么时候才能选择左括号？
        //      有左括号就能选
        if (leftRemain > 0) {
            track[level] = '(';
            // 上面记录轨迹后，往下钻
            dfs(level + 1, track, leftRemain - 1, rightRemain, list);
        }

        // 右括号的情况
        //  什么时候才能选择右括号？
        //      有右括号，并且右括号的数量不能与左括号数量相等
        if (rightRemain > 0 && rightRemain != leftRemain) {
            track[level] = ')';
            // 上面记录轨迹后，往下钻
            dfs(level + 1, track, leftRemain, rightRemain - 1, list);
        }
    }

    public static void main(String[] args) {
        _22_括号生成 o = new _22_括号生成();
        System.out.println(o.generateParenthesis(3));
    }

}
