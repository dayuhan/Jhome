package com.geoServer.modules.geoServer.model.vo;

import lombok.Data;

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
 * @description: 高程数据
 * @author: Daxv
 * @create: 2021-01-13 16:23
 **/
@Data
public class ElevationDataVO {
    /**
     * 行号
     */
    public int xline;
    /**
     * 列号
     */
    public int yline;
    /**
     * 经度
     */
    public double longitude;
    /**
     * 纬度
     */
    public double latitude;
    /**
     * 海拔
     */
    public double altitude;
}
