## Maven 错误 :The POM for com.xxx:jar:0.0.1-SNAPSHOT is invalid, transitive dependencies (if any) will not be available
但是呢，对根项目的pom， 执行mvn clean complie 是没问题的，但是对 appservice-darc  执行 mvn clean complie是不行的，出现下面错误：
    
    复制代码
    [INFO] ------------------------------------------------------------------------
    [INFO] Building appservice-darc 0.0.1-SNAPSHOT
    [INFO] ------------------------------------------------------------------------
    [WARNING] The POM for com.worepay:appservice-entity:jar:0.0.1-SNAPSHOT is invalid, transitive dependencies (if any) will not be available, enable debug logging for more details
    [INFO] 
    [INFO] --- maven-resources-plugin:2.6:resources (default-resources) @ appservice- ---
    [INFO] Using 'UTF-8' encoding to copy filtered resources.
    [INFO] Copying 4 resources
    [INFO] Copying 2 resources
    [INFO] 
    [INFO] --- maven-compiler-plugin:3.1:compile (default-compile) @ appservice- ---
    [INFO] Changes detected - recompiling the module!
    [INFO] Compiling 10 source files to F:\dev\SVN\GYF\newAppservice\appservice-\target\classes
    [INFO] -------------------------------------------------------------
    [ERROR] COMPILATION ERROR : 
    [INFO] -------------------------------------------------------------
    [ERROR] /F:/dev/SVN/GYF/newAppservice/src/main/java/com/worepay/appservice//Banner.java:[3,25] 程序包javax.persistence不存在
    [ERROR] /F:/dev/SVN/GYF/newAppservice/src/main/java/com/worepay/appservice//Banner.java:[4,25] 程序包javax.persistence不存在
    [ERROR] /F:/dev/SVN/GYF/newAppservice/src/main/java/com/worepay/appservice//Banner.java:[5,25] 程序包javax.persistence不存在
    [ERROR] /F:/dev/SVN/GYF/newAppservice/src/main/java/com/worepay/appservice//Banner.java:[8,2] 找不到符号
      符号: 类 Table
    [ERROR] /F:/dev/SVN/GYF/newAppservice/src/main/java/com/worepay/appservice//Banner.java:[12,10] 找不到符号
      符号:   类 Id
      位置: 类 com.worepay.appservice..Banner
    [ERROR] /F:/dev/SVN/GYF/newAppservice/src/main/java/com/worepay/appservice//Banner.java:[13,10] 找不到符号
      符号:   类 Column
      位置: 类 com.worepay.appservice..Banner
    [ERROR] /F:/dev/SVN/GYF/newAppservice/src/main/java/com/worepay/appservice//Banner.java:[17,10] 找不到符号
      符号:   类 Column
  
复制代码
IDEA中项目源码中是没有错误的，说明编译是ok的，但是执行maven compile 就是不行。。  检查发现 persistence-api-1.0.jar 依赖也确实是存在的。但是为什么mvn操作就总是不行呢？

appservice-darc 依赖了 appservice-entity，而从上面的日志看， appservice-entity好像有什么问题。。 pom 为什么是 invalid ？ 打开pom 是没用任何错误提示的呢， 那就奇怪了。。

 

 

后面通过maven 调试发现（添加 -X 参数 ），发现appservice-entity确实还是有问题的。原因是appservice-entity 的pom 引用了一个本地的jar，它的写法是 相对路径，从而导致appservice-entity 所依赖的所有jar都不可用了，如下：

    
            <dependency>
                <groupId>com.oracle</groupId>
                <artifactId>ojdbc7</artifactId>
                <version>12.1.0.1.0</version>
                <scope>system</scope>
                <systemPath>${project.basedir}/lib/ojdbc14_g.jar</systemPath>
            </dependency>
    
 

从而，maven compile 失败了。 怎么解决呢？ 提示告诉我，需要写成绝对路径的形式。 把那个本地jar 的地址改为绝对路径就好了：


        <dependency>
            <groupId>com.oracle</groupId>
            <artifactId>ojdbc7</artifactId>
            <version>12.1.0.1.0</version>
            <scope>system</scope>
            <systemPath>F:/dev/SVN/GYF/rxw/newAppservice/appservice-entity/lib/ojdbc14_g.jar</systemPath>
        </dependency>

 不过，发现 还行需要先把appservice-entity 先install，不install 还不行。我开始就是compile了一下，以为都在一个项目了， 应该不至于那么傻，引用不到吧。结果还真是，还是报之前一样的错误。后面只有乖乖的install 一下，结果就好了。

 

观察发现，appservice-darc 是从本地maven 仓库中去获取appservice-entity 的jar ， 因为单单是maven compile，不能要保证本地仓库中的appservice-entity 已经是最新的，是没用的。如果事先对appservice-entity执行clean compile，那么clean 操作会清除本地仓库对应的 jar， 所以本地仓库中的appservice-entity 是不存在的，仅仅存在于当前项目的target目录。