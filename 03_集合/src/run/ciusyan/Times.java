package run.ciusyan;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间初略测试工具
 */
public class Times {

    private static final SimpleDateFormat FMT = new SimpleDateFormat("HH:mm:ss.SSS");

    /**
     * 需要测试的任务
     */
    public interface Task {
        void execute();
    }

    public static void test(String title, Task task) {
        if (task == null) return;
        System.out.println("-------------------------------");
        title = title == null ? "" : ("【" + title + "】");
        System.out.println(title);
        System.out.println("开始测试：" + FMT.format(new Date()));
        long begin = System.currentTimeMillis();
        // 执行测试代码
        task.execute();
        long end = System.currentTimeMillis();
        System.out.println("结束测试：" + FMT.format(new Date()));
        double times = (end - begin) / 1000.0; // 将其转换为秒
        System.out.println("此次测试耗时：" + times + "s");
        System.out.println("-------------------------------");

    }

}
