package com.bracket.common.Queue;

import com.bracket.common.Bus.IBus;

/**
 * 队列服务
 *
 * @author Administrator
 */
public class Bus {
    public IBus ibus;
    public QueueHandler queueHandler;
    public Config queueConfig;

    /**
     *
     * @param queueConfig
     */
    public Bus(Config queueConfig) {
        this.queueConfig=queueConfig;
        System.out.println(String.format("来自Jhome消息：Bus注入容器------  >..<   OK 注入参数信息：%s",queueConfig.toString()));
    }

    /**
     * 方式一 构造函数 直接调用
     * @param config
     * @param person
     */
    public Bus(Config config, IBus person) {
        queueHandler = new QueueHandler(config);
        ibus = person;
    }

    /**
     * 方式二 加入消息
     * @param person
     * @return
     */
    public boolean AddMessAgeByQueue(IBus person)
    {
        queueHandler = new QueueHandler(this.queueConfig);
        ibus = person;
       return this.Invoke();
    }
    //发送消息
    public boolean Invoke() {
        boolean flag = false;
        try {
            ibus.doQueueHandle(queueHandler);
            //释放TCP通道
            queueHandler.getQueue().getRaMqUtil().dispose();
            flag = true;
        } catch (Exception e) {
            // TODO: handle exception
            flag = false;
        }
        return flag;
    }


}
