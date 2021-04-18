package com.shiro.common.session;

import com.alibaba.fastjson.JSONObject;
import com.domain.common.PermissionContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Title: ServiceBFeignClient
 * @ProjectName springcloud_feign
 * @Description: FeignClient
 * @Author daxv
 * 1. FeignClient必须是一个接口interface
 * 2. 必须加上@FeignClient指定需要调用哪个服务的接口
 * 3. Feign默认集成了Ribbon，所以通过Feign也可以实现服务的负载均衡调用(轮询方式)。
 * <p>
 * Feign的实现的过程大致如下：
 * a. 首先通过@EnableFeignClients注解开启FeignClient
 * b. 根据Feign的规则实现接口，并加@FeignClient注解
 * c. 程序启动后，会进行包扫描，扫描所有的@ FeignClient的注解的类，并将这些信息注入到ioc容器中。
 * d. 当接口的方法被调用，通过jdk的代理，来生成具体的RequestTemplate
 * e. RequestTemplate在生成Request
 * f. Request交给Client去处理，其中Client可以是HttpUrlConnection、HttpClient也可以是Okhttp
 * g. 最后Client被封装到LoadBalanceClient类，这个类结合类Ribbon做到了负载均衡
 */
//@FeignClient注解通过value指定调用的服务名称，对应application.yml的application-name，如本例为eureka-feign-service-b
//通过fallback指定远程服务调用失败的回调方法，也叫服务降级处理,回调类必须实现使用@FeignClient标识的接口（implements ServiceBFeignClient）

public interface RemoteBaseInterface {
    @RequestMapping(value = "/jhome/RemoteService/getSession", method = RequestMethod.POST)
    String getSession(@RequestParam("sessionId") String sessionId);

    @RequestMapping(value = "/jhome/RemoteService/createSession", method = RequestMethod.POST)
    String createSession(@RequestParam("sessionJson") String sessionJson);
    //ResponResult createSession(@RequestBody ShiroSession session);

    @RequestMapping(value = "/jhome/RemoteService/updateSession", method = RequestMethod.POST)
    String updateSession(@RequestParam("sessionJson") String sessionJson);

    @RequestMapping(value = "/jhome/RemoteService/deleteSession", method = RequestMethod.POST)
    //boolean deleteSession(@RequestBody RequestResult result);
    String deleteSession(@RequestParam("sessionJson") String sessionJson);

    @RequestMapping(value = "/jhome/RemoteService/getPermissions", method = RequestMethod.POST)
    PermissionContext getPermissions(@RequestParam("username")  String username);

    @RequestMapping(value = "/jhome/RemoteService/getCallbackUrl", method = RequestMethod.POST)
    String getCallbackUrl();

    /**
     * 对象请求 使用 JSONObject
     * JSONObject obj=new JSONobject()
     * obj.put("key","00");
     * this.getSessionTest(obj)
     */
//    @RequestMapping(value = "/account/RemoteService/getSession", method = RequestMethod.POST)
//    String getSessionTest(@RequestBody JSONObject obj);


}
