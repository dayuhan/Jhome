package com.service.server;/**
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-10-05 18:11
 **/

import com.bracket.common.Queue.Bus;
import com.service.config.ConfigInfo;
import com.service.model.MessageType;
import com.service.model.Transactionlog;
import com.service.service.TransactionlogService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
 * @program: jhome-root
 * @description: 巡检服务
 * @author: Daxv
 * @create: 2020-10-05 18:11
 **/
@Component
public class monitorHandlerJob implements Job {
    protected Logger logger = LoggerFactory.getLogger(monitorHandlerJob.class);
    @Autowired
    private Bus bus;
    @Autowired
    private TransactionlogService transactionlogService;
    @Autowired
    private ConfigInfo configInfo;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        logger.info(String.format("我在巡检中...线程ID：%s 线程名称：%s", Thread.currentThread().getId(), Thread.currentThread().getName()));

    }
}
