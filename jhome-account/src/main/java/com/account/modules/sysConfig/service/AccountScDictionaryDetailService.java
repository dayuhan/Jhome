package com.account.modules.sysConfig.service;

import com.account.modules.sysConfig.model.bo.AccountScDictionaryDetail;
import com.account.modules.sysConfig.model.query.AccountScDictionaryDetailQuery;
import com.baomidou.mybatisplus.extension.service.IService;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bracket.common.Bus.ResponseJson;

import java.util.List;

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
 * @description: 字典明细表 服务类
 * @author: 1
 * @create: 2020-08-29
 **/
public interface AccountScDictionaryDetailService extends IService<AccountScDictionaryDetail> {

    /**
     * 添加对象
     */
    ResponseJson addAccountScDictionaryDetail(AccountScDictionaryDetailQuery obj);

    /**
     * 编辑对象
     */
    ResponseJson editAccountScDictionaryDetail(AccountScDictionaryDetailQuery obj);

    /**
     * 删除对象
     */
    ResponseJson deleteAccountScDictionaryDetail(AccountScDictionaryDetailQuery obj);

    /**
     * 批量删除对象
     */
    ResponseJson batchDeleteAccountScDictionaryDetail(String[] ids);

    /**
     * 分页系统
     */
    IPage<AccountScDictionaryDetail> selectAccountScDictionaryDetailPageList(AccountScDictionaryDetailQuery queryObject);


    /**
     * 根据Id获取 单个对象
     *
     * @param id
     * @return
     */
    AccountScDictionaryDetail selectAccountScDictionaryDetailById(String id);

    /**
     * 根据Ids获取多个对象
     *
     * @param
     */
    List<AccountScDictionaryDetail> selectBatchAccountScDictionaryDetailByIds(List<String> idList);

    /**
     * 根据条件获取某个对象
     *
     * @param queryObject
     * @return
     */
    AccountScDictionaryDetail selectAccountScDictionaryDetailOne(QueryWrapper<AccountScDictionaryDetail> queryObject);

    /**
     * 根据自定义条件获取对象实体集合
     *
     * @param queryObject
     * @return
     */
    List<AccountScDictionaryDetail> selectAccountScDictionaryDetailList(QueryWrapper<AccountScDictionaryDetail> queryObject);

}
