package com.rpc.common.zk.annotation;

import com.bracket.common.ToolKit.StringUtil;
import com.rpc.common.zk.ZkUtil;
import com.rpc.common.zk.listener.MyTreeCacheListener;
import com.rpc.common.zk.loadBanalce.RandomLoadBalance;
import com.rpc.common.zk.scoket.DoDbTSocketInvokedProxy;
import com.rpc.common.zk.annotation.DubboExpand;
import com.rpc.common.zk.scoket.Invoked;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

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
 * @program: account-root
 * @description: zk注册
 * @author: Daxv
 * @create: 2021-03-26 09:02
 **/
public class ZkRegistry implements ImportSelector {
    private Invoked invoked;

    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(DubboExpand.class.getName(), true));
        String serverName = "/" + attributes.getString("serverName");//服务名称
        String value = attributes.getString("value");//注册IP值
        boolean isDiscovery = attributes.getBoolean("isDiscovery");//是否启用
        //注册到注册中心
        if (StringUtil.isNotBlank(serverName) && StringUtil.isNotBlank(value)) {
            ZkUtil.initialize();//初始化
            ZkUtil.createSequentialEphemeralNode(serverName, value);
            TreeCacheListener listener = new MyTreeCacheListener();
            ZkUtil.registerTreeCacheListener("/", 3, listener);
        }
        //判断是否启用发现
        if (isDiscovery) {
            if (StringUtil.isNotBlank(serverName)) {
                RandomLoadBalance randomLoadBalance = new RandomLoadBalance();
                invoked = new DoDbTSocketInvokedProxy(randomLoadBalance);//委托代理对象调用
                invoked.doStart(serverName);
            }
        }
        return new String[]{"com.rpc.common.zk.scoket.DoDbTSocketInvoked"};
    }
}
