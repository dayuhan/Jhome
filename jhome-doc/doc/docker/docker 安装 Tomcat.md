找 tomcat 安装路径

    docker exec -it tomcat222 /bin/bash 

第一步 映射文件夹

    docker cp tomcat222:/usr/local/tomcat/conf /user/daxu/daxu-tomcat
    docker cp tomcat222:/usr/local/tomcat/webapps /user/daxu/daxu-tomcat
    docker cp tomcat222:/usr/local/tomcat/logs /user/daxu/daxu-tomcat

第二笔 启动映射 挂在docker 外部目录

    docker run  --name tomcat333 -d  -p 8088:8080 tomcat:7.0.96 -v /user/daxu/daxu-tomcat/webapps:/usr/local/tomcat/webapps -v /user/daxu/daxu-tomcat/conf:/usr/local/tomcat/conf -v /user/daxu/daxu-tomcat/logs:/usr/local/tomcat/logs  
     
    sudo docker run  -P 8085:8080  --name tomcat333 -v /user/daxu/daxu-tomcat/webapps:/usr/local/tomcat/webapps -v /user/daxu/daxu-tomcat/conf:/usr/local/tomcat/conf -v /user/daxu/daxu-tomcat/logs:/usr/local/tomcat/logs   -d tomcat:7.0.96


