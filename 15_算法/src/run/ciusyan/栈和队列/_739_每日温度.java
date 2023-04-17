package run.ciusyan.栈和队列;

import java.util.Stack;

/**
 * https://leetcode.cn/problems/daily-temperatures/
 */
public class _739_每日温度 {

    /**
     * 倒推法
     */
    public int[] dailyTemperatures(int[] temperatures) {
        if (temperatures == null || temperatures.length == 0) return null;
        int[] res = new int[temperatures.length];

        // 从倒数第二个元素开始求就可以了，因为最后一个肯定是 0 天升温
        for (int today = res.length - 2; today >= 0; today--) {
            // 往后的天，默认是往后一天
            int after = today + 1;

            // 需要倒推出每一天，所以需要循环执行
            //      这里可以不写条件，等待里面的 break 退出循环即可
            while (true) {

                if (temperatures[today] < temperatures[after]) {
                    // 代表到after天时，就会升温
                    res[today] = after - today; // 求出相对距离
                    break;
                }

                if (res[after] == 0) {
                    // 说明之后，不会有升温了，其实可以直接 break，（使用默认值）
                    //      但是为了之后方便查看，可以详细写出来
                    res[today] = 0;
                    break;
                }

                // 来到这里，说明，之后可能升温，至少可以往后查找，
                //      今天的温度，和后面天的温度相等的情况，也在这里考虑了
                after = after + res[after];

                // 赋值过后，又进入下一轮循环，也就是用后面天的温度，与今天的温度比较
            }
        }

        return res;
    }

    /**
     * 单调栈实现
     */
    public int[] dailyTemperatures1(int[] temperatures) {
        if (temperatures == null || temperatures.length == 0) return null;

        // 结果数组
        int[] res = new int[temperatures.length];
        // 准备一个栈，用于求解出，右边第一个比自己大的元素的索引
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                // 说明这里，弹出来的每一个元素，的右边第一个比它大的元素，是当前元素
                Integer top = stack.pop();
                res[top] = i - top; // 相对距离 = 当前位置 - 之前的位置
            }

            // 将其元素入栈
            stack.push(i);
        }

        return res;
    }
}
