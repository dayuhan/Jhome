package com.fileStore.modules.fileManagement.model.bo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.*;
import com.fasterxml.jackson.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
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

@TableName("fs_file_directory_detail")
@ApiModel(value = "FsFileDirectoryDetail对象", description = "")
public class FsFileDirectoryDetail extends Model<FsFileDirectoryDetail> {

    private static final long serialVersionUID=1L;
                private String id;

    @ApiModelProperty(value = "文件目录")
    @TableField("`fileDirectoryId`")
        private String fileDirectoryId;

    @ApiModelProperty(value = "文件类型")
    @TableField("`fileType`")
        private String fileType;

    @ApiModelProperty(value = "文件名称")
    @TableField("`fileName`")
        private String fileName;

    @ApiModelProperty(value = "文件下载次数")
    @TableField("`fileDownloadCount`")
        private Integer fileDownloadCount;

    @ApiModelProperty(value = "文件大小")
    @TableField("`fileSize`")
        private Integer fileSize;

    @ApiModelProperty(value = "文件地址")
    @TableField("`fileUrl`")
        private String fileUrl;

    @ApiModelProperty(value = "文件备注")
    @TableField("`fileRemarks`")
        private String fileRemarks;

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
        return"FsFileDirectoryDetail{"+
                "id="+ id +
                ", fileDirectoryId="+ fileDirectoryId +
                ", fileType="+ fileType +
                ", fileName="+ fileName +
                ", fileDownloadCount="+ fileDownloadCount +
                ", fileSize="+ fileSize +
                ", fileUrl="+ fileUrl +
                ", fileRemarks="+ fileRemarks +
                ", createTime="+ createTime +
                ", createBy="+ createBy +
                ", updateTime="+ updateTime +
                ", updateBy="+ updateBy +
                ", isDelete="+ isDelete +
                ", tenantId="+ tenantId +
        "}";
    }
 }