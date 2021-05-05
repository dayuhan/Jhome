package com.nettyService.model.bo;/**
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-09-12 16:28
 **/

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
 * @program: jhome-root
 * @description: 离线消息类
 * @author: Daxv
 * @create: 2020-09-12 16:28
 **/
@Getter
@Setter
@NoArgsConstructor
public class NsMessageInformation {
    private String Id;
    private String fromUserId;
    private String toGroupId;
    private String toUserId;
    private String content;

    private Integer type;
    /**
     * 推送次数
     */
    private Integer sendCount;
    private String fileUrl;
    private Integer fileSize;
    /**
     * 0 未阅读 1 已经阅读
     */
    private Integer status;
    private String createdBy;
    private Date createdTime;
    private String tenantIdId;
    private String schoolYearId;

    @Override
    public String toString() {
        return "NsMessageInformation{" +
                "Id='" + Id + '\'' +
                ", fromUserId='" + fromUserId + '\'' +
                ", toGroupId='" + toGroupId + '\'' +
                ", toUserId='" + toUserId + '\'' +
                ", content='" + content + '\'' +
                ", type=" + type +
                ", sendCount=" + sendCount +
                ", fileUrl='" + fileUrl + '\'' +
                ", fileSize=" + fileSize +
                ", status=" + status +
                ", createdBy='" + createdBy + '\'' +
                ", createdTime=" + createdTime +
                ", tenantIdId='" + tenantIdId + '\'' +
                ", schoolYearId='" + schoolYearId + '\'' +
                '}';
    }
}
