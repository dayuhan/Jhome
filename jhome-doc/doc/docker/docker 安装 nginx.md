
1、下载镜像文件

    docker pull nginx

1、删除原本装好的nginx容器（如果安装过）

    docker rm nginx(容器名字或者id)

2、删除nginx镜像文件

    docker images //查看镜像文件
    docker rmi nginx //删除nginx镜像
    docker rmi -f nginx 强制删除nginx镜像

3、重装并启动nginx

    docker run --name nginx -d -p 80:80 nginx​


