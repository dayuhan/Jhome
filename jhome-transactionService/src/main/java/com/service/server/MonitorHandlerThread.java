package com.service.server;

import com.bracket.common.Queue.Bus;
import com.service.config.ConfigInfo;
import com.service.model.MessageType;
import com.service.model.Transactionlog;
import com.service.service.TransactionlogService;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
 * @description: 执行巡检消息
 * 保证上游服务对消息的 100% 可靠投递 *
 * @author: Daxv
 * @create: 2020-09-16 18:58
 **/
@Service
@Getter
@Setter
public class MonitorHandlerThread implements Runnable {
    @Autowired
    private Bus bus;
    @Autowired
    private TransactionlogService transactionlogService;
    @Autowired
    private ConfigInfo configInfo;

    @SneakyThrows
    @Override
    public void run() {
        try {
            do {
                List<Transactionlog> transactionlogList = transactionlogService.getList(null);
                transactionlogList.stream().forEach(t -> {
                    // 1,巡检 消息一直是“待确认”状态
                    if (t.getType().equals(MessageType.WAITINGCONFIRM.getType())) {
                        //http请求 回调上游服务
                        //transactionlogService.SendByPostRequest()
                    }
                    // 2,巡检 消息状态一直是“已经提交事务 已发送”
                    if (t.getType().equals(MessageType.COMMIT_TRANSACTION.getType())) {
                        //http请求 回调上游服务
                    }
                    // 3,巡检 消息状态是“需要回滚的事务”
                    if (t.getType().equals(MessageType.BACK_TRANSACTION.getType())) {
                        //http请求 回调上游服务
                    }
                });
                Thread.sleep(10000);//休息10s后继续执行
            }
            while (true);
        } catch (Exception ex) {

        }
    }
}
