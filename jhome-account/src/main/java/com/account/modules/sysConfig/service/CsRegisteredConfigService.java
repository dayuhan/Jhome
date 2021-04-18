package com.account.modules.sysConfig.service;

import com.account.modules.sysConfig.model.bo.CsRegisteredConfig;
import com.account.modules.sysConfig.model.query.CsRegisteredConfigQuery;
import com.account.modules.sysConfig.model.vo.CsRegisteredConfigModelView;
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
 * @description:  服务类
 * @author:  1
 * @create: 2020-08-24
 **/
public interface CsRegisteredConfigService extends IService<CsRegisteredConfig> {

    /**
     * 添加对象
     */
    ResponseJson addCsRegisteredConfig(CsRegisteredConfigQuery obj);
    /**
     * 编辑对象
     */
    ResponseJson editCsRegisteredConfig(CsRegisteredConfigQuery obj);
    /**
     * 删除对象
     */
    ResponseJson deleteCsRegisteredConfig(CsRegisteredConfigQuery obj);
    /**
     * 批量删除对象
     */
    ResponseJson batchDeleteCsRegisteredConfig(String[] ids);
    /**
     * 分页系统
     */
    IPage<CsRegisteredConfigModelView> queryCsRegisteredConfigList(CsRegisteredConfigQuery queryObject);


    /**
     * 根据Id获取 单个对象
     *
     * @param id
     * @return
     */
    CsRegisteredConfig selectCsRegisteredConfigById(String id);

    /**
     * 根据Ids获取多个对象
     *
     * @param
     */
    List<CsRegisteredConfig> selectBatchCsRegisteredConfigByIds(List<String> idList);

    /**
     * 根据条件获取某个对象
     * @param queryObject
     * @return
     */
    CsRegisteredConfig selectCsRegisteredConfigOne(QueryWrapper<CsRegisteredConfig> queryObject);

    /**
     * 根据自定义条件获取对象实体集合
     * @param queryObject
     * @return
     */
    List<CsRegisteredConfig> selectCsRegisteredConfigList(QueryWrapper<CsRegisteredConfig> queryObject);



}
