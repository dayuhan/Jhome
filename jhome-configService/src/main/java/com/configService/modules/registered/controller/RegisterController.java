package com.configService.modules.registered.controller;

import com.bracket.common.register.ProductCode;
import com.bracket.common.register.Register;
import com.configService.modules.registered.model.bo.SysConfigProperties;
import com.configService.modules.registered.model.query.RegisterQuery;
import com.configService.modules.registered.model.query.SysConfigPropertiesQuery;
import com.configService.modules.registered.service.SysConfigPropertiesService;
import com.bracket.common.Bus.AbstractController.BaseController;
import com.bracket.common.Bus.ResponseJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
 * @description: 注册控制器
 * @author: Daxv
 * @create: 2020-08-08 12:35
 **/
@Api(tags = "z、注册系统")
@RestController
@RequestMapping("/register")
public class RegisterController extends BaseController {
    @Autowired
    public SysConfigPropertiesService sysConfigPropertieService;

    @ApiOperation(value = "获取注册列表参数", notes = "是否是公共组件填写：  0:不是 1:是")
    @RequestMapping(value = "/getRegisterList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String getRegisterList(SysConfigPropertiesQuery sysConfigPropertiesQuery) {
        return sysConfigPropertieService.getList(sysConfigPropertiesQuery).toString();
    }

    @ApiOperation(value = "保存参数,注册验证码")
    @ResponseBody
    @RequestMapping(value = "/saveRegisterInfo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseJson saveRegisterInfo(HttpServletRequest request,
                                         HttpServletResponse response,
                                         @Validated @RequestBody List<SysConfigProperties> sysConfigProperties,
                                         BindingResult result) {
        if (result.hasErrors())
            return new ResponseJson().error(result.getFieldError().getField());
        try {
            sysConfigPropertieService.batchUpdate(sysConfigProperties);
        } catch (Exception ex) {
            return new ResponseJson().error(ex.getMessage());
        }
        return new ResponseJson().success();
    }

    @ApiOperation(value = "获取申请码", notes = "获取申请码")
    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseJson getRegisterList() {
        String applyId = Register.GetCode(ProductCode.PRODUCT_CODE.toString());
        return new ResponseJson().setValue("data", applyId);

    }

    @ApiOperation(value = "注册")
    @ResponseBody
    @RequestMapping(value = " /saveRegister", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseJson saveRegister(@Validated @RequestBody RegisterQuery registerQuery, BindingResult result) throws Throwable {
        return new ResponseJson().setValue("data", sysConfigPropertieService.register(registerQuery));
    }

}
