package run.ciusyan.栈和队列;

import run.ciusyan.common.TreeNode;

public class _654_最大二叉树 {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums == null || nums.length == 0) return null;

        return getRoot(nums, 0, nums.length);
    }

    /**
     * 将 nums 从 [begin, end) ，构建成一棵最大二叉树，返回根节点
     */
    private TreeNode getRoot(int[] nums, int begin, int end) {
        if (begin == end) return null;

        // 找出最大值所在索引，将其分隔成两部分
        int maxIndex = begin;
        for (int i = begin + 1; i < end; i++) {
            if (nums[i] > nums[maxIndex]) maxIndex = i;
        }

        // 利用最大值索引，构建一个二叉树节点
        TreeNode root = new TreeNode(nums[maxIndex]);

        // 递归构建左子树、右子树
        root.left = getRoot(nums, begin, maxIndex);
        root.right = getRoot(nums, maxIndex + 1, end);

        return root;
    }
}
