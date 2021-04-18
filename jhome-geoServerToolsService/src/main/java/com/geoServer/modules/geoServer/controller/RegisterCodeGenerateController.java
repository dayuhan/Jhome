package com.geoServer.modules.geoServer.controller;

import com.bracket.common.Bus.AbstractController.BaseController;
import com.bracket.common.Bus.ResponseJson;
import com.geoServer.modules.geoServer.service.GeoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
 * @create: 2020-09-22 11:27
 **/
@Api(tags = "S、生成注册授权码")
@RestController
@RequestMapping("/register")
public class RegisterCodeGenerateController extends BaseController {

    @Autowired
    protected GeoService geoService;

    @ResponseBody
    @ApiOperation(value = "添加[代码生成器生成]")
    @PostMapping(value = "/generateCode", produces = "application/json;charset=UTF-8")
    public ResponseJson getGenerateCode(@Validated @RequestBody String declareCode, BindingResult result) {
        return new ResponseJson().success().setValue("data", geoService.getGeoService(declareCode));

    }

}
