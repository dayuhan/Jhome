package com.nettyService.dao.impl;

import com.nettyService.dao.NsMessageInformationDao;
import com.nettyService.model.bo.NsMessageInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
 * @program: jhome-root
 * @description: 离线消息实现
 * @author: Daxv
 * @create: 2020-09-12 16:27
 **/
@Repository
public class NsMessageInformationDImpl implements NsMessageInformationDao {
    private static final Logger logger = LoggerFactory.getLogger(NsMessageInformation.class);
    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Override
    public boolean save(NsMessageInformation NsMessageInformation) {
        try {
            String sql = "INSERT INTO `as_message_information`(`Id`, `from_user_id`, `to_group_Id`, `to_user_id`, `content`, `type`, `file_url`, `file_Size`, `status`, `created_by`, `created_time`, `tenant_id`, `school_year_id`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            return jdbcTemplate.update(sql, UUID.randomUUID().toString(), NsMessageInformation.getFromUserId(), NsMessageInformation.getToGroupId(), NsMessageInformation.getToUserId(), NsMessageInformation.getContent(), NsMessageInformation.getType(), NsMessageInformation.getFileUrl(), NsMessageInformation.getFileSize(), NsMessageInformation.getStatus(), NsMessageInformation.getCreatedBy(), NsMessageInformation.getCreatedTime(), NsMessageInformation.getTenantIdId(), NsMessageInformation.getSchoolYearId()) > 0;
        } catch (Exception ex) {
            logger.info(String.format("新增失败，失败原因：%s", ex.getMessage()));
            return false;
        }
    }

    @Override
    public boolean update(NsMessageInformation NsMessageInformation) {
        try {
            String sql = "UPDATE `gta_ebd`.`as_message_information` SET " + "`from_user_id` = '?', " + "`to_group_Id` = '?', " + "`to_user_id` = '?', " + "`content` = '?', " + "`type` = 1, " + "`file_url` = '?', " + "`file_Size` = 1, " + "`status` = 1, " + "`created_by` = '?', " + "`created_time` = '?', " + "`tenant_id` = '?', " + "`school_year_id` = '?'" + " WHERE `Id` = '?'; ";
            return jdbcTemplate.update(sql, NsMessageInformation.getFromUserId(), NsMessageInformation.getToGroupId(), NsMessageInformation.getToUserId(), NsMessageInformation.getContent(), NsMessageInformation.getType(), NsMessageInformation.getFileUrl(), NsMessageInformation.getFileSize(), NsMessageInformation.getStatus(), NsMessageInformation.getCreatedBy(), NsMessageInformation.getCreatedTime(), NsMessageInformation.getTenantIdId(), NsMessageInformation.getSchoolYearId(), NsMessageInformation.getId()) > 0;
        } catch (Exception ex) {
            logger.info(String.format("修改失败，失败原因：%s", ex.getMessage()));
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            String sql = "delete from as_message_information where id=?";
            return jdbcTemplate.update(sql, id) > 0;
        } catch (Exception ex) {
            logger.info("新增失败，失败原因：%s", ex.getMessage());
            return false;
        }
    }

    @Override
    public List<NsMessageInformation> findAll() {
        try {
            String sql = "select * from as_message_information";
            List<NsMessageInformation> sysConfigProperties = jdbcTemplate.query(sql, new BeanPropertyRowMapper(NsMessageInformation.class));
            return sysConfigProperties;
        } catch (Exception ex) {
            logger.info(String.format("查询失败，失败原因：%s", ex.getMessage()));
            return null;
        }
    }

    @Override
    public List<NsMessageInformation> getList(NsMessageInformation NsMessageInformation) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("select * from as_message_information where 1=1 and status=0 ");
            stringBuilder.append(String.format(" and send_count<%s", NsMessageInformation.getSendCount()));
            List<NsMessageInformation> sysConfigProperties = jdbcTemplate.query(stringBuilder.toString(), new BeanPropertyRowMapper(NsMessageInformation.class));
            return sysConfigProperties;
        } catch (Exception ex) {
            logger.info(String.format("查询失败，失败原因：%s", ex.getMessage()));
            return null;
        }
    }
}
