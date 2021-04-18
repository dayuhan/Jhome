package com.domain.common;/**
 * @program: account-root
 * @description
 * @author: Daxv
 * @create: 2020-07-04 15:13
 **/

import java.io.Serializable;

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
 * @program: account-root
 * @description:
 * @author: Daxv
 * @create: 2020-07-04 15:13
 **/
public abstract class DefaultCertification implements Serializable {
    private String jhomeToken;

    @Override
    public String toString() {
        return "DefaultCertification{" +
                "token='" + jhomeToken + '\'' +
                '}';
    }

    public String getJhomeToken() {
        return jhomeToken;
    }

    public void setJhomeToken(String jhomeToken) {
        this.jhomeToken = jhomeToken;
    }
}
