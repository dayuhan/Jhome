package com.service.service.impl;

import com.bracket.common.Bus.AbstractDaoImpl.BaseDaoImpl;
import com.bracket.common.Bus.AbstractDaoImpl.HttpDaoImpl;
import com.rpc.common.thrift.socketService;
import jdk.internal.util.xml.impl.ReaderUTF8;
import org.apache.thrift.TException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
 * @create: 2020/5/14 0014
 **/
@Service
public class SocketServiceImpl extends HttpDaoImpl implements socketService.Iface {

    @Override
    public void singleSend(String param) throws TException {
    }

    @Override
    public void groupSend(String param) throws TException {
    }

    @Override
    public String getOnlineUserList() throws TException {
        return null;
    }
}
