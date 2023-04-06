package run.ciusyan.divideconquer;

/**
 * 最大子数组
 */
public class MaxSubarray {

    /**
     * 方法三：分治
     */
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        return maxSubArray(nums, 0, nums.length);
    }

    /**
     * nums 中从 [begin, end)的最大子数组之和
     */
    private int maxSubArray(int[] nums, int begin, int end) {
        // 说明数组中只有一个元素，
        if (end - begin < 2) return nums[begin];

        int mid = (begin + end) >> 1;

        // 左子数组之和 [begin, mid)
        int leftSubArrayMax = maxSubArray(nums, begin, mid);
        // 右子数组之和 [mid, end)
        int rightSubArrayMax = maxSubArray(nums, mid, end);

        // 可能的最大值之和
        // 因为最大的子数组和，可能即在左子数组、也在右子数组
        int mayMax = Math.max(leftSubArrayMax, rightSubArrayMax);

        // 从 mid -> 此次最左边 begin
        int leftMax = nums[mid - 1];
        int leftSum = nums[mid - 1];
        for (int i = mid - 2; i >= begin; i--) {
            // mid往左边 加到最左边
            leftSum += nums[i];

            // 找出较大值
            leftMax = Math.max(leftMax, leftSum);
        }

        // 从 mid -> 此次最右边 end
        int rightMax = nums[mid];
        int rightSum = nums[mid];
        for (int i = mid + 1; i < end; i++) {
            // mid往右边  加到最右边
            rightSum += nums[i];

            // 找出较大值
            rightMax = Math.max(rightMax, rightSum);
        }

        // 将上面可能的最大值，中间的最大值比较，谁大谁就是结果
        // mayMax：子数组仅存在左边 或者 右边
        // leftMax + rightMax：子数组存在与 左右两边
        return Math.max(mayMax, leftMax + rightMax);
    }

    /**
     * 方法二：暴力求解2
     */
    public int maxSubArray2(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int max = Integer.MIN_VALUE;
        for (int begin = 0; begin < nums.length; begin++) {
            int sum = 0;
            for (int end = begin; end < nums.length; end++) {
                // 用前面求的一个和，加上后一个即可
                sum += nums[end];
                max = Math.max(max, sum);
            }
        }

        return max;
    }

    /**
     * 方法一：暴力求解
     */
    public int maxSubArray1(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        int max = Integer.MIN_VALUE;

        // begin 从 0 开始 -> 最后一个元素
        for (int begin = 0; begin < nums.length; begin++) {
            // end 从begin 开始 -> 最后一个元素
            for (int end = begin; end < nums.length; end++) {
                int sum = 0;
                // 求出 [begin, end] 之间的和
                for (int i = begin; i <= end; i++) {
                    sum += nums[i];
                }
                max = Math.max(max, sum);
            }
        }

        return max;
    }

}
