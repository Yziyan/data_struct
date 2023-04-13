package run.ciusyan.array;

/**
 * https://leetcode.cn/problems/merge-sorted-array/
 */
public class _88_合并两个有序数组 {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (nums1 == null || nums2 == null) return;

        int i1 = m - 1; // 指向前一个有序序列
        int i2 = n - 1; // 指向后一个有序序列
        int ai = i1 + i2 + 1; // 指向现在应该放在哪里

        // 如果 nums2 放置完了，说明nums1的就不用放了，默认就是有序的
        while (i2 >= 0) {
            // 如果上面的全部放到后面了，直接将下面的放到前面即可
            if (i1 >= 0 && nums1[i1] > nums2[i2]) {
                // 说明要将上面的放到后面
                nums1[ai--] = nums1[i1--];
            } else {
                // 说明要将下面的放到后面
                nums1[ai--] = nums2[i2--];
            }
        }
    }
}
