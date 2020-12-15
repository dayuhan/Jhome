### Docker 通过 JAVA:8u111 运行 JAR
    下载docker java 镜像文件： 
     docker pull java:8u111
        
    docker 基于 java8u111 运行： 
    docker run -e TZ="Asia/Shanghai" -d -p 9090:9090 -v /usr/springboot-1.jar:/usr/springboot-1.jar --name springboot java:8u111 java -jar /usr/springboot-1.jar
    
    docker 基于 java8u111 运行随系统启动： 
    docker run -e TZ="Asia/Shanghai"  -d -p 8088:8088  -v /root/daxu/jhome.jar:/usr/jhome.jar --name jhome --restart=always java:8u111 java -jar /usr/jhome.jar --server.port=8088

 参数解释：

    -d 表示在后台启动 
    -p 9090:9090 表示将容器的端口 映射成宿主主机的端口，否则9090端口访问不到 
    -v /usr/springboot-1.jar:/usr/springboot-1.jar 表示将宿主主机的jar文件，映射到容器中（分号前为宿主主机的路径，分号后为容器中的路径） 
    --name springboot 表示为该容器取一个全局唯一的名称，这里我取的名称为springboot 
    java:8u111 表示镜像文件的名称和tag 
    java -jar /usr/springboot-1.jar 表示运行jar包，注意：这里的jar包为容器中的位置，是通过前面的-v属性映射的
    --server.port 启动参数
    -e TZ="Asia/Shanghai"  容器是标准时间，比主机时间晚一天，需要设置
##### 参考博客
-----------
 https://www.cnblogs.com/zhangwufei/p/9034997.html  
 https://my.oschina.net/aidelingyu/blog/877970
