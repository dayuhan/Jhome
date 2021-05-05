package com.rpc.common.zk.scoket;


import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

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
 * @description: 重载TSocket
 * @author: Daxv
 * @create: 2021-03-26 09:48
 **/
public class DbTSocketExpand extends TSocket {
    private static final Logger LOGGER = LoggerFactory.getLogger(TSocket.class.getName());
    private Socket socket_;
    private String host_;
    private int port_;
    private int socketTimeout_;
    private int connectTimeout_;

    public DbTSocketExpand(Socket socket) throws TTransportException {
        super(socket);
    }

    public DbTSocketExpand(String host, int port) {
        super(host, port);
    }

    public DbTSocketExpand(String host, int port, int timeout) {
        super(host, port, timeout);
    }

    public DbTSocketExpand(String host, int port, int socketTimeout, int connectTimeout) {
        super(host, port, socketTimeout, connectTimeout);
    }

    public void open() throws TTransportException {
        if (this.isOpen()) {
            throw new TTransportException(2, "Socket already connected.");
        } else if (this.host_ != null && this.host_.length() != 0) {
            if (this.port_ > 0 && this.port_ <= 65535) {
                if (this.socket_ == null) {
                    this.initSocket();
                }

                try {
                    this.socket_.connect(new InetSocketAddress(this.host_, this.port_), this.connectTimeout_);
                    this.inputStream_ = new BufferedInputStream(this.socket_.getInputStream());
                    this.outputStream_ = new BufferedOutputStream(this.socket_.getOutputStream());
                } catch (IOException var2) {
                    this.close();
                    throw new TTransportException(1, var2);
                }
            } else {
                throw new TTransportException(1, "Invalid port " + this.port_);
            }
        } else {
            throw new TTransportException(1, "Cannot open null host.");
        }
    }

    private void initSocket() {
        this.socket_ = new Socket();
        try {
            this.socket_.setSoLinger(false, 0);
            this.socket_.setTcpNoDelay(true);
            this.socket_.setKeepAlive(true);
            this.socket_.setSoTimeout(this.socketTimeout_);
        } catch (SocketException var2) {
            LOGGER.error("Could not configure socket.", var2);
        }
    }

    public Socket getSocket_() {
        return socket_;
    }

    public void setSocket_(Socket socket_) {
        this.socket_ = socket_;
    }

    public String getHost_() {
        return host_;
    }

    public void setHost_(String host_) {
        this.host_ = host_;
    }

    public int getPort_() {
        return port_;
    }

    public void setPort_(int port_) {
        this.port_ = port_;
    }

    public int getSocketTimeout_() {
        return socketTimeout_;
    }

    public void setSocketTimeout_(int socketTimeout_) {
        this.socketTimeout_ = socketTimeout_;
    }

    public int getConnectTimeout_() {
        return connectTimeout_;
    }

    public void setConnectTimeout_(int connectTimeout_) {
        this.connectTimeout_ = connectTimeout_;
    }
}
