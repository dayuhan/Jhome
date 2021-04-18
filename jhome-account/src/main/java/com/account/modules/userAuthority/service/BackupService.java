package com.account.modules.userAuthority.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import javax.servlet.http.HttpServletResponse;

public interface BackupService {


    /**
     * 分页查询数据库备份记录
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page backupData(Integer pageNum, Integer pageSize);

    /**
     * 备份数据
     * @param response
     */
    void backupNew(HttpServletResponse response);

    /**
     * 删除备份记录及备份文件
     * @param id
     */
    void backupDel(Integer id);
}
