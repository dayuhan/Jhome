package com.configService.modules.registered.dao;

import com.configService.modules.registered.model.bo.SysConfigProperties;
import com.configService.modules.registered.model.query.SysConfigPropertiesQuery;

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
 * @program: jhome-root
 * @description:
 * @author: Daxv
 * @create: 2020-08-08 13:00
 **/
public interface SysConfigPropertiesDao {
    /**
     * 保存用户
     * @param user
     */
    boolean save(SysConfigProperties user);


    /**
     * 修改
     * @param user
     */
    boolean update(SysConfigProperties user);

    /**
     * 删除
     * @param id
     */
    boolean delete(String id);

    /**
     * 查询全部配置
     * @return
     */
    List<SysConfigProperties> findAll();


    /**
     * 根据条件查询
     * @param
     * @return
     */
    List<SysConfigProperties>  getList(SysConfigPropertiesQuery sysConfigPropertiesQuery);

}
