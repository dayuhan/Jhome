package com.account.modules.userAuthority.service;


import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhiqiang.liu1
 */
@Api(value = "RedisService", tags = "S、ACC内部Redis服务")
@RestController
public interface RedisService {

    /**
     * TODO(批量删除对应的value)
     *
     * @param keys
     */
    @RequestMapping(value = "/removeBatchKey", method = RequestMethod.POST)
    public void remove(@RequestParam(value = "keys") final String... keys);

    /**
     * TODO(删除对应的value)
     *
     * @param key
     */
    @RequestMapping(value = "/removeKey", method = RequestMethod.POST)
    public void remove(@RequestParam(value = "key") final String key);

    /**
     * TODO(获取key)
     *
     * @param key
     * @return
     */
    @RequestMapping(value = "/getKey", method = RequestMethod.POST)
    public String get(@RequestParam(value = "key") final String key);

    /**
     * TODO(写入缓存,永久)
     * 写入key如果存在,会覆盖,注意业务需要加判断(重新定义key名称)。
     *
     * @param key
     * @param value
     * @return
     */
    @RequestMapping(value = "/setKey", method = RequestMethod.POST)
    public boolean set(@RequestParam(value = "key") final String key, @RequestParam(value = "value") String value);

    /**
     * TODO(写入缓存（有时间限制）)
     *
     * @param key
     * @param value
     * @param expireTime
     * @param timeUnit   TimeUnit.DAYS          //天
     *                   TimeUnit.HOURS         //小时
     *                   TimeUnit.MINUTES       //分钟
     *                   TimeUnit.SECONDS       //秒
     *                   TimeUnit.MILLISECONDS  //毫秒
     * @return
     */
    @RequestMapping(value = "/setKeyTime", method = RequestMethod.POST)
    public boolean set(@RequestParam(value = "key") final String key,
                       @RequestParam(value = "value") String value,
                       @RequestParam(value = "expireTime") Long expireTime,
                       @RequestParam(value = "timeUnit") TimeUnit timeUnit);

    /**
     * TODO(设置key缓存时间)
     *
     * @param key
     * @param expireTime
     * @param timeUnit   TimeUnit.DAYS          //天
     *                   TimeUnit.HOURS         //小时
     *                   TimeUnit.MINUTES       //分钟
     *                   TimeUnit.SECONDS       //秒
     *                   TimeUnit.MILLISECONDS  //毫秒
     * @return
     */
    @RequestMapping(value = "/setExpire", method = RequestMethod.POST)
    public boolean expire(@RequestParam(value = "key") final String key,
                          @RequestParam(value = "expireTime") Long expireTime,
                          @RequestParam(value = "timeUnit") TimeUnit timeUnit);


    /**
     * TODO(判断缓存中是否有对应的value)
     *
     * @param key
     * @return
     */
    @RequestMapping(value = "/existsKey", method = RequestMethod.POST)
    public boolean exists(@RequestParam(value = "key") final String key);


    /**
     * TODO(获取指定key的范围内的value值的 list列表。 （0  -1）返回所有值列表 )
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    @RequestMapping(value = "/rangeListKey", method = RequestMethod.POST)
    List<String> range(@RequestParam(value = "key") final String key,
                       @RequestParam(value = "start") Long start,
                       @RequestParam(value = "end") Long end);

    /**
     * TODO 获取Set集合列表
     * @param content
     * @return
     */
    @RequestMapping(value = "/ListKey", method = RequestMethod.POST)
    String listKey(@RequestParam(value = "content") String content);

}
