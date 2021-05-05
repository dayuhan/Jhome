package com.bracket.common.ToolKit;

import com.beust.jcommander.internal.Lists;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

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
 * @description: 对象操作类
 * @author: Daxv
 * @create: 2020-08-22 12:34
 **/
public class BeanUtils {

    /**
     * 将一个集合中对象的值拷贝到另一个对象，属性相同即赋值
     * @param source 源数据，将此对象数据取出
     * @param tClass 目标对象，将取出的数据赋值到该对象中
     * @param <T> 源数据类型
     * @param <E> 目标数据类型
     * @return 被赋值后的目标对象集合
     * @throws Exception 自定义异常，通过反射创建对象或调用方法时抛出的异常
     */
    public static <T, E> List<E> copyProperties(List<T> source, Class<E> tClass) throws Exception {

        // 判断传入源数据是否为空，如果空，则抛自定义异常
        if(null == source) {
            throw new Exception("数据源为空");
        }

        // 创建一个集合，用于存储目标对象，全部数据抓换完成后，将该集合返回
        List<E> targetList = Lists.newArrayList();

        // 循环取到单个源对象
        for(T t : source) {
            // 获取源对象的类的详情信息
            Class<?> sClass = t.getClass();
            // 获取源对象的所有属性
            Field[] sFields = sClass.getDeclaredFields();
            // 获取目标对象的所有属性
            Field[] tFields = tClass.getDeclaredFields();

            E target = null;
            try {
                // 通过类的详情信息，创建目标对象 这一步等同于UserTwo target = new UserTwo()；
                target = tClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                throw new CopyPropertyException("目标对象创建失败");
            }

            // 循环取到源对象的单个属性
            for(Field sField : sFields) {
                // 循环取到目标对象的单个属性
                for(Field tField : tFields) {

                    // 判断源对象的属性名、属性类型是否和目标对象的属性名、属性类型一致
                    if(sField.getName().equals(tField.getName()) && sField.getGenericType().equals(tField.getGenericType())) {

                        try {
                            // 获取源对象的属性名，将属性名首字母大写，拼接如：getUsername、getId的字符串
                            String sName = sField.getName();
                            char[] sChars = sName.toCharArray();
                            sChars[0] -= 32;
                            String sMethodName = "get" + String.valueOf(sChars);
                            // 获得属性的get方法
                            Method sMethod = sClass.getMethod(sMethodName);
                            // 调用get方法
                            Object sFieldValue = sMethod.invoke(t);

                            // 获取目标对象的属性名，将属性名首字母大写，拼接如：setUsername、setId的字符串
                            String tName = tField.getName();
                            char[] tChars = tName.toCharArray();
                            tChars[0] -= 32;
                            String tMethodName = "set" + String.valueOf(tChars);
                            // 获得属性的set方法
                            Method tMethod = tClass.getMethod(tMethodName, tField.getType());
                            // 调用方法，并将源对象get方法返回值作为参数传入
                            tMethod.invoke(target, sFieldValue);

                            break;
                        } catch (Exception e) {
                            e.printStackTrace();
                            throw new CopyPropertyException("转换失败，请检查属性类型是否匹配");
                        }
                    }
                }
            }
            // 将通过反射创建的目标对象放入集合中
            targetList.add(target);
        }
        // 返回集合
        return targetList;
    }

    public static <T, E> E copyProperties(T source, Class<E> tClass) throws Exception {

        // 判断传入源数据是否为空，如果空，则抛自定义异常
        if(null == source) {
            throw new Exception("数据源为空");
        }
        // 创建一个集合，用于存储目标对象，全部数据抓换完成后，将该集合返回
        // 获取源对象的类的详情信息
        Class<?> sClass = source.getClass();
        // 获取源对象的所有属性
        Field[] sFields = sClass.getDeclaredFields();
        // 获取目标对象的所有属性
        Field[] tFields = tClass.getDeclaredFields();

        E target = null;
        try {
            // 通过类的详情信息，创建目标对象 这一步等同于UserTwo target = new UserTwo()；
            target = tClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new CopyPropertyException("目标对象创建失败");
        }

        // 循环取到源对象的单个属性
        for(Field sField : sFields) {
            // 循环取到目标对象的单个属性
            for(Field tField : tFields) {

                // 判断源对象的属性名、属性类型是否和目标对象的属性名、属性类型一致
                if(sField.getName().equals(tField.getName()) && sField.getGenericType().equals(tField.getGenericType())) {

                    try {
                        // 获取源对象的属性名，将属性名首字母大写，拼接如：getUsername、getId的字符串
                        String sName = sField.getName();
                        char[] sChars = sName.toCharArray();
                        sChars[0] -= 32;
                        String sMethodName = "get" + String.valueOf(sChars);
                        // 获得属性的get方法
                        Method sMethod = sClass.getMethod(sMethodName);
                        // 调用get方法
                        Object sFieldValue = sMethod.invoke(source);

                        // 获取目标对象的属性名，将属性名首字母大写，拼接如：setUsername、setId的字符串
                        String tName = tField.getName();
                        char[] tChars = tName.toCharArray();
                        tChars[0] -= 32;
                        String tMethodName = "set" + String.valueOf(tChars);
                        // 获得属性的set方法
                        Method tMethod = tClass.getMethod(tMethodName, tField.getType());
                        // 调用方法，并将源对象get方法返回值作为参数传入
                        tMethod.invoke(target, sFieldValue);

                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new CopyPropertyException("转换失败，请检查属性类型是否匹配");
                    }
                }
            }
        }

        // 返回集合
        return target;
    }
}









