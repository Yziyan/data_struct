package run.ciusyan.链表;

/**
 * https://leetcode.cn/problems/partition-list/
 */
public class _86_分隔链表 {
    public ListNode partition(ListNode head, int x) {
        if (head == null) return null;

        // 准备两条链表，一条接前面
        ListNode dummyLeft = new ListNode(0);
        ListNode tailLeft = dummyLeft;
        // 另一条接后面
        ListNode dummyRight = new ListNode(0);
        ListNode tailRight = dummyRight;

        while (head != null) {
            if (head.val < x) {
                // 接第一条链表
                tailLeft = tailLeft.next = head;
            } else {
                // 接第二条链表
                tailRight = tailRight.next = head;
            }

            head = head.next;
        }

        // 将第二条链表的尾部手动置为 null
        tailRight.next = null;

        // 将第一条链表的最后，与第二条链表的前面接起来
        tailLeft.next = dummyRight.next;

        return dummyLeft.next;
    }
}
