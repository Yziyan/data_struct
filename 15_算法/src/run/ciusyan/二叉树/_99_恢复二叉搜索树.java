package run.ciusyan.二叉树;

import run.ciusyan.common.TreeNode;

/**
 * https://leetcode.cn/problems/recover-binary-search-tree/
 */
public class _99_恢复二叉搜索树 {

    /**
     * 中序遍历的前一个节点
     */
    private TreeNode prev;

    /**
     * 第一个错误的节点：第一个逆序对中，较大的节点
     */
    private TreeNode first;

    /**
     * 第二个错误的节点：第二个逆序对中，较小的节点
     */
    private TreeNode second;

    public void recoverTree(TreeNode root) {
        if (root == null) return;

        // 找到两个错误的节点
        findWrongNode(root);

        // 修复节点的值
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }

    /**
     * 利用中序遍历，找出两个错误的节点
     */
    private void findWrongNode(TreeNode node) {
        if (node == null) return;

        findWrongNode(node.left);
        // 中序遍历
        if (prev != null && prev.val > node.val) {
            // 说明找到逆序对了，但是不知道是第几个
            //  但是遇到一个逆序对，我们就可以当做他是最后一个逆序对，
            //  然后将小的节点，赋值给 second
            second = node;

            // 如果 first 节点已经找到了，说明这是遇到了第二个逆序对，
            //      直接退出递归即可，因为已经找到了两个错误节点
            if (first != null) return;
            // 来到这里，说明是遇到了第一个逆序对，将较大的节点赋值给 first
            first = prev;
        }

        // 保留作为下一次的前一个节点
        prev = node;
        findWrongNode(node.right);
    }
}
