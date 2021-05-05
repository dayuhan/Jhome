package com.fileStore.modules.fileManagement.controller;
 
import com.bracket.common.Bus.AbstractController.BaseController;
import com.bracket.common.Bus.AbstractModel.PageUtils;
import com.bracket.common.Bus.ResponseJson;
import com.bracket.common.Bus.Status;
import com.bracket.common.ToolKit.ObjectBaseUtils;
import com.bracket.common.ToolKit.StringUtil;
import com.fileStore.modules.fileManagement.model.bo.FsFileDirectoryDetail;
import com.fileStore.modules.fileManagement.model.query.FsFileDirectoryDetailQuery;
import com.fileStore.modules.fileManagement.model.vo.FsFileDirectoryDetailView;
import com.fileStore.modules.fileManagement.service.FsFileDirectoryDetailService;
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
@Api(tags = "F、上传服务-资源明细")
@RestController
@RequestMapping("/file")
public class FsFileDirectoryDetailController  extends BaseController {
    @Autowired
    protected FsFileDirectoryDetailService doFsFileDirectoryDetailService;

    @ResponseBody
    @ApiOperation(value = "添加[代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/addFsFileDirectoryDetail", produces = "application/json;charset=UTF-8")
    public ResponseJson addFsFileDirectoryDetail(
    @Validated @RequestBody FsFileDirectoryDetailQuery obj,
            BindingResult result) {
            try {
            if (result.hasErrors())
                return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
            return doFsFileDirectoryDetailService.addFsFileDirectoryDetail(obj);
            } catch (Exception ex) {
                 return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
            }
    }

    @ResponseBody
    @ApiOperation(value = "编辑 [代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/editFsFileDirectoryDetail", produces = "application/json;charset=UTF-8")
    public ResponseJson editFsFileDirectoryDetail(
            @Validated @RequestBody FsFileDirectoryDetailQuery obj,
            BindingResult result) {
            if (result.hasErrors())
                return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
            try {
                return doFsFileDirectoryDetailService.editFsFileDirectoryDetail(obj);
            } catch (Exception ex) {
                return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
            }
    }

    @ResponseBody
    @ApiOperation(value = "删除 [代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/deleteFsFileDirectoryDetail", produces = "application/json;charset=UTF-8")
    public ResponseJson deleteFsFileDirectoryDetail(
            @Validated @RequestBody FsFileDirectoryDetailQuery obj,
            BindingResult result) {
            if (result.hasErrors())
                return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
            try {
                return doFsFileDirectoryDetailService.deleteFsFileDirectoryDetail(obj);
            } catch (Exception ex) {
                return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
            }
    }
    @ApiOperation(value = "批量删除 [代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/batchDeleteFsFileDirectoryDetail", produces = "application/json;charset=UTF-8")
    public ResponseJson batchDeleteFsFileDirectoryDetail(@RequestParam("ids") String ids) {
            try {
                if(StringUtil.isBlank(ids))
                    return new ResponseJson().error("批量删除 Ids不能为空！");
                String[] idsArrays=  ids.split(",");
                return doFsFileDirectoryDetailService.batchDeleteFsFileDirectoryDetail(idsArrays);
            } catch (Exception ex) {
                return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
            }
    }

    @ResponseBody
    @ApiOperation(value = "获取列表[分页][代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectFsFileDirectoryDetailPageList", produces = "application/json;charset=UTF-8")
    public ResponseJson selectFsFileDirectoryDetailPageList(@Validated @RequestBody FsFileDirectoryDetailQuery query, BindingResult result) {
        if (result.hasErrors())
            return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
        try {
            IPage<FsFileDirectoryDetailView> pageInfo = doFsFileDirectoryDetailService.selectFsFileDirectoryDetailPageList(query);
            PageUtils<List<FsFileDirectoryDetailView>> pageUtils = new PageUtils<List<FsFileDirectoryDetailView>>(
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
    @PostMapping(value = "/selectFsFileDirectoryDetailById", produces = "application/json;charset=UTF-8")
    public ResponseJson selectFsFileDirectoryDetailById(String id) {
    try {
            return new ResponseJson().success().setValue("data", doFsFileDirectoryDetailService.selectFsFileDirectoryDetailById(id));
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "根据Ids获取多个FsFileDirectoryDetail对象[代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectBatchFsFileDirectoryDetailByIds", produces = "application/json;charset=UTF-8")
        public ResponseJson selectBatchFsFileDirectoryDetailByIds(String ids) {
        try {
            List<String>idList= Arrays.asList(ids.split(","));
            return new ResponseJson().success().setValue("data", doFsFileDirectoryDetailService.selectBatchFsFileDirectoryDetailByIds(idList));
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "根据条件获取FsFileDirectoryDetail对象[代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectFsFileDirectoryDetailOne", produces = "application/json;charset=UTF-8")
    public ResponseJson selectFsFileDirectoryDetailOne(@Validated @RequestBody FsFileDirectoryDetailQuery query, BindingResult result)
    {
        if (result.hasErrors())
             return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
        try {
        FsFileDirectoryDetail targetObj = ObjectBaseUtils.copyProperties(query, FsFileDirectoryDetail.class);
            QueryWrapper<FsFileDirectoryDetail> queryWrapper=new QueryWrapper<>(targetObj);
            return new ResponseJson().success().setValue("data", doFsFileDirectoryDetailService.selectFsFileDirectoryDetailOne(queryWrapper));
        } catch (Exception ex) {
            return new ResponseJson().setValue("data",null).error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }

    @ResponseBody
    @ApiOperation(value = "根据自定义条件获取FsFileDirectoryDetail对象实体集合[代码生成器生成]", notes = "注意：jhomeToken默认由前端放到header中,调试模式不提供改参数，从cookies中获取")
    @PostMapping(value = "/selectFsFileDirectoryDetailList", produces = "application/json;charset=UTF-8")
    public ResponseJson selectFsFileDirectoryDetailList(@Validated @RequestBody FsFileDirectoryDetailQuery query, BindingResult result)
    {
        if (result.hasErrors())
             return new ResponseJson().error(result.getFieldError().getDefaultMessage() + " 错误字段：" + result.getFieldError().getField());
        try {
        FsFileDirectoryDetail targetObj = ObjectBaseUtils.copyProperties(query, FsFileDirectoryDetail.class);
             QueryWrapper<FsFileDirectoryDetail> queryWrapper=new QueryWrapper<>(targetObj);
             return new ResponseJson().success().setValue("data", doFsFileDirectoryDetailService.selectFsFileDirectoryDetailList(queryWrapper));
        } catch (Exception ex) {
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }
  }

