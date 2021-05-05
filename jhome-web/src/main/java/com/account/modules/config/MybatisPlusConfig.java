package com.account.modules.config;/**
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-07-04 14:16
 **/

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

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
 * @description: MybatisPlusConfig
 * @author: Daxv
 * @create: 2020-07-04 14:16
 **/
@Configurable
@MapperScan({"com.account.modules.**.dao"})
public class MybatisPlusConfig {

    /**
     * 分页插件.**.
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        //置请求的页码大于最大页后操作，true调回到首页 ，false 继续请求 默认 false
        paginationInterceptor.setOverflow(false);
        //置请求的页码大于最大页后操作，默认500条，-1不受限制
        paginationInterceptor.setLimit(500);
        paginationInterceptor.setDialectType("mysql");
        //开启count 的 join 优化 ，只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;


    }


}
