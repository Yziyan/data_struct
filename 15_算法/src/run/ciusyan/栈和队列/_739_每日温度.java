package run.ciusyan.栈和队列;

import java.util.Stack;

/**
 * https://leetcode.cn/problems/daily-temperatures/
 */
public class _739_每日温度 {
    public int[] dailyTemperatures(int[] temperatures) {
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
