package com.account.common.utils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

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
 * @description: mybatis plus 字段填充
 * @author: Daxv
 * @create: 2020-12-07 20:05
 * 例子在字段上加：
 * @TableField(value="`update_time`",fill=FieldFill.INSERT_UPDATE)
 **/
@Component
public class FillColumnMetaHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        Date thisDate = new Date();
        String fillMeta = "";
        if (metaObject.hasSetter(fillMeta)) {
            //设置字段值
        }
        fillMeta = "createTime";
        if (metaObject.hasSetter(fillMeta)) {
            //设置字段值
            this.setFieldValByName(fillMeta, thisDate, metaObject);
        }
        fillMeta = "isDel";
        if (metaObject.hasSetter(fillMeta)) {
            this.setFieldValByName(fillMeta, 0, metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        Date thisDate = new Date();
        String fillMeta = "updateTime";
        if (metaObject.hasSetter(fillMeta)) {
            //设置字段值
            this.setFieldValByName(fillMeta, thisDate, metaObject);
        }
    }
}
