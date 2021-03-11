### IDEA的Docker插件实战(Dockerfile篇)


pom.xml文件中加入docker的打包方式

            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <!--加入下面两项配置-->
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <includeSystemScope>true</includeSystemScope>
                </configuration>
            </plugin>

            <!--使用docker-maven-plugin插件-->
            <plugin>
                <groupId>com.spotify</groupId>
                <artifactId>docker-maven-plugin</artifactId>
                <version>1.0.0</version>

                <!--将插件绑定在某个phase执行-->
                <executions>
                    <execution>
                        <id>build-image</id>
                        <!--将插件绑定在package这个phase上。也就是说，用户只需执行mvn package ，就会自动执行mvn docker:build-->
                        <phase>package</phase>
                        <goals>
                            <goal>build</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <!--指定生成的镜像名-->
                    <imageName>svc/${project.artifactId}</imageName>
                    <!--指定标签-->
                    <imageTags>
                        <imageTag>latest</imageTag>
                    </imageTags>
                    <!-- 指定 Dockerfile 路径-->
                    <dockerDirectory>${project.basedir}/src/main/docker</dockerDirectory>
                    <!--指定远程 docker api地址-->
                    <dockerHost>http://192.168.226.128:2375</dockerHost>
                    <!-- 这里是复制 jar 包到 docker 容器指定目录配置 -->
                    <resources>
                        <resource>
                            <targetPath>/</targetPath>
                            <!--jar 包所在的路径  此处配置的 即对应 target 目录-->
                            <directory>${project.build.directory}</directory>
                            <!-- 需要包含的 jar包 ，这里对应的是 Dockerfile中添加的文件名　-->
                            <include>${project.build.finalName}.jar</include>
                        </resource>
                    </resources>
                </configuration>
            </plugin>

在src/main下面新建一个文件夹docker，并且新建一个docker配置的文件DockerFile

    # 基于哪个镜像
    FROM java:8
    # 将本地文件夹挂载到当前容器
    VOLUME /tmp
    # 拷贝文件到容器，svc_user-1.0-SNAPSHOT.jar这里是maven打包后的名字
    ADD svc_user-1.0-SNAPSHOT.jar app.jar
    RUN bash -c 'touch /app.jar'
    # 配置容器启动后执行的命令
    #ENTRYPOINT  ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
    ENTRYPOINT  ["java","-jar","/app.jar"]
    


至此，使用maven的mvn clean 和mvn的clean就可以打包并自动执行mvn docker:build，将镜像上传到docker server
————————————————
版权声明：本文为CSDN博主「yulouchunqiu」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/qq_38294335/article/details/108226433


    https://cloud.tencent.com/developer/article/1494921
    https://blog.csdn.net/qq_38294335/article/de