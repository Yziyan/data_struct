package run.ciusyan.数组;

/**
 * https://leetcode.cn/problems/squares-of-a-sorted-array/
 */
public class _977_有序数组的平方 {
    public int[] sortedSquares(int[] nums) {
        if (nums == null || nums.length == 0) return null;

        int l = 0;
        int r = nums.length - 1;

        int[] res = new int[nums.length];
        int i = r;

        while (r >= l) {
            int left = nums[l] < 0 ? -nums[l] : nums[l];
            int right = nums[r] < 0 ? -nums[r] : nums[r];

            if (left > right) {
                // 说明左边的大，交换值
                res[i--] = left * left;
                l++;
            } else {
                res[i--] = right * right;
                r--;
            }
        }

        return res;
    }
}
