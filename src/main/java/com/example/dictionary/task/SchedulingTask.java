package com.example.dictionary.task;

import com.example.dictionary.task.job.CreateJob;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class SchedulingTask {
    /**
     * 程序启动不会直接运行
     * 只有在每小时 3的倍数分钟 00 秒运行（根据cron表达式判断）
     * 如果上一次运行还未结束，则不会运行
     * @Scheduled(cron = "0 0/3 * * * ?")
     *
     * 程序启动直接运行
     * 只有在上一次运行结束后5秒才会运行
     * @Scheduled(fixedDelay=5000)
     *
     * 程序启动直接运行
     * 如果上一次运行时间超过5秒，判断上一次运行是否结束，未结束，则不会运行，结束直接运行
     * 如果上一次运行时间不超过5秒，将在上一次运行开始时间后5秒运行
     * @Scheduled(fixedRate=5000)
     */
    private CreateJob createJob;

    @Scheduled(fixedDelay = 5*60*1000)
    public void delayFiveMinutes(){
        //每隔五分钟执行一次，服务启动立即执行一次
        log.info("五分钟定时器任务开始");
        createJob.doWork();
        log.info("五分钟定时器任务结束");

    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void test(){
        //每天0点自动执行
    }
}
