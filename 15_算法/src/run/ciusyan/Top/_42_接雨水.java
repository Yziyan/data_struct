package run.ciusyan.Top;

/**
 * https://leetcode.cn/problems/trapping-rain-water/
 */
public class _42_接雨水 {


    public int trap(int[] height) {
        if (height == null || height.length < 2) return 0;

        int water = 0;
        int l = 0, r = height.length - 1;
        int lowerMax = 0;

        while (l < r) {
            // 找到更低的那一边，之后用它做计算
            //      其实就是确定另一边，一定不会比自己矮，可以放心看自己这一边
            //      因为矮的那边计算过能否接水了，将它往中间靠（每根柱子只用计算一次）
            int lower = height[l] > height[r] ? height[r--] : height[l++];
            // 更新较低的最大值，谁往中间移动了，这个就是哪边的最大值
            lowerMax = Math.max(lowerMax, lower);

            water += lowerMax - lower;
        }

        return water;
    }


    public int trap3(int[] height) {
        if (height == null || height.length < 2) return 0;

        // 能接的雨水量
        int water = 0;
        // 指向第二根柱子和倒数第二根柱子
        int left = 1, right = height.length - 2;
        // 某柱子左右两边的最大值
        int leftMax = 0, rightMax = 0;

        // 只需要算一遍
        while (left <= right) {
            // 每次都用前一根柱子，或者后一根柱子，推算出当前柱子的高度
            if (height[left - 1] > height[right + 1]) {
                // 说明右边柱子矮，用右边的柱子作为参照
                // 先更新右边柱子的最大值
                rightMax = Math.max(rightMax, height[right + 1]);
                if (rightMax > height[right]) {
                    // 说明该柱子能够接水
                    water += rightMax - height[right];
                }

                // 操作完成后，将矮的柱子往中间移动
                right--;
            } else {
                // 说明左边柱子矮，相等放哪都行
                // 先更新左边柱子的最大值
                leftMax = Math.max(leftMax, height[left - 1]);
                if (leftMax > height[left]) {
                    // 说明可以接水
                    water += leftMax - height[left];
                }

                // 操作完成后，将矮的柱子往中间移动
                left++;
            }
        }

        return water;
    }

    /**
     * 合并循环
     */
    public int trap2(int[] height) {
        if (height == null || height.length < 2) return 0;

        // 能接的雨水量
        int water = 0;
        int lastH = height.length - 1;

        // 右边的最大高度
        int[] rightDp = new int[height.length];
        for (int i = lastH - 1; i >= 1; i--) {
            rightDp[i] = Math.max(rightDp[i + 1], height[i + 1]);
        }

        // 左边的最大高度
        int leftDp = 0;
        // 计算出每根柱子能接的雨水
        for (int i = 1; i < lastH; i++) {
            // 先更新左边的最大值
            leftDp = Math.max(leftDp, height[i - 1]);

            // 相对的高：左右两边较矮的那个
            int relH = Math.min(leftDp, rightDp[i]);
            if (relH <= height[i]) continue;

            // 来到这里，说明能接住雨水
            water = water + relH - height[i];
        }

        return water;
    }

    /**
     * 最直接，最清晰的写法
     */
    public int trap1(int[] height) {
        if (height == null || height.length < 2) return 0;

        // 能接的雨水量
        int water = 0;
        int lastH = height.length - 1;

        // 每一根柱子，左边柱高的最大值，
        int[] leftDp = new int[height.length];
        for (int i = 1; i < lastH; i++) {
            leftDp[i] = Math.max(leftDp[i - 1], height[i - 1]);
        }

        // 每一根柱子，右边柱高的最大值，
        int[] rightDp = new int[height.length];
        for (int i = lastH - 1; i >= 1; i--) {
            rightDp[i] = Math.max(rightDp[i + 1], height[i + 1]);
        }

        // 求出每一根柱子，能接多少水
        for (int i = 1; i < lastH; i++) {
            // 左右两边较小的作为相对高
            int relH = Math.min(leftDp[i], rightDp[i]);
            if (relH <= height[i]) continue; // 还没自己高，肯定接不了水

            // 来到这里说明可以接水
            //  真正的高 = relH - height[i]
            water = water + relH - height[i];
        }

        return water;
    }
}
