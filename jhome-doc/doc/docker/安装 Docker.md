安装Docker

    vim /etc/docker/daemon.json

1、查看你当前的内核版本

    uname -r

2、更新yum包

    yum clean all
    yum makecache
    sudo yum update

3、卸载已安装的docker（如果安装过的话）

    yum remove docker docker-common docker-selinux docker-engine

4、安装需要的软件包

    sudo yum install -y yum-utils device-mapper-persistent-data lvm2
    yum install -y yum-utils device-mapper-persistent-data lvm2

5、设置yum源
0 新版的 Docker 推荐使用 json 配置文件的方式 Docker 修改镜像源地址
几个速度比较快的镜像地址 Docker 官方中国区:

    https://registry.docker-cn.com 网易: http://hub-mirror.c.163.com 中科大: https://docker.mirrors.ustc.edu.cn
    sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
    sudo yum-config-manager --add-repo http://hub-mirror.c.163.com/docker-ce/linux/centos/docker-ce.repo
    yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

6、可以查看所有仓库中所有docker版本，并选择特定版本安装

    yum list docker-ce --showduplicates | sort -r

7、重新安装docker

    sudo yum install docker-ce
    sudo yum install docker-ce

8、启动docker

    sudo systemctl start docker

9、验证安装是否成功

    docker virsion //查看docker的版本
    docker ps //查看当前正在运行的容器

10、开机自启

    sudo systemctl enable docker
