package run.ciusyan.二叉树;

import run.ciusyan.common.TreeNode;

public class _333_最大BST子树 {

    public int getMaxBSTSize(TreeNode root) {
        return (root == null) ? 0 : getInfo(root).size;
    }

    /**
     * 获取以 root 为根节点，这棵二叉搜索树的信息
     */
    private Info getInfo(TreeNode root) {
        if (root == null) return null;

        // 获取左右子树的 Info 信息
        Info leftSubInfo = getInfo(root.left);
        Info rightSubInfo = getInfo(root.right);

        // 定义左右子树的信息

        // 左子树的最大值、右子树的最小值，之后用于查看，是否是 BST
        //      因为：左子树最大值 < root < 右子树最小值
        //      为什么默认值为 root.val 呢？
        //          假设只有一个节点，那么最大值、最小值、都是根节点
        int max = root.val, min = root.val;
        // 左右子树的节点数量，
        //      如果根节点就是BST，那么有可能需要总的 size = leftSize + rightSize + 根节点(1)
        //      默认值为 -1，代表没有左子树或者没有右子树
        int leftSize = -1, rightSize = -1;

        if (leftSubInfo == null) {
            // 说明没有左子树
            leftSize = 0;
        } else if (root.left == leftSubInfo.root && root.val > leftSubInfo.max) {
            // 到这里面，左子树本身就是一颗 BST，那么之后构建的信息，最小值肯定在左子树
            min = leftSubInfo.min;
            leftSize = leftSubInfo.size;
        }

        if (rightSubInfo == null) {
            // 说明没有右子树
            rightSize = 0;
        } else if (root.right == rightSubInfo.root && root.val < rightSubInfo.min) {
            // 到这里面，右子树本身就是一颗 BST，那么之后构建的信息，最大值肯定在右子树
            max = rightSubInfo.max;
            rightSize = rightSubInfo.size;
        }

        if (leftSize >= 0 && rightSize >= 0) {
            // 来到这里说明：根节点本身就是最大的 BST
            return new Info(root, 1 + leftSize + rightSize, max, min);
        }

        // 来到这里，说明最大的BST，要么存在左子树、要么存在右子树

        if (leftSubInfo != null && rightSubInfo != null) {
            // 到这里面，说明左右子树都存在 BST，返回较大的那颗
            return (leftSubInfo.size > rightSubInfo.size) ? leftSubInfo : rightSubInfo;
        }

        // 到这里，左右子树只会有一个是null的，那么返回不null的那个即可
        //      因为其余的情况已经被前面拦截了
        return leftSubInfo != null ? leftSubInfo : rightSubInfo;
    }

    private static class Info {
        /** 根节点 */
        TreeNode root;
        /** 节点数量 */
        int size;
        /** 最大值 */
        int max;
        /** 最小值*/
        int min;

        public Info(TreeNode root, int size, int max, int min) {
            this.root = root;
            this.size = size;
            this.max = max;
            this.min = min;
        }
    }
}

// 测试代码
class Test {
    public static void main(String[] args) {
        _333_最大BST子树 solution = new _333_最大BST子树();

        // Test case 1: The given binary tree is null
        TreeNode root1 = null;
        int maxBSTSize1 = solution.getMaxBSTSize(root1);
        System.out.println("Max BST size: " + maxBSTSize1); // Expected output: 0

        // Test case 2: The given binary tree is a single node BST
        TreeNode root2 = new TreeNode(1);
        int maxBSTSize2 = solution.getMaxBSTSize(root2);
        System.out.println("Max BST size: " + maxBSTSize2); // Expected output: 1

        // Test case 3: The given binary tree is a BST with multiple nodes
        TreeNode root3 = new TreeNode(10);
        root3.left = new TreeNode(5);
        root3.right = new TreeNode(15);
        root3.left.left = new TreeNode(1);
        root3.left.right = new TreeNode(8);
        root3.right.left = new TreeNode(12);
        root3.right.right = new TreeNode(20);
        int maxBSTSize3 = solution.getMaxBSTSize(root3);
        System.out.println("Max BST size: " + maxBSTSize3); // Expected output: 7

        // Test case 4: The given binary tree is not a BST
        TreeNode root4 = new TreeNode(10);
        root4.left = new TreeNode(5);
        root4.right = new TreeNode(20);
        root4.left.left = new TreeNode(1);
        root4.left.right = new TreeNode(11);
        root4.right.left = new TreeNode(15);
        root4.right.right = new TreeNode(25);
        int maxBSTSize4 = solution.getMaxBSTSize(root4);
        System.out.println("Max BST size: " + maxBSTSize4); // Expected output: 3

        // Test case 5: The given binary tree is a BST with multiple nodes
        TreeNode root5 = new TreeNode(20);
        root5.left = new TreeNode(15);
        root5.right = new TreeNode(25);
        root5.left.left = new TreeNode(10);
        root5.left.right = new TreeNode(18);
        root5.right.left = new TreeNode(22);
        root5.right.right = new TreeNode(30);
        root5.left.left.left = new TreeNode(8);
        root5.left.left.right = new TreeNode(12);
        root5.left.right.left = new TreeNode(16);
        root5.left.right.right = new TreeNode(19);
        root5.right.left.left = new TreeNode(21);
        root5.right.left.right = new TreeNode(24);
        root5.right.right.left = new TreeNode(28);
        root5.right.right.right = new TreeNode(35);
        int maxBSTSize5 = solution.getMaxBSTSize(root5);
        System.out.println("Max BST size: " + maxBSTSize5); // Expected output: 15

        /*
        打印结果：
            Max BST size: 0
            Max BST size: 1
            Max BST size: 7
            Max BST size: 3
            Max BST size: 15
         */

    }
}
