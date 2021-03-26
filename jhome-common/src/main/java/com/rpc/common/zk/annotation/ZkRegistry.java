package com.rpc.common.zk.annotation;

import com.bracket.common.ToolKit.StringUtil;
import com.rpc.common.zk.ZkUtil;
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
 * @program: jhome-root
 * @description: 注册到zookper中
 * @author: Daxv
 * @create: 2021-03-12 17:13
 **/
public class ZkRegistry implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        //获取 标记对应的属性
        AnnotationAttributes attributes=AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(ZkRegistry.class.getName(),true));
        String path=attributes.getString("path");
        String value=attributes.getString("value");
        if (StringUtil.isNotBlank(path)&&StringUtil.isNotBlank(value))
        {
            //初始化
            ZkUtil.initialize();
            //注册有序节点
            ZkUtil.createSequentialPersistentNode(path,value);
        }
        return new String[0];
    }
}
