package com.account.modules.sysConfig.controller;


import com.account.modules.sysConfig.model.query.CsRegisteredConfigQuery;
import com.account.modules.sysConfig.model.vo.CsRegisteredConfigModelView;
import com.bracket.common.Bus.AbstractController.BaseController;
import com.bracket.common.Bus.ResponseJson;
import com.bracket.common.Bus.Status;
import com.bracket.common.ToolKit.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.*;
import com.account.common.sbUtil.PageUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.List;
import java.util.Arrays;
import com.account.modules.sysConfig.service.*;
import com.account.modules.sysConfig.model.bo.*;
import org.springframework.web.bind.annotation.RestController;


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
 * @description:  控制器
 * @author:  1
 * @create: 2020-08-29
 **/
@Api(tags = "SYS、系统设置-参数配置")
@RestController
@RequestMapping("/sysConfig")
public class CsRegisteredConfigController  extends BaseController {
    @Autowired
    protected  CsRegisteredConfigService doCsRegisteredConfigService;

    @ResponseBody
    @ApiOperation(value = "添加[代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/addCsRegisteredConfig", produces = "application/json;charset=UTF-8")
    public ResponseJson addCsRegisteredConfig(
            @Validated @RequestBody  CsRegisteredConfigQuery  obj,
            BindingResult result) {
        try {
            if (result.hasErrors())
                return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
            return doCsRegisteredConfigService.addCsRegisteredConfig(obj);
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "编辑 [代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/editCsRegisteredConfig", produces = "application/json;charset=UTF-8")
    public ResponseJson editCsRegisteredConfig(
            @Validated @RequestBody CsRegisteredConfigQuery obj,
            BindingResult result) {
        if (result.hasErrors())
            return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
        try {
            return doCsRegisteredConfigService.editCsRegisteredConfig(obj);
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "删除 [代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/deleteCsRegisteredConfig", produces = "application/json;charset=UTF-8")
    public ResponseJson deleteCsRegisteredConfig(
            @Validated @RequestBody CsRegisteredConfigQuery obj,
            BindingResult result) {
        if (result.hasErrors())
            return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
        try {
            return doCsRegisteredConfigService.deleteCsRegisteredConfig(obj);
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }
    @ApiOperation(value = "批量删除 [代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/batchDeleteCsRegisteredConfig", produces = "application/json;charset=UTF-8")
    public ResponseJson batchDeleteCsRegisteredConfig(@RequestParam("ids") String ids) {
        try {
            if(StringUtil.isBlank(ids))
                return new ResponseJson().error("批量删除 Ids不能为空！");
            String[] idsArrays=  ids.split(",");
            return doCsRegisteredConfigService.batchDeleteCsRegisteredConfig(idsArrays);
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "获取列表[分页][代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectCsRegisteredConfigPageList", produces = "application/json;charset=UTF-8")
    public ResponseJson selectCsRegisteredConfigList(@Validated @RequestBody CsRegisteredConfigQuery query, BindingResult result) {
        if (result.hasErrors())
            return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
        try {
            IPage<CsRegisteredConfigModelView> pageInfo = doCsRegisteredConfigService.queryCsRegisteredConfigList(query);
            PageUtils<List<CsRegisteredConfigModelView>> pageUtils = new PageUtils<List<CsRegisteredConfigModelView>>(
                    pageInfo.getRecords(),
                    pageInfo.getTotal(),
                    query.getPageSize());
            return new ResponseJson().success().setValue("data", pageUtils);
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "根据Id获取 单个对象 [代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectCsRegisteredConfigById", produces = "application/json;charset=UTF-8")
    public ResponseJson selectCsRegisteredConfigById(String id) {
        try {
            return new ResponseJson().success().setValue("data", doCsRegisteredConfigService.selectCsRegisteredConfigById(id));
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "根据Ids获取多个CsRegisteredConfig对象[代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectBatchCsRegisteredConfigByIds", produces = "application/json;charset=UTF-8")
    public ResponseJson selectBatchCsRegisteredConfigByIds(String ids) {
        try {
            List<String>idList= Arrays.asList(ids.split(","));
            return new ResponseJson().success().setValue("data", doCsRegisteredConfigService.selectBatchCsRegisteredConfigByIds(idList));
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "根据条件获取CsRegisteredConfig对象[代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectCsRegisteredConfigOne", produces = "application/json;charset=UTF-8")
    public ResponseJson selectCsRegisteredConfigOne(@Validated @RequestBody CsRegisteredConfigQuery query, BindingResult result)
    {
        if (result.hasErrors())
            return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
        try {
            CsRegisteredConfig targetObj = new CsRegisteredConfig();
            BeanUtils.copyProperties(query,targetObj);
            QueryWrapper<CsRegisteredConfig> queryWrapper=new QueryWrapper<>(targetObj);
            return new ResponseJson().success().setValue("data", doCsRegisteredConfigService.selectCsRegisteredConfigOne(queryWrapper));
        } catch (Exception ex) {
            return new ResponseJson().setValue("data",null).error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "根据自定义条件获取CsRegisteredConfig对象实体集合[代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectCsRegisteredConfigList", produces = "application/json;charset=UTF-8")
    public ResponseJson selectCsRegisteredConfigList(@Validated @RequestBody CsRegisteredConfig query, BindingResult result)
    {
        if (result.hasErrors())
            return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
        try {
            CsRegisteredConfig targetObj = new CsRegisteredConfig();
            BeanUtils.copyProperties(query,targetObj);
            QueryWrapper<CsRegisteredConfig> queryWrapper=new QueryWrapper<>(targetObj);
            return new ResponseJson().success().setValue("data", doCsRegisteredConfigService.selectCsRegisteredConfigList(queryWrapper));
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }
}

