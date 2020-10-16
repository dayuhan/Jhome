package com.api.dubbo.service;

import com.alibaba.fastjson.JSONObject;
import com.bracket.common.Bus.ResponseJson;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
 * @create: 2020-10-15 15:29
 **/
public interface AccSysConfigFeignRemoteInterface {
    /*-------------------------------------------参数设置---------------------------------------------------------*/
    /**
     * 添加-  [参数设置]
     */
    @RequestMapping(value = "/zlbzxt/sysConfig/addCsRegisteredConfig", produces = {MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST)
    ResponseJson addCsRegisteredConfig(@RequestBody JSONObject obj);


    /**
     * 根据条件获取 [参数设置] 对象
     *
     * @param query
     * @return
     */
    @PostMapping(value = "/zlbzxt/sysConfig/selectCsRegisteredConfigOne", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseJson selectCsRegisteredConfigOne(@RequestBody JSONObject query);


    /**
     * 根据自定义条件获取 [参数设置] 对象实体集合
     */
    @PostMapping(value = "/zlbzxt/sysConfig/selectCsRegisteredConfigList", produces = { MediaType.APPLICATION_JSON_VALUE})
    ResponseJson selectCsRegisteredConfigList(@RequestBody JSONObject query);



    /*-------------------------------------------字典目录---------------------------------------------------------*/

    /**
     * 根据Id获取 单个 [字典目录] 对象
     */
    @PostMapping(value = "/zlbzxt/sysConfig/selectAccontScDictionaryCatalogueById", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseJson selectAccontScDictionaryCatalogueById(@RequestParam("id") String id);

    /**
     * 根据Ids获取多个 [字典目录] 对象
     *
     * @param ids
     * @return
     */
    @PostMapping(value = "/zlbzxt/sysConfig/selectBatchAccontScDictionaryCatalogueByIds", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseJson selectBatchAccontScDictionaryCatalogueByIds(@RequestParam("ids") String ids);

    /**
     * 根据条件获取 [字典目录] 对象
     *
     * @param query
     * @return
     */
    @PostMapping(value = "/zlbzxt/sysConfig/selectAccontScDictionaryCatalogueOne", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseJson selectAccontScDictionaryCatalogueOne(@RequestBody JSONObject query);

    /**
     * 根据自定义条件获取 [字典目录] 对象实体集合
     *
     * @param query
     * @return
     */
    @PostMapping(value = "/zlbzxt/sysConfig/selectAccontScDictionaryCatalogueList", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseJson selectAccontScDictionaryCatalogueList(@RequestBody JSONObject query);

    /*-------------------------------------------字典明细---------------------------------------------------------*/

    /**
     * 根据Id获取 单个 [字典明细] 对象
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/zlbzxt/sysConfig/selectAccontScDictionaryDetailById", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseJson selectAccontScDictionaryDetailById(@RequestParam("id") String id);


    /**
     * 根据Ids获取多个 [字典明细] 对象
     *
     * @param ids
     * @return
     */
    @PostMapping(value = "/zlbzxt/sysConfig/selectBatchAccontScDictionaryDetailByIds", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseJson selectBatchAccontScDictionaryDetailByIds(@RequestParam("ids") String ids);


    /**
     * 根据条件获取 [字典明细] 对象
     *
     * @param query
     * @return
     */
    @PostMapping(value = "/zlbzxt/sysConfig/selectAccontScDictionaryDetailOne", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseJson selectAccontScDictionaryDetailOne(@RequestBody JSONObject query);


    /**
     * 根据自定义条件获取  [字典明细] 对象实体集合
     *
     * @param query
     * @return
     */
    @PostMapping(value = "/zlbzxt/sysConfig/selectAccontScDictionaryDetailList", produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseJson selectAccontScDictionaryDetailList(@RequestBody JSONObject query);
}
