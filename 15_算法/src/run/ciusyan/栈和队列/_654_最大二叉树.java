package run.ciusyan.栈和队列;

import run.ciusyan.common.TreeNode;

import java.util.Arrays;
import java.util.Map;
import java.util.Stack;

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

    public int[] parentIndexes(int[] nums) {
        if (nums == null || nums.length == 0) return null;

        // 用于存放左右两边的结果
        int[] lefts = new int[nums.length];
        int[] rights = new int[nums.length];

        // 初始化一下
        for (int i = 0; i < lefts.length; i++) {
            lefts[i] = -1;
            rights[i] = -1;
        }

        // 遍历，利用栈，求出结果值
        Stack<Integer> stack = new Stack<>(); // 栈中用于存放索引

        for (int i = 0; i < nums.length; i++) {
            // 尝试将其放入栈，但是需要保证单调性
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                // 这里只能是 > 才弹出
                //      但是弹出后，说明当前元素，是栈顶元素右边第一个比自己大的数
                rights[stack.pop()] = i;
            }

            // 来到这里，尝试将其放入栈中
            //      能放入栈中，说明当前栈顶元素，是当前元素左边第一个比它大的元素
            if (!stack.isEmpty()) {
                // 栈为空的场景，已经初始化过了
                lefts[i] = stack.peek();
            }

            // 尝试放入栈
            stack.push(i);
        }

        System.out.println("left: " + Arrays.toString(lefts));
        System.out.println("right: " + Arrays.toString(rights));

        // 最终结果（每个节点的父节点索引）
        int[] parents = new int[nums.length];

        // 求出 lefts[i] 和 rights[i] 的最小值
        for (int i = 0; i < parents.length; i++) {
            if (lefts[i] == -1 && rights[i] == -1) {
                // 说明没有根节点
                parents[i] = -1;
                continue;
            }

            if (lefts[i] == -1) {
                // 说明右边是索引
                parents[i] = rights[i];
            } else if (rights[i] == -1) {
                // 说明左边是索引
                parents[i] = lefts[i];
            } else {
                // 说明左右都有，小的是索引
                parents[i] = (nums[lefts[i]] > nums[rights[i]]) ? rights[i] : lefts[i];
            }
        }

        System.out.println("parent: " + Arrays.toString(parents));

        return parents;
    }

    public static void main(String[] args) {
        _654_最大二叉树 o = new _654_最大二叉树();
        o.parentIndexes(new int[]{1, 7, 5, 3, -3, -1, 8, 2});
    }
}
