package com.account.modules.sysConfig.dao;

import com.account.modules.sysConfig.model.bo.CsRegisteredConfig;
import com.account.modules.sysConfig.model.query.CsRegisteredConfigQuery;
import com.account.modules.sysConfig.model.vo.CsRegisteredConfigModelView;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;


/**
 //
 //                       .::::.
 //                     .::::::::.
 //                    :::::::::::
 //                 ..:::::::::::'
 //              '::::::::::::'
 //                .::::::::::
 //           '::::::::::::::..
 //                ..::::::::::::.
 //              ``::::::::::::::::
 //               ::::``:::::::::'        .:::.
 //              ::::'   ':::::'       .::::::::.
 //            .::::'      ::::     .:::::::'::::.
 //           .:::'       :::::  .:::::::::' ':::::.
 //          .::'        :::::.:::::::::'      ':::::.
 //         .::'         ::::::::::::::'         ``::::.
 //     ...:::           ::::::::::::'              ``::.
 //    ```` ':.          ':::::::::'                  ::::..
 //                       '.:::::'                    ':'````..
 * @description:  Mapper 接口
 * @author:  1
 * @create: 2020-08-24
 **/
public interface CsRegisteredConfigMapper extends BaseMapper<CsRegisteredConfig> {
    IPage<CsRegisteredConfigModelView> queryCsRegisteredConfigList(IPage<CsRegisteredConfigModelView> page, @Param("queryObject") CsRegisteredConfigQuery queryObject);
}
