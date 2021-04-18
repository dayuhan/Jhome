package com.account.modules.sysConfig.service.impl;

import com.account.common.utils.UserAuxiliary;
import com.account.modules.sysConfig.model.bo.CsRegisteredConfig;
import com.account.modules.sysConfig.dao.CsRegisteredConfigMapper;
import com.account.modules.sysConfig.model.query.CsRegisteredConfigQuery;
import com.account.modules.sysConfig.model.vo.CsRegisteredConfigModelView;
import com.account.modules.sysConfig.service.CsRegisteredConfigService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bracket.common.Bus.ResponseJson;
import com.bracket.common.Bus.Status;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
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
 * @description:  服务实现类
 * @author:  1
 * @create: 2020-08-24
 **/
@Service
public class CsRegisteredConfigServiceImpl extends ServiceImpl<CsRegisteredConfigMapper, CsRegisteredConfig>implements CsRegisteredConfigService {

    @Autowired
    protected CsRegisteredConfigMapper mapper;
    @Autowired
    protected UserAuxiliary userAuxiliary;

    @Override
    public ResponseJson addCsRegisteredConfig(CsRegisteredConfigQuery obj) {
        String massAge = "";
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式

            CsRegisteredConfig targetObj= new CsRegisteredConfig();
            BeanUtils.copyProperties(obj,targetObj);
            targetObj.setId(UUID.randomUUID().toString());
            targetObj.setUpdateBy(userAuxiliary.getLoginRes().getUserId().toString());
         /*   targetObj.setCreateTime(df.format(new Date()));*/
            int count = mapper.insert(targetObj);
            if (count > 0)
                return new ResponseJson().success();
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,"新增失败~");
        } catch (Exception ex) {
            massAge = ex.getMessage();
        }
        return new ResponseJson().error(massAge);
    }

    @Override
    public ResponseJson editCsRegisteredConfig( CsRegisteredConfigQuery obj) {
        String massAge = "";
        try {
            CsRegisteredConfig targetObj= new CsRegisteredConfig();
            BeanUtils.copyProperties(obj,targetObj);
            int count = mapper.updateById(targetObj);
            if (count > 0)
                return new ResponseJson().success();
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,"编辑失败~");
        } catch (Exception ex) {
            massAge = ex.getMessage();
        }
        return new ResponseJson().error(massAge);
    }

    @Override
    public ResponseJson batchDeleteCsRegisteredConfig(String[] ids) {
        String massAge = "";
        try {

            int count = mapper.deleteBatchIds(Arrays.asList(ids));
            if (count > 0)
                return new ResponseJson().success();
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,"删除失败~");
        } catch (Exception ex) {
            massAge = ex.getMessage();
        }
        return new ResponseJson().error(massAge);
    }

    @Override
    public ResponseJson deleteCsRegisteredConfig( CsRegisteredConfigQuery obj) {
        String massAge = "";
        try {
            CsRegisteredConfig targetObj= new CsRegisteredConfig();
            BeanUtils.copyProperties(obj,targetObj);
            int count = mapper.deleteById(targetObj);
            if (count > 0)
                return new ResponseJson().success();
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR,"删除失败~");
        } catch (Exception ex) {
            massAge = ex.getMessage();
        }
        return new ResponseJson().error(massAge);
    }

    @Override
    public  IPage<CsRegisteredConfigModelView> queryCsRegisteredConfigList(CsRegisteredConfigQuery queryObject){
        IPage<CsRegisteredConfigModelView> page = new Page<>(queryObject.getPageNum(), queryObject.getPageSize());
        IPage<CsRegisteredConfigModelView> result = mapper.queryCsRegisteredConfigList(page, queryObject);
        return result;
    }


    /**
     * 根据Id获取 单个对象
     *
     * @param id
     * @return
     */
    @Override
    public CsRegisteredConfig selectCsRegisteredConfigById(String id) {
        return mapper.selectById(id);
    }

    /**
     * 根据Ids获取多个对象
     *
     * @param
     */
    @Override
    public List<CsRegisteredConfig> selectBatchCsRegisteredConfigByIds(List<String> idList) {
        return mapper.selectBatchIds(idList);
    }

    /**
     * 获取一个目标对象
     *
     * @param queryObject
     * @return
     */
    @Override
    public CsRegisteredConfig selectCsRegisteredConfigOne(QueryWrapper<CsRegisteredConfig> queryObject) {
        return mapper.selectOne(queryObject);
    }

    /**
     * 根据条件 获取多个目标对象
     *
     * @param queryObject
     * @return
     */
    @Override
    public List<CsRegisteredConfig> selectCsRegisteredConfigList(QueryWrapper<CsRegisteredConfig> queryObject) {
        return mapper.selectList(queryObject);
    }
}
