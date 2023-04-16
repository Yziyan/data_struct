package run.ciusyan.栈和队列;

import java.util.Deque;
import java.util.LinkedList;

/**
 * https://leetcode.cn/problems/sliding-window-maximum/
 */
public class _239_滑动窗口最大值 {
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];
        if (k == 1) return nums;

        // nums.length - (k + 1) 是滑动窗口的个数
        int[] maxes = new int[nums.length - k + 1];

        // 准备一个双端队列，用于记录索引
        Deque<Integer> deque = new LinkedList<>();

        // 从头遍历到尾
        for (int i = 0; i < nums.length; i++) {

            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                // 只能是逐渐减小的序列
                deque.pollLast(); // 将队尾删除
            }

            // 来到这里，说明 nums[i] < nums[队尾]了，将其入队
            deque.offerLast(i); // 注意，这里存储的是索引


            // 存储完后，看看 w 是不是有效值
            int w = i - k + 1; // w 是每一个窗口的第一个元素位置
            if (w < 0) continue;

            // 来到这里，说明窗口是有效的
            //  继续查看，对头的是不是有效值索引
            if (deque.peekFirst() < w) {
                // 说明队头元素无效，（不在窗口的索引中）
                deque.pollFirst(); // 删除队头元素
            }

            // 来到这里，窗口值和队头值都有效了
            // 设置窗口最大值
            maxes[w] = nums[deque.peekFirst()];
        }

        return maxes;
    }
}
