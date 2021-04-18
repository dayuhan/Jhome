package com.account.modules.sysConfig.service.impl;

import com.account.modules.sysConfig.model.bo.AccountScDictionaryCatalogue;
import com.account.modules.sysConfig.model.bo.AccountScDictionaryDetail;
import com.account.modules.sysConfig.dao.AccountScDictionaryDetailMapper;
import com.account.modules.sysConfig.model.query.AccountScDictionaryDetailQuery;
import com.account.modules.sysConfig.service.AccountScDictionaryDetailService;
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
 * @description: 字典明细表 服务实现类
 * @author: 1
 * @create: 2020-08-29
 **/
@Service
public class AccountScDictionaryDetailServiceImpl extends ServiceImpl<AccountScDictionaryDetailMapper, AccountScDictionaryDetail> implements AccountScDictionaryDetailService {

    @Autowired
    protected AccountScDictionaryDetailMapper mapper;

    @Override
    public ResponseJson addAccountScDictionaryDetail(AccountScDictionaryDetailQuery obj) {
        String massAge = "";
        try {
            AccountScDictionaryDetail targetObj = new AccountScDictionaryDetail();
            BeanUtils.copyProperties(obj, targetObj);
            targetObj.setId(UUID.randomUUID().toString());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            targetObj.setUpdatedTime(new Date());
            targetObj.setCreatedTime(new Date());
            int count = mapper.insert(targetObj);
            if (count > 0)
                return new ResponseJson().success();
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR, "新增失败~");
        } catch (Exception ex) {
            massAge = ex.getMessage();
        }
        return new ResponseJson().error(massAge);
    }

    @Override
    public ResponseJson editAccountScDictionaryDetail(AccountScDictionaryDetailQuery obj) {
        String massAge = "";
        try {
            AccountScDictionaryDetail targetObj = new AccountScDictionaryDetail();
            BeanUtils.copyProperties(obj, targetObj);
            int count = mapper.updateById(targetObj);
            if (count > 0)
                return new ResponseJson().success();
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR, "编辑失败~");
        } catch (Exception ex) {
            massAge = ex.getMessage();
        }
        return new ResponseJson().error(massAge);
    }

    @Override
    public ResponseJson batchDeleteAccountScDictionaryDetail(String[] ids) {
        String massAge = "";
        try {

            int count = mapper.deleteBatchIds(Arrays.asList(ids));
            if (count > 0)
                return new ResponseJson().success();
            return new ResponseJson().error("删除失败~");
        } catch (Exception ex) {
            massAge = ex.getMessage();
        }
        return new ResponseJson().error(massAge);
    }

    @Override
    public ResponseJson deleteAccountScDictionaryDetail(AccountScDictionaryDetailQuery obj) {
        String massAge = "";
        try {
            AccountScDictionaryDetail targetObj = new AccountScDictionaryDetail();
            BeanUtils.copyProperties(obj, targetObj);
            int count = mapper.deleteById(targetObj);
            if (count > 0)
                return new ResponseJson().success();
            return new ResponseJson().error(Status.SC_INTERNAL_SERVER_ERROR, "删除失败~");
        } catch (Exception ex) {
            massAge = ex.getMessage();
        }
        return new ResponseJson().error(massAge);
    }

    @Override
    public IPage<AccountScDictionaryDetail> selectAccountScDictionaryDetailPageList(AccountScDictionaryDetailQuery queryObject) {
        IPage<AccountScDictionaryDetail> page = new Page<>(queryObject.getPageNum(), queryObject.getPageSize());
        IPage<AccountScDictionaryDetail> result = mapper.selectAccountScDictionaryDetailPageList(page, queryObject);
        return result;
    }


    /**
     * 根据Id获取 单个对象
     *
     * @param id
     * @return
     */
    @Override
    public AccountScDictionaryDetail selectAccountScDictionaryDetailById(String id) {
        return mapper.selectById(id);
    }

    /**
     * 根据Ids获取多个对象
     *
     * @param
     */
    @Override
    public List<AccountScDictionaryDetail> selectBatchAccountScDictionaryDetailByIds(List<String> idList) {
        return mapper.selectBatchIds(idList);
    }

    /**
     * 获取一个目标对象
     *
     * @param queryObject
     * @return
     */
    @Override
    public AccountScDictionaryDetail selectAccountScDictionaryDetailOne(QueryWrapper<AccountScDictionaryDetail> queryObject) {
        return mapper.selectOne(queryObject);
    }

    /**
     * 根据条件 获取多个目标对象
     *
     * @param queryObject
     * @return
     */
    @Override
    public List<AccountScDictionaryDetail> selectAccountScDictionaryDetailList(QueryWrapper<AccountScDictionaryDetail> queryObject) {
        return mapper.selectList(queryObject);
    }
}
