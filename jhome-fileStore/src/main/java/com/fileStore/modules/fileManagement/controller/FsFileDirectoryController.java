package com.fileStore.modules.fileManagement.controller;


import com.bracket.common.Bus.AbstractController.BaseController;
import com.bracket.common.Bus.AbstractModel.PageUtils;
import com.bracket.common.Bus.ResponseJson;
import com.bracket.common.Bus.Status;
import com.bracket.common.ToolKit.ObjectBaseUtils;
import com.bracket.common.ToolKit.StringUtil;
import com.fileStore.modules.fileManagement.model.bo.FsFileDirectory;
import com.fileStore.modules.fileManagement.model.query.FsFileDirectoryQuery;
import com.fileStore.modules.fileManagement.model.vo.FsFileDirectoryView;
import com.fileStore.modules.fileManagement.service.FsFileDirectoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.List;
import java.util.Arrays;
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
 * @create: 2020-09-04
 **/
@Api(tags = "F、上传服务-资源目录")
@RestController
@RequestMapping("/file")
public class FsFileDirectoryController  extends BaseController {
    @Autowired
    protected FsFileDirectoryService doFsFileDirectoryService;

    @ResponseBody
    @ApiOperation(value = "添加[代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/addFsFileDirectory", produces = "application/json;charset=UTF-8")
    public ResponseJson addFsFileDirectory(
    @Validated @RequestBody FsFileDirectoryQuery obj,
            BindingResult result) {
            try {
            if (result.hasErrors())
                return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
            return doFsFileDirectoryService.addFsFileDirectory(obj);
            } catch (Exception ex) {
                 return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
            }
    }

    @ResponseBody
    @ApiOperation(value = "编辑 [代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/editFsFileDirectory", produces = "application/json;charset=UTF-8")
    public ResponseJson editFsFileDirectory(
            @Validated @RequestBody FsFileDirectoryQuery obj,
            BindingResult result) {
            if (result.hasErrors())
                return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
            try {
                return doFsFileDirectoryService.editFsFileDirectory(obj);
            } catch (Exception ex) {
                return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
            }
    }

    @ResponseBody
    @ApiOperation(value = "删除 [代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/deleteFsFileDirectory", produces = "application/json;charset=UTF-8")
    public ResponseJson deleteFsFileDirectory(
            @Validated @RequestBody FsFileDirectoryQuery obj,
            BindingResult result) {
            if (result.hasErrors())
                return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
            try {
                return doFsFileDirectoryService.deleteFsFileDirectory(obj);
            } catch (Exception ex) {
                return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
            }
    }
    @ApiOperation(value = "批量删除 [代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/batchDeleteFsFileDirectory", produces = "application/json;charset=UTF-8")
    public ResponseJson batchDeleteFsFileDirectory(@RequestParam("ids") String ids) {
            try {
                if(StringUtil.isBlank(ids))
                    return new ResponseJson().error("批量删除 Ids不能为空！");
                String[] idsArrays=  ids.split(",");
                return doFsFileDirectoryService.batchDeleteFsFileDirectory(idsArrays);
            } catch (Exception ex) {
                return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
            }
    }

    @ResponseBody
    @ApiOperation(value = "获取列表[分页][代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectFsFileDirectoryPageList", produces = "application/json;charset=UTF-8")
    public ResponseJson selectFsFileDirectoryPageList(@Validated @RequestBody FsFileDirectoryQuery query, BindingResult result) {
        if (result.hasErrors())
            return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
        try {
            IPage<FsFileDirectory> pageInfo = doFsFileDirectoryService.selectFsFileDirectoryPageList(query);
            PageUtils<List<FsFileDirectory>> pageUtils = new PageUtils<List<FsFileDirectory>>(
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
    @PostMapping(value = "/selectFsFileDirectoryById", produces = "application/json;charset=UTF-8")
    public ResponseJson selectFsFileDirectoryById(String id) {
    try {
            return new ResponseJson().success().setValue("data", doFsFileDirectoryService.selectFsFileDirectoryById(id));
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "根据Ids获取多个FsFileDirectory对象[代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectBatchFsFileDirectoryByIds", produces = "application/json;charset=UTF-8")
        public ResponseJson selectBatchFsFileDirectoryByIds(String ids) {
        try {
            List<String>idList= Arrays.asList(ids.split(","));
            return new ResponseJson().success().setValue("data", doFsFileDirectoryService.selectBatchFsFileDirectoryByIds(idList));
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "根据条件获取FsFileDirectory对象[代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectFsFileDirectoryOne", produces = "application/json;charset=UTF-8")
    public ResponseJson selectFsFileDirectoryOne(@Validated @RequestBody FsFileDirectoryQuery query, BindingResult result)
    {
        if (result.hasErrors())
             return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
        try {
        FsFileDirectory targetObj = ObjectBaseUtils.copyProperties(query, FsFileDirectory.class);
            QueryWrapper<FsFileDirectory> queryWrapper=new QueryWrapper<>(targetObj);
            return new ResponseJson().success().setValue("data", doFsFileDirectoryService.selectFsFileDirectoryOne(queryWrapper));
        } catch (Exception ex) {
            return new ResponseJson().setValue("data",null).error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "根据自定义条件获取FsFileDirectory对象实体集合[代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectFsFileDirectoryList", produces = "application/json;charset=UTF-8")
    public ResponseJson selectFsFileDirectoryList(@Validated @RequestBody FsFileDirectoryQuery query, BindingResult result)
    {
        if (result.hasErrors())
             return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
        try {
        FsFileDirectory targetObj = ObjectBaseUtils.copyProperties(query, FsFileDirectory.class);
             QueryWrapper<FsFileDirectory> queryWrapper=new QueryWrapper<>(targetObj);
             return new ResponseJson().success().setValue("data", doFsFileDirectoryService.selectFsFileDirectoryList(queryWrapper));
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "根据自定义条件获取FsFileDirectory Tree 格式对象实体集合", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectFsFileDirectoryListByTree", produces = "application/json;charset=UTF-8")
    public ResponseJson selectFsFileDirectoryListByTree(@Validated @RequestBody FsFileDirectoryQuery query, BindingResult result)
    {
        if (result.hasErrors())
            return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
        try {
            FsFileDirectory targetObj = ObjectBaseUtils.copyProperties(query, FsFileDirectory.class);
            QueryWrapper<FsFileDirectory> queryWrapper=new QueryWrapper<>(targetObj);
            return new ResponseJson().success().setValue("data", doFsFileDirectoryService.selectFsFileDirectoryListByTree(queryWrapper));
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }
  }

