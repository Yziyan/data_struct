package run.ciusyan.Top;

/**
 * https://leetcode.cn/problems/container-with-most-water/
 */
public class _11_盛最多水的容器 {

    public int maxArea(int[] height) {
        if (height == null || height.length < 2) return 0;

        // 准备两个指针，分别指向左和右
        int left = 0, right = height.length - 1;

        // 最终的面积
        int maxArea = 0;

        while (left < right) {
            // 用矮的柱子作为高，并计算出可能的最大面积
            int h = Math.min(height[left], height[right]);
            maxArea = Math.max(maxArea, h * (right - left));

            // 跳过不必要的比较，说明左边比当前的高还低或者相等，跳过它
            while (left < right && height[left] <= h) left++;

            // 跳过不必要的比较，说明右边边比当前的高还低或者相等，跳过它
            while (left < right && height[right] <= h) right--;

            // 而且上面两个条件，仅仅有一个会被执行，
            //  h 就是用当前左边和右边柱子的最小值
        }

        return maxArea;
    }

    public int maxArea3(int[] height) {
        if (height == null || height.length < 2) return 0;

        // 准备两个指针，分别指向左和右
        int left = 0, right = height.length - 1;

        // 最终的面积
        int maxArea = 0;

        while (left < right) {
            if (height[left] > height[right]) {
                // 说明右边矮
                int h = height[right];
                maxArea = Math.max(maxArea, h * (right - left));

                // 跳过右边，和当前相等或者比当前还矮的柱子
                while (left < right && height[right] <= h) right--;

            } else {
                // 说明左边矮
                int h = height[left];
                maxArea = Math.max(maxArea, h * (right - left));

                // 跳过左边，和当前相等或者比当前还矮的柱子
                while (left < right && height[left] <= h) left++;
            }
        }

        return maxArea;
    }

    public int maxArea2(int[] height) {
        if (height == null || height.length < 2) return 0;

        // 准备两个指针，分别指向左和右
        int left = 0, right = height.length - 1;

        // 最终的面积
        int maxArea = 0;

        while (left < right) {
            // 用矮的那边作为高，然后将矮的指针往中间移动
            int h = height[left] > height[right] ? height[right--] : height[left++];
            // 此时的底已经往中间挪动 1了，所以需要 + 1 后计算
            maxArea = Math.max(maxArea, h * (right - left + 1));
        }

        return maxArea;
    }


        public int maxArea1(int[] height) {
        if (height == null || height.length < 2) return 0;

        // 准备两个指针，分别指向左和右
        int left = 0, right = height.length - 1;

        // 最终的面积
        int maxArea = 0;

        // 遍历，直至重叠
        while (left < right) {
            // 取较小的柱子作为高，right到left之间的距离作为底
            maxArea = Math.max(maxArea, Math.min(height[left], height[right]) * (right - left));

            // 操作完，将较矮的柱子，向中心移动一点
            if (height[left] < height[right]) {
                // 说明左边的柱子矮
                left++;
            } else {
                // 说明右边的柱子矮，
                // 高度相等在哪都一样
                right--;
            }
        }

        return maxArea;
    }

    public static void main(String[] args) {
        _11_盛最多水的容器 o = new _11_盛最多水的容器();
        System.out.println(o.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
    }

}
