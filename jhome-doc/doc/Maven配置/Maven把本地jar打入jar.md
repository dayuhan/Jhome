### maven system path，加载本地jar   
当引用第三方包，且没有源代码时候，可以使用system path
  
        <dependency>
            <groupId>ctec</groupId>
            <artifactId>xxx-core</artifactId>
            <version>1.0</version>
            <scope>system</scope>
            <systemPath>${project.basedir}/src/main/resources/libs/ctec-xxx-core.jar</systemPath>
        </dependency>
  
仅这样做，并不能将jar包含到war中，需要使用<includeSystemScope>true</includeSystemScope>

 
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <includeSystemScope>true</includeSystemScope>
            </configuration>
        </plugin>
    </plugins>
  

常见内置变量

    ${basedir} 项目根目录
    ${project.build.directory} 构建目录，缺省为target
    ${project.build.outputDirectory} 构建过程输出目录，缺省为target/classes
    ${project.build.finalName} 产出物名称，缺省为${project.artifactId}-${project.version}
    ${project.packaging} 打包类型，缺省为jar
    ${project.xxx} 当前pom文件的任意节点的内容
 
 
 #### 参考博客
 https://www.cnblogs.com/xiang--liu/p/11451521.html