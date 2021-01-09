package com.registerMachine.modules.fileManagement.controller;

import com.bracket.common.Bus.AbstractController.BaseController;
import com.bracket.common.Bus.ResponseJson;
import com.bracket.common.Bus.Status;
import com.bracket.common.FastDFS.*;
import com.registerMachine.modules.fileManagement.service.RegisterMachineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

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
 * @description:
 * @author: Daxv
 * @create: 2020-09-22 11:27
 **/
@Api(tags = "S、生成注册授权码")
@RestController
@RequestMapping("/register")
public class RegisterCodeGenerateController extends BaseController {

    @Autowired
    protected RegisterMachineService registerMachineService;

    @ResponseBody
    @ApiOperation(value = "添加[代码生成器生成]")
    @PostMapping(value = "/generateCode", produces = "application/json;charset=UTF-8")
    public ResponseJson getGenerateCode(@Validated @RequestBody String declareCode, BindingResult result) {
        return new ResponseJson().success().setValue("data", registerMachineService.getGenerateCode(declareCode));

    }

}
