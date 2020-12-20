### docker 迁移到大盘

docker的默认存储目录是/var/lib/docker,目录结构如下：

    [root@ztscshop docker]# cd /var/lib/docker
    [root@ztscshop docker]# ll
    total 64
    drwx------  2 root root  4096 Jan 24  2019 builder
    drwx------  4 root root  4096 Jan 24  2019 buildkit
    drwx--x--x  3 root root  4096 Jan 24  2019 containerd
    drwx------  4 root root  4096 Oct 24 15:05 containers
    drwx------  3 root root  4096 Jan 24  2019 image
    drwxr-x---  3 root root  4096 Jan 24  2019 network
    drwx------ 80 root root 16384 Oct 24 15:25 overlay2
    drwx------  4 root root  4096 Jan 24  2019 plugins
    drwx------  2 root root  4096 Oct 22 11:57 runtimes
    drwx------  2 root root  4096 Jan 24  2019 swarm
    drwx------  2 root root  4096 Oct 24 15:13 tmp
    drwx------  2 root root  4096 Jan 24  2019 trust
    drwx------ 15 root root  4096 Feb  7  2019 volumes
    [root@ztscshop docker]# 

1.查看磁盘占用情况：df -h

    [test@localhost docker]$ df -lh
    Filesystem               Size  Used Avail Use% Mounted on
    /dev/mapper/centos-root   50G  50G   0G   100% /
    devtmpfs                 7.7G     0  7.7G   0% /dev
    tmpfs                    7.8G     0  7.8G   0% /dev/shm
    tmpfs                    7.8G   34M  7.7G   1% /run
    tmpfs                    7.8G     0  7.8G   0% /sys/fs/cgroup
    /dev/sda1                494M  126M  369M  26% /boot
    /dev/mapper/centos-home  1.8T  472G  1.4T  27% /home
    tmpfs                    1.6G     0  1.6G   0% /run/user/0
    tmpfs                    1.6G     0  1.6G   0% /run/user/1000
2.查看docker自身的内存占用：

    docker system df

3.迁移/var/lib/docker目录（docker默认目录）到 磁盘空间比较大的目录下 /ExpandDisk1。

    mv /var/lib/docker /ExpandDisk1

4.停止docker服务

    systemctl stop docker

5 配置 /etc/systemd/system/docker.service.d/devicemapper.conf。查看 devicemapper.conf 是否存在。如果不存在，就新建。

    mkdir -p /etc/systemd/system/docker.service.d/    #新建目录
    vi /etc/systemd/system/docker.service.d/devicemapper.conf  #创建配置文件

6 然后在 devicemapper.conf 写入：（同步的时候把父文件夹一并同步过来，实际上的目录应在 /home/docker/lib/docker ）

    [Service]
    ExecStart=
    ExecStart=/usr/bin/dockerd --graph=/ExpandDisk1/docker

7 为docker做软连接

    ln -s /ExpandDisk1/docker ./    #给当前根目录创建Docker 快捷方式 软连接

8 重新加载 docker

    重新加载docker
    systemctl daemon-reload
    重启docker
    systemctl restart docker
    随机器启动
    systemctl enable docker

9 为了确认一切顺利，运行
    docker info
命令检查Docker 的根目录.它将被更改为 /home/docker/lib/docker
    ...
    Docker Root Dir: /home/docker/lib/docker
    Debug Mode (client): false
    Debug Mode (server): false
    Registry: https://index.docker.io/v1/
    ...