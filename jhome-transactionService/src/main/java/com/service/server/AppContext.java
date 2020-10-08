package com.service.server;

import com.bracket.common.Bus.IBus;
import com.bracket.common.Queue.Bus;
import com.bracket.common.Queue.Config;
import com.bracket.common.Queue.QueueHandler;
import com.bracket.common.Queue.ReadQueueDao;
import com.service.config.ConfigInfo;
import io.swagger.annotations.ApiModelProperty;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * //
 * //                       .::::.
 * //                     .::::::::.
 * //                    :::::::::::
 * //                 ..:::::::::::'
 * //              '::::::::::::'
 * //                .::::::::::
 * //           '::::::::::::::..
 * //                ..::::::::::::.
 * //              ``::::::::::::::::
 * //               ::::``:::::::::'        .:::.
 * //              ::::'   ':::::'       .::::::::.
 * //            .::::'      ::::     .:::::::'::::.
 * //           .:::'       :::::  .:::::::::' ':::::.
 * //          .::'        :::::.:::::::::'      ':::::.
 * //         .::'         ::::::::::::::'         ``::::.
 * //     ...:::           ::::::::::::'              ``::.
 * //    ```` ':.          ':::::::::'                  ::::..
 * //                       '.:::::'                    ':'````..
 *
 * @description: 启动服务
 * @author: Daxv
 * @create: 2020-09-16 19:34
 **/
@Component
public class AppContext {
    protected final Logger logger = LoggerFactory.getLogger(AppContext.class);

    @Autowired
    protected ConfigInfo configInfo;
    @Autowired
    protected HandlerThread handlerThread;
    @Autowired
    protected MonitorHandlerThread monitorHandlerThread;

    /**
     * 启动服务器
     */
    public void run() {
        try {
            logger.info(

                    "    _ _                                 _                                  _   _              _____                 _          \n" +
                            "   (_) |                               | |                                | | (_)            / ____|               (_)         \n" +
                            "    _| |__   ___  _ __ ___   ___ ______| |_ _ __ __ _ _ __  ___  __ _  ___| |_ _  ___  _ __ | (___   ___ _ ____   ___  ___ ___ \n" +
                            "   | | '_ \\ / _ \\| '_ ` _ \\ / _ \\______| __| '__/ _` | '_ \\/ __|/ _` |/ __| __| |/ _ \\| '_ \\ \\___ \\ / _ \\ '__\\ \\ / / |/ __/ _ \\\n" +
                            "   | | | | | (_) | | | | | |  __/      | |_| | | (_| | | | \\__ \\ (_| | (__| |_| | (_) | | | |____) |  __/ |   \\ V /| | (_|  __/\n" +
                            "   | |_| |_|\\___/|_| |_| |_|\\___|       \\__|_|  \\__,_|_| |_|___/\\__,_|\\___|\\__|_|\\___/|_| |_|_____/ \\___|_|    \\_/ |_|\\___\\___|\n" +
                            "  _/ |                                                                                                                         \n" +
                            " |__/                                                                                                                         " +

                            "" + "Spring 3.0 " +
                            "" +
                            "");
            logger.info("启动可靠消息服务！...");
            long startTime = System.currentTimeMillis();   //获取开始时间
            SendAndReceive();
            long endTime = System.currentTimeMillis(); //获取结束时间
            logger.info("启动成功！启动耗时：%s 毫秒", startTime - endTime);
            Thread.sleep(4000);

            logger.info("启动巡检服务！...");
            long startTime1 = System.currentTimeMillis();   //获取开始时间
            //new Thread(monitorHandlerThread).start();
            this.StartMonitorHandlerJob();
            long endTime1 = System.currentTimeMillis(); //获取结束时间
            logger.info("启动成功！启动耗时：%s 毫秒", startTime1 - endTime1);

        } catch (Exception ex) {
            logger.info(String.format("启动服务器失败：s%", ex.getMessage()));
        }
    }

    /**
     * 获取定位的消息
     */
    public void SendAndReceive() {
        ExecutorService executorService = Executors.newFixedThreadPool(60);
        new Thread(() -> {
            do {
                try {
                    new Bus(new Config(configInfo.getHostName(), configInfo.getUserName(), configInfo.getPassWord(), configInfo.getVirtualHost()), new IBus() {
                        public void doQueueHandle(QueueHandler queueManager) {
                            for (int i = 0; i < 50; i++) {
                                queueManager.GetQueues("TransactionHandler", new ReadQueueDao() {
                                    public void ReadQueue(String pushInfo) {
                                        HandlerThread thread = null;
                                        try {
                                            logger.info("收到消息！..." + pushInfo);
                                            handlerThread.setPushInfo(pushInfo);
                                            //thread = new HandlerThread(pushInfo);
                                            executorService.execute(handlerThread);
                                        } catch (Exception e) {
                                            // TODO: handle exception
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        }
                    }).Send();
                    Thread.sleep(500);
                } catch (Exception e) {
                    // TODO: handle exception
                }
            } while (true);
        }).start();
    }

    public void StartMonitorHandlerJob() throws SchedulerException {
        //创建任务
        JobDetail jobDetail = JobBuilder.newJob(monitorHandlerJob.class)
                .withDescription("执行巡检服务")
                .withIdentity("monitorJob", "monitorJob-group")
                .build();

        JobDetail jobDetail1= JobBuilder.newJob(monitorHandlerJob.class)
                .withDescription("执行巡检服务")
                .withIdentity("monitorJob1", "monitorJob-group1")
                .build();

        //创建触发器
        Trigger triggers = TriggerBuilder.newTrigger()
                .withDescription("每2分钟执行一次")
                .startAt(new Date())
                .withIdentity("Trigger", "Trigger-group")
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(60))
                //.withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(1))
                //.withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(10, 60))
                .build();
        //创建一个调度器
        StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail, triggers);
        //启动任务
        scheduler.start();
    }

}
