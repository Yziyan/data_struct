package run.ciusyan.链表;

/**
 * https://leetcode.cn/problems/intersection-of-two-linked-lists/
 */
public class _160_相交链表 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        ListNode curA = headA;
        ListNode curB = headB;

        while (curA != curB) {
            curA = (curA == null) ? headB : curA.next;
            curB = (curB == null) ? headA : curB.next;
        }

        return curA;
    }
}
