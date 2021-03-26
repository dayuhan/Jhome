###第一种出发方法自动继承 

在父级项目中将部分子模块中不需要继承的jar包放入中，在需要引入的子模块中增依赖继承如下：
父：   

    <dependencyManagement>
            <dependencies>
                <dependency>
                    <groupId>cn.codeyi</groupId>
                    <artifactId>codeyi-common</artifactId>
                    <version>${codeyi.version}</version>
                </dependency>
            </dependencies>
        </dependencyManagement> 

需继承子模块：    
### 第二种方法 排除父类依赖
    <dependencies>
                <dependency>
                    <groupId>cn.codeyi</groupId>
                    <artifactId>codeyi-common</artifactId>
                    <!--自动继承父级版本号-->
              </dependency>
     </dependencies> 
     
     
在子模块中直接排除JAR包依赖
    <dependencies>
            <dependency>
                    <groupId>cn.codeyi</groupId>
                    <artifactId>codeyi-common</artifactId>
                    <version>${pmsuite.version}</version>
                <exclusions>
                    <exclusion>
                       <groupId>cn.codeyi</groupId>
                       <artifactId>codeyi-common</artifactId>
                    </exclusion>
                </exclusions>
            </dependency>
        </dependencies> 
        
### 第三 排除 父POM 打包组件
<!--    排除父pom中的通用打包组件-->
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                <!--    跳过加载父级组件-->
                    <skip>true</skip>
                </configuration>
            </plugin>
        </plugins>
    </build>