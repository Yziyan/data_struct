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


    /**
     * Morris 中序遍历版本
     */
    public void recoverTree(TreeNode root) {
        if (root == null) return;

        // Morris 中序遍历
        TreeNode node = root;
        while (node != null) {

            if (node.left != null) {
                // 说明可以往左走，需要先连接好前驱和自己的线

                // 找到前驱节点
                TreeNode pred = node.left;
                // 一路往右找，但是前驱的右边不能是自己。
                while (pred.right != null && pred.right != node) {
                    pred = pred.right;
                }

                // 看看连接好前驱的线了没有，
                if (pred.right == null) {
                    // 说明没有连接过
                    pred.right = node;
                    // 往左走
                    node = node.left;
                } else { // prec.right == node
                    // 说明连接过了，并且已经访问过前驱了，该访问自己了
                    find(node);

                    // 然后往右走，但是需要清空原先的连线
                    pred.right = null;
                    node = node.right;
                }

            } else { // 说明左边走不了了
                // 访问节点
                find(node);

                // 尝试往右走
                node = node.right;
            }
        }

        // 修复节点的值
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }

    /**
     * 查找错误节点
     */
    private void find(TreeNode node) {
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

        prev = node;
    }

    /**
     * 普通中序遍历版本
     */
    public void recoverTree1(TreeNode root) {
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
        find(node);

        // 保留作为下一次的前一个节点
        prev = node;
        findWrongNode(node.right);
    }
}
