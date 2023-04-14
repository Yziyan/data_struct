package run.ciusyan.数组;

/**
 * https://leetcode.cn/problems/sort-colors/
 */
public class _75_颜色分类 {

    public void sortColors(int[] nums) {
        if (nums == null) return;

        int left = 0;
        int right = nums.length - 1;
        int ai = 0; // 从头开始遍历

        while (right >= ai) {
            int ele = nums[ai];
            if (ele == 0) {
                swap(nums, ai++, left++);
            } else if (ele == 1) {
                ai++;
            } else {
                swap(nums, ai, right--);
            }
        }
    }

    // 交换两个索引位置的元素
    private void swap(int[] nums, int i1, int i2) {
        int temp = nums[i1];
        nums[i1] = nums[i2];
        nums[i2] = temp;
    }

}
