package run.ciusyan.二叉树;

import run.ciusyan.common.TreeNode;

/**
 * https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/
 */
public class _236_二叉树的最近公共祖先 {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || p == root || q == root) return root;

        // 分别在左右子树查找公共祖先
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // 如果两边都能找到，说明p、q遍布再左右子树
        if (left != null && right != null) return root;

        // 来到这里，起码说明不再左右子树
        // 说明要么都在右子树、要么都在左子树、要么 root == null了

        return (left == null) ? right : left;
    }
}
