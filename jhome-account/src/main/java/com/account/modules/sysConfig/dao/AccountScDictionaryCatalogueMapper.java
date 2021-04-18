package com.account.modules.sysConfig.dao;

import com.account.modules.sysConfig.model.bo.AccountScDictionaryCatalogue;
import com.account.modules.sysConfig.model.query.AccountScDictionaryCatalogueQuery;
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
 * @description: 字典分类表 Mapper 接口
 * @author:  1
 * @create: 2020-08-29
 **/
public interface AccountScDictionaryCatalogueMapper extends BaseMapper<AccountScDictionaryCatalogue> {
    IPage<AccountScDictionaryCatalogue> selectAccountScDictionaryCataloguePageList(IPage<AccountScDictionaryCatalogue> page, @Param("queryObject") AccountScDictionaryCatalogueQuery queryObject);
}
