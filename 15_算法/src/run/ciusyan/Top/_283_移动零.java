package run.ciusyan.Top;

/**
 * https://leetcode.cn/problems/move-zeroes/
 */
public class _283_移动零 {
    public void moveZeroes(int[] nums) {
        if (nums == null) return;
        if (nums.length <= 1) return;

        // 扫描一遍，准备两个指针，
        //      如果 nums[i] == 0，那么 i++，即可
        //      如果 nums[i] != 0，那么将 i位置的元素放到 cur 位置
        //      但是如果 nums[i] != 0 但是， i = cur，说明直接跳过即可
        for (int i = 0, cur = 0; i < nums.length; i++) {
            if (nums[i] == 0) continue;

            if (i != cur) {
                // 如果 i = cur 时，不能去覆盖，让他放置在原位置即可
                nums[cur] = nums[i];
                nums[i] = 0;
            }
            cur++;
        }
    }
}
