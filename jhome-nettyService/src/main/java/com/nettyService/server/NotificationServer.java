package com.nettyService.server;

import com.alibaba.fastjson.JSONObject;
import com.nettyService.config.ConfigInfo;
import com.nettyService.model.ChatType;
import com.nettyService.model.bo.NsMessageInformation;
import com.nettyService.service.ChatService;
import com.nettyService.service.NsMessageInformationService;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
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
 * @description:
 * @author: Daxv
 * @create: 2020-09-12 16:47
 **/
@Getter
@Setter
@Component
public class NotificationServer implements Runnable {
    public static final Logger LOGGER = LoggerFactory.getLogger(NotificationServer.class);
    @Autowired
    protected NsMessageInformationService NsMessageInformationService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private ConfigInfo configInfo;

    public NotificationServer() {
    }

    @SneakyThrows
    @Override
    public void run() {
        do {
            try {
                NsMessageInformation NsMessageInformation = new NsMessageInformation();
                NsMessageInformation.setStatus(0);
                NsMessageInformation.setCreatedTime(new Date());//10天以内的消息推送
                NsMessageInformation.setSendCount(configInfo.getSendCount());
                List<NsMessageInformation> NsMessageInformations = NsMessageInformationService.getList(NsMessageInformation);
                if (NsMessageInformations != null) {
                    NsMessageInformations.stream().forEach(c -> {
                        if (c.getSendCount().equals(configInfo.getSendCount()))//次数超过2次以上不在推送                           
                            return;
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("fromUserId", c.getFromUserId());
                        jsonObject.put("toGroupId", c.getToGroupId());
                        jsonObject.put("content", c.getContent());
                        jsonObject.put("type", ChatType.SINGLE_SENDING);
                        chatService.singleSendByRemotely(jsonObject.toJSONString());
                        c.setSendCount(c.getSendCount() + 1);
                    });
                    Thread.sleep(10000);
                    NsMessageInformations.forEach(c -> {
                        NsMessageInformationService.update(c);
                    });                    //设置空指针 GC掉                   
                    NsMessageInformations = null;
                }
            } catch (Exception ex) {
                LOGGER.error("预警推送服务出现异常：" + ex.getMessage());
            } finally {
                Thread.sleep(configInfo.getSendTime());//xx分钟读取 发送一次        
            }
        } while (true);
    }
}