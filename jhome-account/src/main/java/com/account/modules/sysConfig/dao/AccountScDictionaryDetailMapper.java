package com.account.modules.sysConfig.dao;

import com.account.modules.sysConfig.model.bo.AccountScDictionaryDetail;
import com.account.modules.sysConfig.model.query.AccountScDictionaryDetailQuery;
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
 * @description: 字典明细表 Mapper 接口
 * @author:  1
 * @create: 2020-08-29
 **/
public interface AccountScDictionaryDetailMapper extends BaseMapper<AccountScDictionaryDetail> {
    IPage<AccountScDictionaryDetail> selectAccountScDictionaryDetailPageList(IPage<AccountScDictionaryDetail> page, @Param("queryObject") AccountScDictionaryDetailQuery queryObject);
}
