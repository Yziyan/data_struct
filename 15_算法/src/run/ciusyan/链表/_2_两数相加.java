package run.ciusyan.链表;

/**
 * https://leetcode.cn/problems/add-two-numbers/
 */
public class _2_两数相加 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode dummyHead = new ListNode(0);
        ListNode tail = dummyHead;
        int carry = 0; // 进位值

        while (l1 != null || l2 != null) {
            // 取出 两个节点的 val，如果是 null 节点，当做 0
            int v1 = 0;
            if (l1 != null) {
                v1 = l1.val;
                l1 = l1.next;
            }

            int v2 = 0;
            if (l2 != null) {
                v2 = l2.val;
                l2 = l2.next;
            }

            // 相加，用于构建新节点
            int val = v1 + v2 + carry;
            if (val >= 10) {
                // 进位
                carry = val / 10;
                val = val % 10;
            } else {
                carry = 0;
            }
            // 链接新节点
            tail = tail.next = new ListNode(val);
        }

        // 还需要查看进位，是否为 1，如果是，最后还需要串上进位的值
        if (carry == 1) {
            tail.next = new ListNode(carry);
        }

        return dummyHead.next;
    }
}
