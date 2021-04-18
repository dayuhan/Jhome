package com.account.modules.sysConfig.service;

import com.account.modules.sysConfig.model.bo.AccountScDictionaryCatalogue;
import com.account.modules.sysConfig.model.query.AccountScDictionaryCatalogueQuery;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bracket.common.Bus.ResponseJson;

import java.util.List;
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
 * @description: 字典分类表 服务类
 * @author:  1
 * @create: 2020-08-29
 **/
public interface AccountScDictionaryCatalogueService extends IService<AccountScDictionaryCatalogue> {

    /**
    * 添加对象
    */
    ResponseJson addAccountScDictionaryCatalogue(AccountScDictionaryCatalogueQuery obj);
    /**
    * 编辑对象
    */
    ResponseJson editAccountScDictionaryCatalogue(AccountScDictionaryCatalogueQuery obj);
    /**
    * 删除对象
    */
    ResponseJson deleteAccountScDictionaryCatalogue(AccountScDictionaryCatalogueQuery obj);
    /**
    * 批量删除对象
    */
    ResponseJson batchDeleteAccountScDictionaryCatalogue(String[] ids);
    /**
    * 分页系统
    */
    IPage<AccountScDictionaryCatalogue> selectAccountScDictionaryCataloguePageList(AccountScDictionaryCatalogueQuery queryObject);


    /**
     * 根据Id获取 单个对象
     *
     * @param id
     * @return
     */
    AccountScDictionaryCatalogue selectAccountScDictionaryCatalogueById(String id);

    /**
     * 根据Ids获取多个对象
     *
     * @param
     */
    List<AccountScDictionaryCatalogue> selectBatchAccountScDictionaryCatalogueByIds(List<String> idList);

    /**
     * 根据条件获取某个对象
     * @param queryObject
     * @return
     */
    AccountScDictionaryCatalogue selectAccountScDictionaryCatalogueOne(QueryWrapper<AccountScDictionaryCatalogue> queryObject);

    /**
     * 根据自定义条件获取对象实体集合
     * @param queryObject
     * @return
     */
    List<AccountScDictionaryCatalogue> selectAccountScDictionaryCatalogueList(QueryWrapper<AccountScDictionaryCatalogue> queryObject);



        }
