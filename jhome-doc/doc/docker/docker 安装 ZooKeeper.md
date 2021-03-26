参考博客：https://www.cnblogs.com/LUA123/p/11428113.html

    zookeeper=linux文件系统+监听机制；
    
    拉取镜像：
    docker pull zookeeper
     
    根据镜像启动容器：
    docker run -d -p 2181:2181 --name my-zookeeper --restart always 镜像ID
    
    进入容器：
    docker exec -it 容器ID bash
     
     
     
     
     
     启动zookeeper 服务端
    ./bin/zkServer.sh start
    
    启动zookeeper 客户端
    cd bin # 进入bin目录
    ./zkCli.sh
    或者
    ./bin/zkCli.sh
    
    zookeeper创建节点命令；
    查看根目录下的子节点：ls /
    创建节点：create /path value
    获取节点：get /path
    修改节点值：set /path value
    删除节点：delete /path
    
### 參考博客
https://www.cnblogs.com/summerday152/p/14012622.html
