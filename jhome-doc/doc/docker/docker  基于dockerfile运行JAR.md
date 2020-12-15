### docker 基于dockerfile 运行 JAR
#### 第零步：准备docker java:8u111 linux环境
    docker pull java:8u111
#### 第一步：准备yml文件配置其可扩展yml
    spring:
      # 应用程序名称
      application:
        name: jhome-account
      profiles:
        active: default
        include: accountService #设置扩展项目

#### 第二步：准备环境目录
>/deploy/docker_jar_data/jhome/
>>config ->application.yml  
>>lib -> jhome.jar  
>>Dockerfile

#### 第三步 创建Dockerfile
    FROM java:8u111
    COPY ./config /deploy/docker_jar_data/jhome/config
    COPY ./lib /deploy/docker_jar_data/jhome/lib 
    EXPOSE 8080 
    ENTRYPOINT ["java","-jar","-Dspring.config.location=/deploy/docker_jar_data/jhome/config/application-accountService.yml","/deploy/docker_jar_data/jhome/lib/jhome.jar"]  
#### 第四步 创建镜像文件，把jar打包成 镜像文件
    docker build -t jhome .  # 在当前dockerFile下执行编译
#### 第五步 查看镜像是否生成
    docker images
#### 第六步 运行容器读取新的配置文件
    docker run --name demo2 -p 8035:8080 -d jhome
#### 第七步 查看日志
    docker logs -f -t jhome  
#### 参考博文
    https://www.cnblogs.com/guanxiaohe/p/11906092.html