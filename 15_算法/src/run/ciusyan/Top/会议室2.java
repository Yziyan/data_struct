package run.ciusyan.Top;

import java.util.Arrays;
import java.util.PriorityQueue;

public class 会议室2 {

    public static int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return 0;

        // 按照会议的开始时间排序
        Arrays.sort(intervals, ((o1, o2) -> o1[0] - o2[0]));

        // 准备一个最小堆，用于记录前面会议的最早结束时间
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        // 将第一场会议的结束时间加入到堆中，默认它最早结束
        queue.offer(intervals[0][1]);

        for (int i = 1; i < intervals.length; i++) {
            // 没开一场会议，用这场会议的开始时间，和堆顶比较（前面会议中最早结束的会议）
            if (intervals[i][0] >= queue.peek()) {
                // 说明之前最早结束的会议已经结束了，可以复用会议室
                //  剔除原先开完的会议
                queue.poll();
            }

            // 肯定是需要将新会议的结束时间加入到堆中的。
            queue.offer(intervals[i][1]);
        }

        // 最后堆中有几场会议，那么就需要几间会议室
        return queue.size();
    }

    public static void main(String[] args) {
        int[][] intervals1 = {{0, 30}, {5, 10}, {15, 20}};
        System.out.println(minMeetingRooms(intervals1)); // Output: 2

        int[][] intervals2 = {{7, 10}, {2, 4}};
        System.out.println(minMeetingRooms(intervals2)); // Output: 1

        int[][] intervals3 = {{1, 5}, {2, 3}, {3, 4}};
        System.out.println(minMeetingRooms(intervals3)); // Output: 2

        int[][] intervals4 = {{1, 5}, {5, 6}, {6, 8}};
        System.out.println(minMeetingRooms(intervals4)); // Output: 1
    }


}
