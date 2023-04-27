package run.ciusyan.Top;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.cn/problems/3sum/
 */
public class _15_三数之和 {
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums == null) return null;

        List<List<Integer>> list = new ArrayList<>();
        if (nums.length < 3) return list;

        // 先对序列排序，这里可以直接调用系统库
        Arrays.sort(nums);

        // 从头开始遍历，且至少需要有一个三元组
        int lastIndex = nums.length - 3;

        // 每一次遍历，尾部都是从最后开始的，就没必要每次都算了
        int lastR = nums.length - 1;
        for (int i = 0; i <= lastIndex; i++) {
            // 因为 i 和 i - 1 位置的元素相等了，刚刚在 i 位置，已经查看过更大范围的了
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            // 每次遍历，都需要准备一个头、尾指针
            int left = i + 1, right = lastR;

            // nums[i] + nums[left] + nums[right] = 0
            int remain = -nums[i]; // 0 - nums[i] ?  nums[left] + nums[right]

            while (left < right) {
                int addLR = nums[left] + nums[right];

                if (remain == addLR) {
                    // 说明这里是一个答案
                    list.add(Arrays.asList(nums[i], nums[left], nums[right]));

                    // 但是我们需要去掉重复的，如果发现相邻的元素，
                    //  有和 nums[left] or nums[right]相等的，
                    //      那么直接跳过它们即可，因为已经判断过更大范围的了
                    while (left < right && nums[left] == nums[left + 1]) left++;
                    while (right > left && nums[right] == nums[right - 1]) right--;

                    // 但是在这里，还需要整体向中间逼近，因为此次结果已经记录了
                    //      相同的也跳过了，之后肯定会不满足，所以直接到下一轮相加
                    left++;
                    right--;
                } else if (addLR > remain) {
                    // 说明相加后大了，后面往前走一点，（后面的数太大了）
                    right--;
                } else { // addLR < remain
                    // 说明相加后小了，前面往后走一点，（前面的数太小了）
                    left++;
                }
            }
        }

        return list;
    }
}
