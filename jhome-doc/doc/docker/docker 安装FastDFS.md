
    开启：22122 端口 8888端口 23000 端口
    
    开放8080端口访问权限，设置后需要重启防火墙
    firewall-cmd --permanent --add-port=22122/tcp 
    
    开放8888端口端口访问权限，设置后需要重启防火墙
    firewall-cmd --permanent --add-port=8888/tcp 
    
    开放8080端口访问权限，设置后需要重启防火墙
    firewall-cmd --permanent --add-port=23000/tcp 
    firewall-cmd --reload重启防火墙
     
    非挂在
    启动  tracker
    
    docker run -d --name tracker --net=host morunchang/fastdfs sh tracker.sh
    
    启动 storage server
    
    docker run -d --name storage --net=host -e TRACKER_IP=<your tracker server address>:22122 -e GROUP_NAME=<group name> morunchang/fastdfs sh storage.sh


第二种方式 挂在文件


      创建数据挂载目录
      mkdir -p /apps/fastdfs/tracker1/data
      mkdir -p /apps/fastdfs/storage1/data
      
      拉取 fastdfs:
      docker pull  morunchang/fastdfs
      
      运行调度器： 
     docker run -d --name tracker1 --net=host --restart always \
     -v /apps/fastdfs/tracker1/data:/data/fast_data/ \
     morunchang/fastdfs sh tracker.sh
      
      开启调度器 22122端口：
      firewall-cmd --zone=public --add-port=22122/tcp --permanent 
      
      重启防火墙
      firewall-cmd --reload  
      查看防火墙端口开启状态
      firewall-cmd --list-all
      
      进入容器配置参数
      docker exec -it tracker1  /bin/bash

----------

      启动storage：
      docker run -d --name storage1 --net=host --restart always \
      -v /apps/fastdfs/storage1/data:/data/fast_data/ \
      morunchang/fastdfs sh storage.sh
      
      开放23000端口访问权限，设置后需要重启防火墙
      firewall-cmd --zone=public --add-port=23002/tcp --add-port=9101/tcp --permanent 
      
      重启防火墙
      firewall-cmd --reload  
      查看防火墙端口开启状态
      firewall-cmd --list-all
      
      配置storage.conf ：vim storage.conf  
      tracker_servier=192.168.52.130:22122 ...
      http.server_prot=8888
      
      配置nginx.conf：vim /data/nginx/conf/nginx.conf 
      nginxprot:8888
      
      开启上传测试：
      docker run -tid --name fdfs_sh --net=host morunchang/fastdfs sh

【成功安装FastDFS-安装fsdfs】
 https://blog.csdn.net/zhanngle/article/details/88770028

