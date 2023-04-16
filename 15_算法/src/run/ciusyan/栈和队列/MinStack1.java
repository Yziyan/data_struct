package run.ciusyan.栈和队列;

import java.util.Stack;

/**
 * https://leetcode.cn/problems/min-stack/
 *
 * 155. 最小栈
 */
public class MinStack1 {

    private Stack<Integer> stack;
    private Stack<Integer> minStack;

    public MinStack1() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int val) {
        // 维护普通栈
        stack.push(val);
        // 额外维护一个最小栈
        if (minStack.isEmpty()) {
            // 那么直接将第一个元素入栈
            minStack.push(val);
        } else {
            // 这里需要比较，最小栈顶元素和当前元素的大小
            minStack.push(Math.min(val, minStack.peek()));
        }
    }

    public void pop() {
        stack.pop();
        minStack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
