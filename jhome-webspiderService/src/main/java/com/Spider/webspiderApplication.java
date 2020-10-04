package com.Spider;

import com.Spider.clientCenter.threadProcess;
import com.Spider.clientCenter.threadProcess2;
import com.bracket.common.Bus.IBus;
import com.bracket.common.Queue.Bus;
import com.bracket.common.Queue.Config;
import com.bracket.common.Queue.QueueHandler;
import com.bracket.common.Queue.ReadQueueDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 爬虫客户端服务
 * @author Administrator
 *采用IO编码模式  以后考虑NIO编程模式
 */
public class webspiderApplication {
    public static void main(String[] args) {
        //Bio 无限创建线程模式
        //ServerSocketByNewThread();
        //Bio 线程池模式 重复利用已有的线程
        ServerSocketByNewExecutorService();

    }
    public static void ServerSocketByNewThread()
    {

        new Thread(() -> {
            do {
                try {
                    new Bus(new Config("192.168.43.93", "daxu", "13528764027", "dxhost"), new IBus() {
                        public void doQueueHandle(QueueHandler queueManager) {
                            for (int i = 0; i < 50; i++) {
                                queueManager.GetQueues("daxu", new ReadQueueDao() {
                                    public void ReadQueue(String pushInfo) {
                                        threadProcess thread= null;
                                        try {
                                            System.out.println("收到消息：" + pushInfo);
                                            thread = new threadProcess(pushInfo);
                                            thread.start();
                                        } catch (Exception e) {
                                            // TODO: handle exception
                                            e.printStackTrace();
                                            thread.disconnect();
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

    public static void ServerSocketByNewExecutorService()
    {
        ExecutorService executorService= Executors.newFixedThreadPool(60);
        new Thread(() -> {
            do {
                try {
                    new Bus(new Config("192.168.43.93", "daxu", "13528764027", "dxhost"), new IBus() {
                        public void doQueueHandle(QueueHandler queueManager) {
                            for (int i = 0; i < 50; i++) {
                                queueManager.GetQueues("daxu", new ReadQueueDao() {
                                    public void ReadQueue(String pushInfo) {
                                        threadProcess2 thread= null;
                                        try {
                                            System.out.println("收到消息：" + pushInfo);
                                            thread = new threadProcess2(pushInfo);
                                            executorService.execute(thread);
                                        } catch (Exception e) {
                                            // TODO: handle exception
                                            e.printStackTrace();
                                            thread.disconnect();
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
}
