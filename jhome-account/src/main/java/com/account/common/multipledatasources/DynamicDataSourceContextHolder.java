package com.account.common.multipledatasources;

/**
 * @program: jhome-root
 * @description
 * @author: Daxv
 * @create: 2020-06-13 18:44
 **/
public class DynamicDataSourceContextHolder {
    private static ThreadLocal<DataEnum> threadLocal = new ThreadLocal<>();

    public static void setDataSourceType(DataEnum type) {
        threadLocal.set(type);
        System.out.println("数据库已经切换" + type);
    }

    public static DataEnum getDataSourceType() {
        return threadLocal.get();
    }

    public static void clearDatasourceType() {

        threadLocal.remove();
    }
}
