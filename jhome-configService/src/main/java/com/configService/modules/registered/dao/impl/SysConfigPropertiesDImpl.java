package com.configService.modules.registered.dao.impl;

import com.configService.modules.registered.dao.SysConfigPropertiesDao;
import com.configService.modules.registered.model.bo.SysConfigProperties;
import com.configService.modules.registered.model.query.SysConfigPropertiesQuery;
import com.bracket.common.Bus.AbstractDaoImpl.HttpDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
 * @description: SysConfigProperties
 * @author: Daxv
 * @create: 2020-08-04 08:54
 **/
@Repository
public class SysConfigPropertiesDImpl extends HttpDaoImpl implements SysConfigPropertiesDao {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean save(SysConfigProperties sysConfigProperties) {
        try {

            String sql = "INSERT INTO `sys_config_properties`(`id`, `paramName`, `key`, `value`, `application`, `isEnable`, `profile`, `label`) VALUES ( ?, ?, ?,?, ?, ?, ?)";
            return jdbcTemplate.update(sql,
                    UUID.randomUUID().toString(),
                    sysConfigProperties.getParamName(),
                    sysConfigProperties.getKey(),
                    sysConfigProperties.getValue(),
                    sysConfigProperties.getApplication(),
                    sysConfigProperties.getIsEnable(),
                    sysConfigProperties.getProfile(),
                    sysConfigProperties.getLabel()
            ) > 0 ? true : false;
        } catch (Exception ex) {
            logger.info(String.format("新增失败，失败原因：%s", ex.getMessage()) );

            return false;
        }
    }

    @Override
    public boolean update(SysConfigProperties sysConfigProperties) {
        try {

            String sql = "UPDATE `sys_config_properties` SET " +
                    "`paramName` = ?, " +
                    "`key` = ?, " +
                    "`value` = ?, " +
                    "`application` = ?, " +
                    "`isEnable` = ?, " +
                    "`profile` = ?, " +
                    "`label` = ? " +
                    (sysConfigProperties.getIsPublicComponent().equals(1)?"WHERE `paramName` =?":"WHERE `id` =?");

            return jdbcTemplate.update(sql,
                    sysConfigProperties.getParamName(),
                    sysConfigProperties.getKey(),
                    sysConfigProperties.getValue(),
                    sysConfigProperties.getApplication(),
                    sysConfigProperties.getIsEnable(),
                    sysConfigProperties.getProfile(),
                    sysConfigProperties.getLabel(),
                    !sysConfigProperties.getIsPublicComponent().equals(1)?sysConfigProperties.getId():sysConfigProperties.getParamName()) > 0 ? true : false;

        } catch (Exception ex) {
            logger.info(String.format("修改失败，失败原因：%s", ex.getMessage()) );
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            String sql = "delete from sys_config_properties where id=?";
            return jdbcTemplate.update(sql, id) > 0 ? true : false;
        } catch (Exception ex) {
            logger.info("新增失败，失败原因：%s", ex.getMessage());
            return false;
        }
    }

    @Override
    public List<SysConfigProperties> findAll() {
        List<SysConfigProperties> sysConfigProperties = new ArrayList<>();
        try {
            String sql = "select * from sys_config_properties";
            sysConfigProperties = jdbcTemplate.query(sql, new BeanPropertyRowMapper(SysConfigProperties.class));
            return sysConfigProperties;
        } catch (Exception ex) {
            logger.info(String.format("查询失败，失败原因：%s", ex.getMessage()) );
            return null;
        }
    }


    @Override
    public List<SysConfigProperties> getList(SysConfigPropertiesQuery sysConfigPropertiesQuery) {
        StringBuffer sql = new StringBuffer();
        List<SysConfigProperties> sysConfigPropertiesList = new ArrayList<>();
        try {
            sql.append("select * from sys_config_properties where 1=1 ");
            sql.append(" and isPublicComponent=" + String.valueOf(sysConfigPropertiesQuery.getIsPublicComponent()));
            if (sysConfigPropertiesQuery.getIsPublicComponent().equals(0) && sysConfigPropertiesQuery.getApplication() != null) {
                sql.append(" and application='" + sysConfigPropertiesQuery.getApplication() + "'");
            } else {
                sql.append(" GROUP BY paramName ");
            }
            sysConfigPropertiesList = jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(SysConfigProperties.class));
            return sysConfigPropertiesList;
        } catch (Exception ex) {
            logger.info(String.format("查询失败，失败原因：%s", ex.getMessage()) );
            return null;
        }
    }
}