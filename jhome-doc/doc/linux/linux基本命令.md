1、防火墙设置

    systemctl status firewalld查看firewalld服务状态
    
    firewall-cmd --state查看firewall的状态，是否在运行
    
    service firewalld start 开启firewalld服务
    
    service firewalld start 开启firewalld服务
    
    service firewalld stop 关闭firewalld服务
    
    service firewalld restart 重启firewalld服务
    
    firewall-cmd --list-all 查看防火墙规则

    firewall-cmd --query-port=8080/tcp 查看8080端口是否开放
    
    firewall-cmd --permanent --add-port=8080/tcp 开放8080端口访问权限，设置后需要重启防火墙
    
    firewall-cmd --permanent --remove-port=8080/tcp 关闭8080端口访问权限，设置后需要重启防火墙
    
    firewall-cmd --reload重启防火墙
    
    service firewalld status ；查看防火墙状态
    service firewalld stop：关闭防火墙 

    安装vim
    docker在进入容器后无法修改文件，因为vi指令不生效。这个时候就要主动安装
    apt-get update
    apt-get install vim
    就可以使用vi修改文件了

2、查看权限

    ll tracker.conf

3、查看ip

    ip addr

4、赋予权限

    chmod 777 tracker.conf

5、查看端口

    netstat -aon |grep 221222

6、搜索文件 返回路径

    whereis nginx

7、查看进程

    jps
    ps -ef|grep zookerper

8、查看当前位于哪个目录

    pwd
9、创建目录

    mkdir 

10、送宿主主机往容器容器中拷贝文件

    docker cp /daxu/ storage:/data/fast_data/data/05/00
     
    1、将一个文件夹下的所有内容复制到另一个文件夹下
    cp -r /home/packageA/* /home/cp/packageB/
    或
    cp -r /home/packageA/. /home/cp/packageB/
    这两种方法效果是一样的。
    
    方法示例：
    在这里插入图片描述
    
    2、将一个文件夹复制到另一个文件夹下
    cp -r /home/packageA /home/packageB
    
    运行命令之后packageB文件夹下就有packageA文件夹了。
    
    方法示例：
    在这里插入图片描述
    
    3、删除一个文件夹及其下面的所有文件
    rm -rf /home/packageA
    
    -r表示向下递归，不管有多少级目录，一并删除
    -f表示直接强行删除，不作任何提示的意思
    方法示例：
    在这里插入图片描述
    
    4、移动一个文件夹到另一个文件夹下面
    mv /home/packageA /home/packageB/
    或
    mv /home/packageA /home/packageB
    这两种方法效果是一样的。
    
    方法示例：
    在这里插入图片描述
    
    5、移动一个文件夹下的所有内容到另一个文件夹下面
    mv /home/packageA/* /home/packageB/
    
    方法示例：
    在这里插入图片描述

11、更改为图形界面模式

    systemctl set-default graphical.target

12、更改为命令行模式

    systemctl set-default multi-user.target



