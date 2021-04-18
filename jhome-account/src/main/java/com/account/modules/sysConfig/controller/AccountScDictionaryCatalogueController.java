package com.account.modules.sysConfig.controller;

import com.account.modules.sysConfig.model.query.AccountScDictionaryCatalogueQuery;
import com.ar.common.util.StringUtil;
import com.bracket.common.Bus.AbstractController.BaseController;
import com.bracket.common.Bus.ResponseJson;
import com.bracket.common.Bus.Status;
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
 * @description: 字典分类表 控制器
 * @author:  1
 * @create: 2020-08-29
 **/
@Api(tags = "SYS、系统设置-字典分类表")
@RestController
@RequestMapping("/sysConfig")
public class AccountScDictionaryCatalogueController  extends BaseController {
    @Autowired
    protected  AccountScDictionaryCatalogueService doAccountScDictionaryCatalogueService;

    @ResponseBody
    @ApiOperation(value = "添加字典分类表[代码生成器生成]", notes = "注意：LuxToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/addAccountScDictionaryCatalogue", produces = "application/json;charset=UTF-8")
    public ResponseJson addAccountScDictionaryCatalogue(
    @Validated @RequestBody  AccountScDictionaryCatalogueQuery  obj,
            BindingResult result) {
            try {
            if (result.hasErrors())
                return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
            return doAccountScDictionaryCatalogueService.addAccountScDictionaryCatalogue(obj);
            } catch (Exception ex) {
                 return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
            }
    }

    @ResponseBody
    @ApiOperation(value = "编辑字典分类表 [代码生成器生成]", notes = "注意：LuxToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/editAccountScDictionaryCatalogue", produces = "application/json;charset=UTF-8")
    public ResponseJson editAccountScDictionaryCatalogue(
            @Validated @RequestBody AccountScDictionaryCatalogueQuery obj,
            BindingResult result) {
            if (result.hasErrors())
                return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
            try {
                return doAccountScDictionaryCatalogueService.editAccountScDictionaryCatalogue(obj);
            } catch (Exception ex) {
                return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
            }
    }

    @ResponseBody
    @ApiOperation(value = "删除字典分类表 [代码生成器生成]", notes = "注意：LuxToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/deleteAccountScDictionaryCatalogue", produces = "application/json;charset=UTF-8")
    public ResponseJson deleteAccountScDictionaryCatalogue(
            @Validated @RequestBody AccountScDictionaryCatalogueQuery obj,
            BindingResult result) {
            if (result.hasErrors())
                return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
            try {
                return doAccountScDictionaryCatalogueService.deleteAccountScDictionaryCatalogue(obj);
            } catch (Exception ex) {
                return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
            }
    }
    @ApiOperation(value = "批量删除字典分类表 [代码生成器生成]", notes = "注意：LuxToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/batchDeleteAccountScDictionaryCatalogue", produces = "application/json;charset=UTF-8")
    public ResponseJson batchDeleteAccountScDictionaryCatalogue(@RequestParam("ids") String ids) {
            try {
                if(StringUtil.isBlank(ids))
                    return new ResponseJson().error("批量删除 Ids不能为空！");
                String[] idsArrays=  ids.split(",");
                return doAccountScDictionaryCatalogueService.batchDeleteAccountScDictionaryCatalogue(idsArrays);
            } catch (Exception ex) {
                return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
            }
    }

    @ResponseBody
    @ApiOperation(value = "获取字典分类表列表[分页][代码生成器生成]", notes = "注意：LuxToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectAccountScDictionaryCataloguePageList", produces = "application/json;charset=UTF-8")
    public ResponseJson selectAccountScDictionaryCataloguePageList(@Validated @RequestBody AccountScDictionaryCatalogueQuery query, BindingResult result) {
        if (result.hasErrors())
            return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
        try {
            IPage<AccountScDictionaryCatalogue> pageInfo = doAccountScDictionaryCatalogueService.selectAccountScDictionaryCataloguePageList(query);
            PageUtils<List<AccountScDictionaryCatalogue>> pageUtils = new PageUtils<List<AccountScDictionaryCatalogue>>(
            pageInfo.getRecords(),
            pageInfo.getTotal(),
            query.getPageSize());
            return new ResponseJson().success().setValue("data", pageUtils);
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "根据Id获取 单个对象 [代码生成器生成]", notes = "注意：LuxToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectAccountScDictionaryCatalogueById", produces = "application/json;charset=UTF-8")
    public ResponseJson selectAccountScDictionaryCatalogueById(String id) {
    try {
            return new ResponseJson().success().setValue("data", doAccountScDictionaryCatalogueService.selectAccountScDictionaryCatalogueById(id));
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "根据Ids获取多个AccountScDictionaryCatalogue对象[代码生成器生成]", notes = "注意：LuxToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectBatchAccountScDictionaryCatalogueByIds", produces = "application/json;charset=UTF-8")
        public ResponseJson selectBatchAccountScDictionaryCatalogueByIds(String ids) {
        try {
            List<String>idList= Arrays.asList(ids.split(","));
            return new ResponseJson().success().setValue("data", doAccountScDictionaryCatalogueService.selectBatchAccountScDictionaryCatalogueByIds(idList));
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "根据条件获取AccountScDictionaryCatalogue对象[代码生成器生成]", notes = "注意：LuxToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectAccountScDictionaryCatalogueOne", produces = "application/json;charset=UTF-8")
    public ResponseJson selectAccountScDictionaryCatalogueOne(@Validated @RequestBody AccountScDictionaryCatalogueQuery query, BindingResult result)
    {
        if (result.hasErrors())
             return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
        try {
            AccountScDictionaryCatalogue targetObj = new AccountScDictionaryCatalogue();
            BeanUtils.copyProperties(query, targetObj);
            QueryWrapper<AccountScDictionaryCatalogue> queryWrapper=new QueryWrapper<>(targetObj);
            return new ResponseJson().success().setValue("data", doAccountScDictionaryCatalogueService.selectAccountScDictionaryCatalogueOne(queryWrapper));
        } catch (Exception ex) {
            return new ResponseJson().setValue("data",null).error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "根据自定义条件获取AccountScDictionaryCatalogue对象实体集合[代码生成器生成]", notes = "注意：LuxToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectAccountScDictionaryCatalogueList", produces = "application/json;charset=UTF-8")
    public ResponseJson selectAccountScDictionaryCatalogueList(@Validated @RequestBody AccountScDictionaryCatalogueQuery query, BindingResult result)
    {
        if (result.hasErrors())
             return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
        try {
            AccountScDictionaryCatalogue targetObj = new AccountScDictionaryCatalogue();
            BeanUtils.copyProperties(query, targetObj);
             QueryWrapper<AccountScDictionaryCatalogue> queryWrapper=new QueryWrapper<>(targetObj);
             return new ResponseJson().success().setValue("data", doAccountScDictionaryCatalogueService.selectAccountScDictionaryCatalogueList(queryWrapper));
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }
  }

