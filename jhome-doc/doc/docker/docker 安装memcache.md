**拉取并运行镜像**

     docker run -p 11211:11211 --name memcache memcached

  测试并且进入docker容器

测试是否启动成功

    netstat -luntp|grep 11211

或者

    docker ps

安装telnet

    yum install -y telnet

通过telnet方式连接memcached

    telnet 127.0.0.1 11211

设置值，age是key，0是标志位，900是生命周期，8代表所占字节数，回车之后的10是value

    set age 0 900 8

    10

获取值

    get agea

退出

    exit 


