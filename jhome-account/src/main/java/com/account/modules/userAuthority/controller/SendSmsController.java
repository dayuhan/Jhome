package com.account.modules.userAuthority.controller;

import com.account.common.CommonRlt;
import com.account.modules.userAuthority.model.request.SmsMsgRequest;
import com.account.modules.userAuthority.service.SendSmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author <a href="soin834294085@sina.cn">Levi_liu</a>
 */
@Api(value = "发送短信", tags = "Q、权限服务-发送短信")
@RestController
@Slf4j
@RequestMapping(value = "/sendSms", produces = {MediaType.APPLICATION_JSON_VALUE})
public class SendSmsController  {

    @Autowired
    SendSmsService sendSmsService;

    @RequestMapping(value = "/captcha", method = RequestMethod.POST)
    @ApiOperation(value = "短信验证码", notes = "message authentication code")
    public CommonRlt smsCaptcha (@Validated @RequestBody SmsMsgRequest request, BindingResult result) throws Exception {
        log.info("SendSmsController smsCaptcha show info ：" + request);
        return sendSmsService.smsCaptcha(request);
    }
}
