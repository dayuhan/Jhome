package com.nettyService.service;

import com.nettyService.model.bo.NsMessageInformation;

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
 * @program: jhome-root
 * @description: 离线消息
 * @author: Daxv
 * @create: 2020-09-12 16:25
 **/
public interface NsMessageInformationService {
    /**
     * 保存用户
     *
     * @param nsMessageInformation
     */
    public boolean save(NsMessageInformation nsMessageInformation);


    /**
     * 修改
     *
     * @param nsMessageInformation
     */
    public boolean update(NsMessageInformation nsMessageInformation);

    /**
     * 删除
     *
     * @param id
     */
    public boolean delete(String id);

    /**
     * 查询全部配置
     *
     * @return
     */
    public List<NsMessageInformation> findAll();

    /**
     * 根据条件查询
     *
     * @param
     * @return
     */
    public List<NsMessageInformation> getList(NsMessageInformation NsMessageInformation);
}
