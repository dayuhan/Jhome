package com.account.modules.sysConfig.controller;


import com.account.modules.sysConfig.model.query.AccountScDictionaryDetailQuery;
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
 * @description: 字典明细表 控制器
 * @author: 1
 * @create: 2020-08-29
 **/
@Api(tags = "SYS、系统设置-字典明细表")
@RestController
@RequestMapping("/sysConfig")
public class AccountScDictionaryDetailController extends BaseController {
    @Autowired
    protected AccountScDictionaryDetailService doAccountScDictionaryDetailService;

    @ResponseBody
    @ApiOperation(value = "添加字典明细表[代码生成器生成]", notes = "注意：LuxToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/addAccountScDictionaryDetail", produces = "application/json;charset=UTF-8")
    public ResponseJson addAccountScDictionaryDetail(
            @Validated @RequestBody AccountScDictionaryDetailQuery obj,
            BindingResult result) {
        try {
            if (result.hasErrors())
                return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
            return doAccountScDictionaryDetailService.addAccountScDictionaryDetail(obj);
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "编辑字典明细表 [代码生成器生成]", notes = "注意：LuxToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/editAccountScDictionaryDetail", produces = "application/json;charset=UTF-8")
    public ResponseJson editAccountScDictionaryDetail(
            @Validated @RequestBody AccountScDictionaryDetailQuery obj,
            BindingResult result) {
        if (result.hasErrors())
            return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
        try {
            return doAccountScDictionaryDetailService.editAccountScDictionaryDetail(obj);
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "删除字典明细表 [代码生成器生成]", notes = "注意：LuxToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/deleteAccountScDictionaryDetail", produces = "application/json;charset=UTF-8")
    public ResponseJson deleteAccountScDictionaryDetail(
            @Validated @RequestBody AccountScDictionaryDetailQuery obj,
            BindingResult result) {
        if (result.hasErrors())
            return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
        try {
            return doAccountScDictionaryDetailService.deleteAccountScDictionaryDetail(obj);
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @ApiOperation(value = "批量删除字典明细表 [代码生成器生成]", notes = "注意：LuxToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/batchDeleteAccountScDictionaryDetail", produces = "application/json;charset=UTF-8")
    public ResponseJson batchDeleteAccountScDictionaryDetail(@RequestParam("ids") String ids) {
        try {
            if (StringUtil.isBlank(ids))
                return new ResponseJson().error("批量删除 Ids不能为空！");
            String[] idsArrays = ids.split(",");
            return doAccountScDictionaryDetailService.batchDeleteAccountScDictionaryDetail(idsArrays);
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "获取字典明细表列表[分页][代码生成器生成]", notes = "注意：LuxToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectAccountScDictionaryDetailPageList", produces = "application/json;charset=UTF-8")
    public ResponseJson selectAccountScDictionaryDetailPageList(@Validated @RequestBody AccountScDictionaryDetailQuery query, BindingResult result) {
        if (result.hasErrors())
            return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
        try {
            IPage<AccountScDictionaryDetail> pageInfo = doAccountScDictionaryDetailService.selectAccountScDictionaryDetailPageList(query);
            PageUtils<List<AccountScDictionaryDetail>> pageUtils = new PageUtils<List<AccountScDictionaryDetail>>(
                    pageInfo.getRecords(),
                    pageInfo.getTotal(),
                    query.getPageSize());
            return new ResponseJson().success().setValue("data", pageUtils);
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "根据Id获取 单个对象 [代码生成器生成]", notes = "注意：LuxToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectAccountScDictionaryDetailById", produces = "application/json;charset=UTF-8")
    public ResponseJson selectAccountScDictionaryDetailById(String id) {
        try {
            return new ResponseJson().success().setValue("data", doAccountScDictionaryDetailService.selectAccountScDictionaryDetailById(id));
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "根据Ids获取多个AccountScDictionaryDetail对象[代码生成器生成]", notes = "注意：LuxToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectBatchAccountScDictionaryDetailByIds", produces = "application/json;charset=UTF-8")
    public ResponseJson selectBatchAccountScDictionaryDetailByIds(String ids) {
        try {
            List<String> idList = Arrays.asList(ids.split(","));
            return new ResponseJson().success().setValue("data", doAccountScDictionaryDetailService.selectBatchAccountScDictionaryDetailByIds(idList));
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "根据条件获取AccountScDictionaryDetail对象[代码生成器生成]", notes = "注意：LuxToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectAccountScDictionaryDetailOne", produces = "application/json;charset=UTF-8")
    public ResponseJson selectAccountScDictionaryDetailOne(@Validated @RequestBody AccountScDictionaryDetailQuery query, BindingResult result) {
        if (result.hasErrors())
            return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
        try {
            AccountScDictionaryDetail targetObj = new AccountScDictionaryDetail();
            BeanUtils.copyProperties(query, targetObj);
            QueryWrapper<AccountScDictionaryDetail> queryWrapper = new QueryWrapper<>(targetObj);
            return new ResponseJson().success().setValue("data", doAccountScDictionaryDetailService.selectAccountScDictionaryDetailOne(queryWrapper));
        } catch (Exception ex) {
            return new ResponseJson().setValue("data", null).error(Status.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "根据自定义条件获取AccountScDictionaryDetail对象实体集合[代码生成器生成]", notes = "注意：LuxToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectAccountScDictionaryDetailList", produces = "application/json;charset=UTF-8")
    public ResponseJson selectAccountScDictionaryDetailList(@Validated @RequestBody AccountScDictionaryDetailQuery query, BindingResult result) {
        if (result.hasErrors())
            return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
        try {
            AccountScDictionaryDetail targetObj = new AccountScDictionaryDetail();
            BeanUtils.copyProperties(query, targetObj);
            QueryWrapper<AccountScDictionaryDetail> queryWrapper = new QueryWrapper<>(targetObj);
            return new ResponseJson().success().setValue("data", doAccountScDictionaryDetailService.selectAccountScDictionaryDetailList(queryWrapper));
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }
}

