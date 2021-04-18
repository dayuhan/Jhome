package com.account.common.multipledatasources;

/**
 * @program: account-root
 * @description
 * @author: Daxv
 * @create: 2020-06-13 18:42
 **/
public @interface DataSourceType {
    DataEnum value() default DataEnum.LOCAL;
}
