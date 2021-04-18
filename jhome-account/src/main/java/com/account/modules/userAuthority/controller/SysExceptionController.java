package com.account.modules.userAuthority.controller;

import com.account.common.CommonRlt;
import com.account.modules.userAuthority.model.request.SysExceptionRequest;
import com.account.modules.userAuthority.service.SysExceptionService;
import com.ar.common.exception.IllegalValidateException;
import com.ar.common.rest.BasicRestStatusEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author daxv
 * @date 2019/10/8 11:10
 */
@Api(value = "异常记录管理", tags = "Q、权限服务-异常记录管理")
@RestController
@RequestMapping(value = "/sysException", produces = {MediaType.APPLICATION_JSON_VALUE})
public class SysExceptionController  {

    @Autowired
    SysExceptionService service;

    @Autowired
    StringEncryptor stringEncryptor;

    @PostMapping(value="/addSysException",headers="luxToken")
    @ApiOperation(value = "记录异常信息", notes = "Requires AccessToken")
    public CommonRlt addSysException(@Validated @RequestBody SysExceptionRequest request, BindingResult result) throws
            IllegalValidateException {

        service.addSysException(request);
        return new CommonRlt<>(BasicRestStatusEnum.OK);
    }

    @ApiOperation("加密")
    @RequestMapping(value = "/encrypt",method = RequestMethod.GET)
    public CommonRlt<String> encrypt(String encryptStr,String encryptor) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //加密所需的salt(盐)
        textEncryptor.setPassword(encryptor);
        String aa=stringEncryptor.encrypt(encryptStr);
        String result= textEncryptor.encrypt(encryptStr);
        return new CommonRlt<>(BasicRestStatusEnum.OK, result);
    }

    @ApiOperation("解密")
    @RequestMapping(value = "/decrypt", method = RequestMethod.GET)
    public CommonRlt<String> decrypt(String decryptStr,String encryptor) {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        //StringEncryptor stringEncryptor = new DefaultLazyEncryptor(new StandardEnvironment());
        textEncryptor.setPassword(encryptor);
        //String result= stringEncryptor.decrypt(decryptStr);
        String result= textEncryptor.decrypt(decryptStr);
        return new CommonRlt<>(BasicRestStatusEnum.OK, result);
    }
}
