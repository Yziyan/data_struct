package run.ciusyan.字符串;

import run.ciusyan.common.TreeNode;

/**
 * https://leetcode.cn/problems/subtree-of-another-tree/
 */
public class _572_另一棵树的子树 {
    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null || subRoot == null) return false;

        // 序列化两棵树
        String rootStr = postSerialize(root);
        String subRootStr = postSerialize(subRoot);

        // 如果子树序列化后，是自己的字串，那么说明是自己的子树
        //      至于串匹配算法，还可以使用 KMP 这种高效一些的方式
        return rootStr.contains(subRootStr);
    }

    /**
     * 将 root 这棵二叉树序列化成字符串
     * @param root：树的根节点
     */
    private String postSerialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        postSerialize(root, sb);

        return sb.toString();
    }

    /**
     * 利用 sb 拼接序列化的结果
     */
    private void postSerialize(TreeNode root, StringBuilder sb) {
        // 处理左子树
        if (root.left != null) {
            postSerialize(root.left, sb);
        } else {
            sb.append("#").append("!");
        }

        // 处理右子树
        if (root.right != null) {
            postSerialize(root.right, sb);
        } else {
            sb.append("#").append("!");
        }

        // 后序遍历
        sb.append(root.val).append("!");
    }
}
