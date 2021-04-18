package com.codeGererator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.other.common.lang.StringUtils;

import java.io.File;
import java.util.*;

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
 * @program: account-root
 * @description: 代码生成器
 * @author: Daxv
 * @create: 2020-08-14 16:02
 **/
// 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
public class MyBatisPlusCodeGenerator {


    private static String packageName = "src/main"; //初始文件路径
    private static String customPath = "com.account.modules"; //自定义路径
    private static String authorName = "daxv"; //作者
    private static String path = System.getProperty("user.dir") + "/account-account/src/main/java";
    //private static String path = file.getAbsolutePath();
    //private static String table = "sys_account"; //table名字
    //private static String prefix = "sys_"; //table前缀
    private static File file = new File(packageName);

    public static void main(String[] args) {
        System.out.println("绝对路径" + file.getAbsolutePath());
        // 自定义需要填充的字段
        List<TableFill> tableFillList = new ArrayList<>();
        tableFillList.add(new TableFill("ASDD_SS", FieldFill.INSERT_UPDATE));

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig()
                .setOutputDir(path)//输出目录
                .setFileOverride(false)// 是否覆盖文件
                .setActiveRecord(true)// 开启 activeRecord 模式
                .setEnableCache(false)// XML 二级缓存
                .setBaseResultMap(true)// XML ResultMap
                .setBaseColumnList(true)// XML columList
                .setOpen(false)//生成后打开文件夹
                //.setAuthor(authorName)
                .setAuthor(scanner("请输生成模式 1：普通对象 2 查询对象 "))
                // 自定义文件命名，注意 %s 会自动填充表实体属性！
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setSwagger2(true) //实体属性 Swagger2 注解
                .setDateType(DateType.ONLY_DATE)
                .setControllerName("%sController");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig()
                .setDbType(DbType.MYSQL)// 数据库类型
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername("root")
                .setPassword("123456")
                .setUrl("jdbc:mysql://192.168.52.130:3306/mysqlDb?serverTimezone=UTC&useSSL=false");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig()
                .setParent(customPath)// 自定义包路径;
                .setModuleName(scanner("请输入模块名"));
        if (gc.getAuthor().equals("1")) {
            pc.setController("controller");// 这里是控制器包名，默认 controller
            pc.setMapper("dao");
            pc.setService("service");
            pc.setServiceImpl("service.impl");
            pc.setEntity("model.bo");
        } else {
            pc.setEntity("model.query");
            gc.setEntityName("%sQuery");
        }
        //.setXml("mapper")
        mpg.setPackageInfo(pc);

        // 自定义配置
        // 如果模板引擎是 freemarker
//        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        String templatePath = "/template/mapper.xml.vm";

        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        }.setFileOutConfigList(Collections.<FileOutConfig>singletonList(new FileOutConfig(templatePath) {
            // 自定义输出文件目录
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return System.getProperty("user.dir") + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        }));
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        if (gc.getAuthor().equals("1")) {
            // 关闭默认 xml 生成，调整生成 至 根目录
            //.setXml(null);
            // 自定义模板配置，模板可以参考源码 /mybatis-plus/src/main/resources/template 使用 copy
            // 至您项目 src/main/resources/template 目录下，模板名称也可自定义如下配置：
            templateConfig.setController("/template/controller.java.vm");
            templateConfig.setEntity("/template/entity.java.vm");
            templateConfig.setMapper("/template/mapper.java.vm");
            templateConfig.setXml("/template/mapper.xml.vm");
            templateConfig.setService("/template/service.java.vm");
            templateConfig.setServiceImpl("/template/serviceImpl.java.vm");
        } else {
            templateConfig.setEntity("/template/entity.java.vm");
        }
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig()
                //.setTablePrefix(new String[]{prefix})// 此处可以修改为您的表前缀
                .setNaming(NamingStrategy.underline_to_camel)// 表名生成策略
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setRestControllerStyle(true)
                .setEntityLombokModel(true)
                //.setTableFillList(tableFillList)
                //.setExclude(new String[]{"test"}) // 排除生成的表
                // 自定义实体父类
                // .setSuperEntityClass("com.baomidou.demo.TestEntity")
                // 自定义实体，公共字段
                //.setSuperEntityColumns(new String[]{"test_id"})
                // 自定义 mapper 父类
                // .setSuperMapperClass("com.baomidou.demo.TestMapper")
                // 自定义 service 父类
                // .setSuperServiceClass("com.baomidou.demo.TestService")
                // 自定义 service 实现类父类
                // .setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl")
                // 自定义 controller 父类
                //.setSuperControllerClass("com.ethan"+packageName+".controller.AbstractController")
                // 【实体】是否生成字段常量（默认 false）
                // public static final String ID = "test_id";
                // .setEntityColumnConstant(true)
                // 【实体】是否为构建者模型（默认 false）
                // public User setName(String name) {this.name = name; return this;}
                // .setEntityBuilderModel(true)
                // 【实体】是否为lombok模型（默认 false）<a href="https://projectlombok.org/">document</a>
                // Boolean类型字段是否移除is前缀处理
                // .setEntityBooleanColumnRemoveIsPrefix(true)
                // .setCapitalMode(true)// 全局大写命名
                //.setDbColumnUnderline(true)//全局下划线命名
                //.setInclude(new String[]{table}) // 需要生成的表
                .setControllerMappingHyphenStyle(true)
                .setInclude(scanner("表名，多个英文逗号分割").split(","))
                .setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new VelocityTemplateEngine());
        mpg.execute();
        // 打印注入设置，这里演示模板里面怎么获取注入内容【可无】
        System.err.println(mpg.getCfg().getMap().get("abc"));
        System.err.println("【生成完毕！】");
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

}