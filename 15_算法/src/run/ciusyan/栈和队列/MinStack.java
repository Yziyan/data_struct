package run.ciusyan.栈和队列;

/**
 * https://leetcode.cn/problems/min-stack/
 *
 * 155. 最小栈
 */
public class MinStack {

    // 用一个虚拟头结点
    private Node head;

    public MinStack() {
        // minVal 一开始要是有无穷大，要不然比较可能会出错
        head = new Node(0, Integer.MAX_VALUE, null);
    }

    public void push(int val) {
        head = new Node(val, Math.min(val, head.minVal), head);
    }

    public void pop() {
        // 将最前面的节点给删除掉（模拟栈弹出）
        head = head.next;
    }

    public int top() {
        return head.val;
    }

    public int getMin() {
        return head.minVal;
    }

    private static class Node {
        int val;
        int minVal;
        Node next;

        Node(int val, int minVal, Node next) {
            this.val = val;
            this.minVal = minVal;
            this.next = next;
        }
    }
}
