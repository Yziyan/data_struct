package run.ciusyan.Top;


import java.util.Arrays;

public class 会议室1 {

    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return true;

        // 按照会议的开始时间，对所有会议进行排序
        Arrays.sort(intervals, ((o1, o2) -> o1[0] - o2[0]));

        // 排序过后，遍历会议
        for (int i = 1; i < intervals.length; i++) {
            // 说明后一场会议的开始时间，比前一次会议的结束时间还小，说明有重叠
            if (intervals[i][0] < intervals[i - 1][1]) return false;
        }

        return true;
    }

    public static void main(String[] args) {
        会议室1 test = new 会议室1();

        // test case 1, 返回 true
        int[][] intervals1 = {{0, 30}, {35, 45}, {50, 65}};
        System.out.println(test.canAttendMeetings(intervals1));

        // test case 2, 返回 false
        int[][] intervals2 = {{0, 30}, {15, 45}, {50, 65}};
        System.out.println(test.canAttendMeetings(intervals2));

        // test case 3, 返回 true
        int[][] intervals3 = {{1, 2}, {2, 3}, {3, 4}};
        System.out.println(test.canAttendMeetings(intervals3));

        // test case 4, 返回 true
        int[][] intervals4 = {{0, 5}, {6, 8}, {10, 12}};
        System.out.println(test.canAttendMeetings(intervals4));
    }
}
