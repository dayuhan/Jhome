package com.bracket.common.ServerSocket;

import java.io.IOException;
import java.net.*;

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
 * @description: socket帮助类
 * @author: Daxv
 * @create: 2021-03-19 09:47
 **/
public class SocketUtil {
    /**
     * Java 判断IP和端口是否可连接
     *
     * @param server
     * @param servPort
     * @throws IOException
     * @throws UnknownHostException
     * @throws SocketTimeoutException
     */
    public void IsConnect(String server, int servPort) throws IOException, UnknownHostException, SocketTimeoutException {


        InetAddress ad = InetAddress.getByName(server);
        boolean state = ad.isReachable(1000);//测试是否可以达到该地址 ,判断ip是否可以连接 1000是超时时间
        if (state) {
            System.out.println("连接成功" + ad.getHostAddress());
        } else {
            System.out.println("连接失败");

            throw new IOException();
        }
        //1.创建一个Socket实例：构造函数向指定的远程主机和端口建立一个TCP连接
        //socket = new Socket(server, servPort);
        Socket socket = new Socket();
        socket.setReceiveBufferSize(8192);
        socket.setSoTimeout(1000);// socket.setSoTimeout(2000);
        SocketAddress address = new InetSocketAddress(server, servPort);
        try {
            socket.connect(address, 1000);//1.判断ip、端口是否可连接
        } catch (IOException e) {
            System.out.println("新建一个 socket server " + servPort + "连接失败");
            throw new IOException();
        }
        System.out.println("新建一个socket");
        System.out.println("Connected to server... sending echo string");
        //2. 通过套接字的输入输出流进行通信：一个Socket连接实例包括一个InputStream和一个OutputStream，它们的用法同于其他Java输入输出流。
//        in = socket.getInputStream();
//        out = socket.getOutputStream();
//        isalreadyconnected=1;
        //connect1( server,  servPort) ;
    }
}
