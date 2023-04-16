package run.ciusyan.链表;

/**
 * https://leetcode.cn/problems/palindrome-linked-list/
 */
public class _234_回文链表 {
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;
        if (head.next.next == null) return head.val == head.next.val; // 只有两个节点

        // 查找中心节点：
        ListNode middle = getMiddle(head);

        // 将中心节点后面局部翻转
        ListNode newNode = reverseList(middle.next);
        while (newNode != null) {
            if (head.val != newNode.val) return false;

            head = head.next;
            newNode = newNode.next;
        }

        return true;
    }

    /**
     * 寻找链表的中心节点 —— 快慢指针
     */
    private ListNode getMiddle(ListNode head) {
        if (head == null) return null;

        ListNode fast = head;
        ListNode slow = head;

        // fast.next != null 这是判断奇数个节点
        // fast.next.next != null 这是判断偶数个节点
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    /**
     * 翻转链表 —— 头插法
     */
    private ListNode reverseList(ListNode head) {
        if (head == null) return null;

        ListNode newNode = null;

        while (head != null) {
            ListNode temp = head.next;
            head.next = newNode;
            newNode = head;
            head = temp;
        }

        return newNode;
    }
}
