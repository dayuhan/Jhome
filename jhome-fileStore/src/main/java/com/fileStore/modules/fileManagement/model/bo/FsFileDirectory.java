package com.fileStore.modules.fileManagement.model.bo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


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
 * @description: 对象
 * @author:  1
 * @create: 2020-09-04
 **/
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@EqualsAndHashCode(callSuper = true)

@TableName("fs_file_directory")
@ApiModel(value = "FsFileDirectory对象", description = "")
public class FsFileDirectory extends Model<FsFileDirectory> {

    private static final long serialVersionUID=1L;
                private String id;

    @ApiModelProperty(value = "文件目录")
    @TableField("`fileDirectoryName`")
        private String fileDirectoryName;

    @ApiModelProperty(value = "父目录id")
    @TableField("`parentDirectoryId`")
        private String parentDirectoryId;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("`createTime`")
        private Date createTime;

    @TableField("`createBy`")
        private String createBy;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("`updateTime`")
        private Date updateTime;

    @TableField("`updateBy`")
        private String updateBy;

    @ApiModelProperty(value = "是否删除：0否 1是")
    @TableField("`isDelete`")
        private Boolean isDelete;

    @ApiModelProperty(value = "租户ID")
    @TableField("`tenantId`")
        private String tenantId;


    @Override
    public String toString(){
        return"FsFileDirectory{"+
                "id="+ id +
                ", fileDirectoryName="+ fileDirectoryName +
                ", parentDirectoryId="+ parentDirectoryId +
                ", createTime="+ createTime +
                ", createBy="+ createBy +
                ", updateTime="+ updateTime +
                ", updateBy="+ updateBy +
                ", isDelete="+ isDelete +
                ", tenantId="+ tenantId +
        "}";
    }
 }