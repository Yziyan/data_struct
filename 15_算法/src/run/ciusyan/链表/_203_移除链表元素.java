package run.ciusyan.链表;

/**
 * https://leetcode.cn/problems/remove-linked-list-elements/
 */
public class _203_移除链表元素 {
    public ListNode removeElements(ListNode head, int val) {
        if (head == null) return null;

        // 准备一个虚拟头节点，将其串起来
        ListNode dummyHead = new ListNode(0);
        // 准备一个尾结点，用于之后串起恰当的值
        ListNode tail = dummyHead;

        while (head != null) {
            if (head.val != val) {
                // 如果不是删除的值，才将其串起来
                tail.next = head;
                tail = head;
            }

            head = head.next;
        }
        // 但是防止删除的都是对应元素，那么最后一个元素应该指向null
        //      比如：3>3>3>3>null val = 3
        //      那么应该将 dummyHead -> 0>null
        tail.next = null;

        return dummyHead.next;
    }
}
