package com.bracket.common.Queue;

import com.bracket.common.Bus.PushInfo;
import com.bracket.common.Bus.PushTypeBase;
import com.bracket.common.ToolKit.JSONUtils;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class RabbitMqUtil {
	private final static String EXCHANGE_NAME = "exchange_direct";
	private ConnectionFactory factory;
	private Connection con;
	private Channel ch;
	private String pushJson = null;
	public RabbitMqUtil() {
		super();
	}

	/**
	 * 构造队列对象
	 * 
	 * @param rabbitConfig
	 */
	public RabbitMqUtil(Config rabbitConfig) {
		factory = new ConnectionFactory();
		List<String> listHostName = new ArrayList<String>();
		try {
			Calendar calendar = Calendar.getInstance();
			if (calendar.get(calendar.SECOND) % 2 == 0) {
				listHostName = Arrays.asList(rabbitConfig.getHostName().split(
						","));
			} else {
				listHostName = Arrays.asList(rabbitConfig.getHostName().split(
						","));
			}
			for (String item : listHostName) {
				factory.setHost(item);
				factory.setVirtualHost(rabbitConfig.getVirtualHost());
				factory.setUsername(rabbitConfig.getUserName());
				factory.setPassword(rabbitConfig.getPassWord());
				con = factory.newConnection();
			}
			//System.out.println("兄弟我只能执行一次不要犯傻，创建一大堆TCP连接，耗死自己哦~~~");
			ch = con.createChannel();
			exchangeConfig();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 添加到队列 消息-对象
	 * 
	 * @param queueName
	 * @param pushInfo
	 *            对象
	 * @return
	 * @throws TimeoutException
	 * @throws IOException
	 */
	public <T extends PushTypeBase> boolean AddProducerMQ(String queueName,
                                                          T pushInfo) throws IOException, TimeoutException {
		boolean flag = false;
		try {
			//声明队列
			ch.queueDeclare(queueName, true, false, false, null);
			ch.queueBind(queueName, EXCHANGE_NAME, queueName);
			String pushJsonStr = JSONUtils.beanToJson(pushInfo);
			ch.basicPublish(EXCHANGE_NAME, queueName, null,
					pushJsonStr.getBytes());
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			/*ch.close();
			con.close();*/
		}
		return flag;
	}

	/**
	 * 添加到队列 消息-字符串
	 * 
	 * @param queueName
	 * @param messAge
	 * @return
	 * @throws TimeoutException
	 * @throws IOException
	 */
	public boolean AddProducerMQ(String queueName, String messAge)
			throws IOException, TimeoutException {
		boolean flag = false;
		try {
			ch.queueDeclare(queueName, true, false, false, null);
			ch.queueBind(queueName, EXCHANGE_NAME, queueName);
			String pushJsonStr = messAge;
			ch.basicPublish(EXCHANGE_NAME, queueName, null,
					pushJsonStr.getBytes());
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			/*ch.close();
			con.close();*/
		}
		return flag;
	}

	/**
	 * 获取队列中的消息
	 * 
	 * @param queueName
	 * @param isDelete
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public String GetQueues(String queueName, boolean isDelete,final ReadQueueDao rQueueDao)
			throws IOException, TimeoutException {
		String jsonStr = new String();
		PushInfo pushInfo = new PushInfo();
		try {
			// QueueingConsumer 弃用
			// aotoAck = true，不需要回复，接收到消息后，queue上的消息就会清除
			// aotoAck =
			// false，需要回复，接收到消息后，queue上的消息不会被清除，直到调用channel.basicAck(deliveryTag,
			// false); queue上的消息才会被清除 而且，在当前连接断开以前，其它客户端将不能收到此queue上的消息
			// 监听队列，false表示手动返回完成状态，true表示自动
			// ch.basicConsume(queueName, false, new MyConsumer(ch,con)); 
			
			ch.basicConsume(queueName, false, new Consumer() {
			 
				public void handleDelivery(String arg0, Envelope arg1,
						BasicProperties arg2, byte[] arg3) throws IOException {  
					/*
					 * System.err.println("-----------consume message----------")
					 * System.err.println("consumerTag: " + consumerTag);
					 * System.err.println("envelope: " + envelope);
					 * System.err.println("properties: " + properties);
					 * System.err.println("body: " + new String(body));
					 */
					try { 
					    pushJson = new String(arg3); //向外传参
					    //System.out.println(pushJson);
						// backMessAge(new PushInfo());//向外传参 不能直接修改方法变量的值；可以访问父类的成员变量
						if (!pushJson.isEmpty()) {
							//具体业务逻辑实现...
							rQueueDao.ReadQueue(pushJson); 
							//返回确认状态，手动模式
							ch.basicAck(arg1.getDeliveryTag(), false);  
						}
					} catch (Exception e) {
						// TODO: handle exception
					}   
				}  
				public void handleConsumeOk(String consumerTag) {
					//System.out.println("handleConsumeOk"); 
				}
 
				public void handleCancelOk(String consumerTag) {
					//System.out.println("handleCancelOk");
				}
 
				public void handleCancel(String consumerTag) throws IOException {
					//System.out.println("handleCancel");
				}
 
				public void handleShutdownSignal(String consumerTag,
						ShutdownSignalException sig) {
					 //System.out.println("handleShutdownSignal");
				}
 
				public void handleRecoverOk(String consumerTag) {
					//System.out.println("handleRecoverOk");
				}

				// 自定义函数，通过对象引用方式传出值/传人值
				public Consumer backMessAge(PushInfo pushInfo) {
					return this;
				}
			}); 
			
		/*	if (!pushJson.isEmpty()) {
				System.out.println("获取消息是：" + pushJson);
				return pushJson;
			}*/

		} catch (Exception e) {
			// TODO: handle exception
		} finally {

		}
		return pushJson;
	}

	/**
	 * 清楚制定任务
	 * 
	 * @return
	 * @throws TimeoutException
	 * @throws IOException
	 */
	public boolean removeQueue() throws IOException, TimeoutException {
		boolean flag = false;
		try {
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			ch.close();
			con.close();
		}
		return flag;
	}

	/**
	 * 配置交换机
	 * 
	 * @throws IOException
	 */
	public void exchangeConfig() throws IOException {
		ch.basicQos(1);// 同一时刻服务器只会发一条消息给消费者
		// direct：转发消息到routigKey指定的队列 路由模式
		// topic：按规则转发消息（最灵活）
		// headers：（这个还没有接触到）
		// fanout：转发消息到所有绑定队列 订阅模式
		boolean persistMode = true;// 是否对消息队列持久化保存
		ch.exchangeDeclare(EXCHANGE_NAME, "direct");
	}
	/**
	 * 释放连接
	 * @throws IOException
	 * @throws TimeoutException
	 */
    public void dispose() throws IOException, TimeoutException
    {
        if (ch != null && ch.isOpen())
        {
            ch.close(); 
        }
        if (con != null && con.isOpen())//has AutoClose , dobule check
        {
            con.close(); 
        }
    }
}
