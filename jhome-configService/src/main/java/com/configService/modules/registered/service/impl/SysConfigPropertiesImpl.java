package com.configService.modules.registered.service.impl;

import com.bracket.common.register.ProductCode;
import com.bracket.common.register.Register;
import com.configService.modules.registered.dao.SysConfigPropertiesDao;
import com.configService.modules.registered.model.bo.SysConfigProperties;
import com.configService.modules.registered.model.query.RegisterQuery;
import com.configService.modules.registered.model.query.SysConfigPropertiesQuery;
import com.configService.modules.registered.service.SysConfigPropertiesService;
import com.bracket.common.Bus.ResponseJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
 * @program: account-root
 * @description:
 * @author: Daxv
 * @create: 2020-08-08 13:10
 **/
@Service
public class SysConfigPropertiesImpl implements SysConfigPropertiesService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysConfigPropertiesImpl.class);
    @Autowired
    protected SysConfigPropertiesDao sysConfigPropertiesDao;
    @Autowired
    protected RedisTemplate redisTemplate;

    @Value("${account.serviceUrl}")
    protected String serviceUrl;

    @Override
    public boolean save(SysConfigProperties user) {
        return sysConfigPropertiesDao.save(user);
    }

    @Override
    public boolean update(SysConfigProperties user) {
        return sysConfigPropertiesDao.update(user);
    }

    @Override
    public boolean delete(String id) {
        return sysConfigPropertiesDao.delete(id);
    }

    @Override
    public List<SysConfigProperties> findAll() {
        return sysConfigPropertiesDao.findAll();
    }

    @Override
    public ResponseJson getList(SysConfigPropertiesQuery sysConfigPropertiesQuery) {
        //String applyId = Register.GetCode(ProductCode.PRODUCT_CODE.toString());// 根据产品码生成申请码。
        ResponseJson responseJson = new ResponseJson();
        List<ResponseJson> list = new ArrayList();
        List<SysConfigProperties> sysConfigProperties = sysConfigPropertiesDao.getList(sysConfigPropertiesQuery);
        if (sysConfigProperties == null)
            return null;
        responseJson.success();
        sysConfigProperties.forEach(c -> {
            ResponseJson responseJsonChild = new ResponseJson();
            String[] item = c.getParamName().split("-");
            if (list.stream().filter(t -> t.get("title").equals(item[0])).findFirst().isPresent())
                return;
            responseJsonChild.setValue("title", item[0]);
            List<SysConfigProperties> childList = sysConfigProperties.stream().filter(t -> t.getParamName().contains(item[0])).collect(Collectors.toList());

            //给申请码赋值
      /*      for (SysConfigProperties childListItem : childList) {
                if (childListItem.getParamName().equals("申请码")) {
                    childListItem.setValue(applyId);
                    break;
                }
            }*/
            responseJsonChild.setValue("formdata", childList);
            list.add(responseJsonChild);
        });
        responseJson.setValue("data", list);

        return responseJson;
    }

    @Transactional
    public boolean batchUpdate(List<SysConfigProperties> sysConfigPropertiesList) {
        try {
            AtomicReference<String> declareCodeAtomicReference = new AtomicReference<>("");
            AtomicReference<String> registeredCodeAtomicReference = new AtomicReference<>("");
            AtomicReference<String> authorizationCodeAtomicReference = new AtomicReference<>("");
            try {
                //注册系统
                for (SysConfigProperties e : sysConfigPropertiesList) {
                    switch (e.getParamName()) {
                        case "申请码":
                            declareCodeAtomicReference.set(e.getValue());
                            break;
                        case "注册码":
                            registeredCodeAtomicReference.set(e.getValue());
                            break;
                        case "授权码":
                            authorizationCodeAtomicReference.set(e.getValue());
                            break;
                    }
                    this.update(e);
                }
                /**
                 * 注册信息写入缓存
                 */
                String registerCode = registeredCodeAtomicReference.get();
                String grantCode = replaceBlank(authorizationCodeAtomicReference.get());
                if (!registerCode.isEmpty() && !grantCode.isEmpty()) {

                    // 授权
                    boolean success = Register.Registe(ProductCode.PRODUCT_CODE.toString(), registerCode,
                            grantCode);
                    // 更改授权标识的状态
                    //RegAPI.setVerifyFlag(success);
                    redisTemplate.opsForValue().set(ProductCode.PRODUCT_CODE.toString(), success);
                    this.StartService();
                }
            } catch (Exception ex) {
                LOGGER.info(String.format("批量插入报错：%s", ex.getMessage()));
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean register(RegisterQuery registerQuery) throws Throwable {
        boolean success = false;
        String grantCode = replaceBlank(registerQuery.getGrantCode());
        String registerCode = replaceBlank(registerQuery.getRegisterCode());
        //授权部分
        success = Register.Registe(ProductCode.PRODUCT_CODE.toString(), registerCode, grantCode);
        if (success) {
            //通过redis 修改注册状态
            // 更改授权标识的状态
            //RegAPI.setVerifyFlag(success);
            redisTemplate.opsForValue().set(ProductCode.PRODUCT_CODE.toString(), success);
        }
        return success;
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    /**
     * 注册完成后重新启动服务
     */
    public void StartService() {
        try {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        //C:\Program Files (x86)\com\dsjV1.0\ceshi\bigscreen
                        String commandStop = "cmd /c start " + serviceUrl + "\\ceshi\\bigscreen\\stop_service.bat";
                        String commandStart = "cmd /c start " + serviceUrl + "\\ceshi\\bigscreen\\start_service.bat";
                        LOGGER.info("..............开始重启...............");
                        Runtime.getRuntime().exec(commandStop);
                        Thread.sleep(5000);
                        LOGGER.info("..............正在重启服务...............");
                        Runtime.getRuntime().exec(commandStart);
                        LOGGER.info("..............启动成功...............");
                    } catch (Throwable e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }


}
