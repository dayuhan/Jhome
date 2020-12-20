# 启动容器时，报如下错：
    docker: Error response from daemon: failed to copy files: copy file range failed: no space left on device.

## 解决方法 CentOS7 LVM 解决方案
### LVM 简介
![alt 属性文本](../images/lmv.png)
![alt 属性文本](../images/lmv2.png)

    PV　　(Physical Volume)　　物理卷

    VG　　(Volume Group)   　　卷组
    
    LV　　(Logical Volume)　 　逻辑卷
LVM常用命令  
    功能  		    PV管理命令	     VG管理命令  	 	LV管理命令

    scan 扫描		 pvscan				vgscan				lvscan
    create 创建		 pvcreate			vgcreate			lvcreate
    display 显示	 pvdisplay			vgdisplay			lvdisplay
    remove 移除		 pvremove			vgremove			lvremove
    extend 扩展	 						vgextend			lvextend(lvresize)
    reduce 减少	 						vgreduce			lvreduce(lvresize)
    resize改变容量											lvresize
    attribute 改变属性 pvchange			vgchange			lvchange
---
    查看硬盘空间：df -h
    查看索引空间：df -i
    磁盘分区情况：fdisk -l /dev/sda如下：
    
    Disk /dev/sda: 21.5 GB, 21474836480 bytes
    255 heads, 63 sectors/track, 2610 cylinders
    Units = cylinders of 16065 * 512 = 8225280 bytes
    Sector size (logical/physical): 512 bytes / 512 bytes
    I/O size (minimum/optimal): 512 bytes / 512 bytes
    Disk identifier: 0x0002133f
    
    Device Boot      Start         End      Blocks   Id  System
    
    /dev/sda1   *           1          64      512000   83  Linux
    /dev/sda2              64        2611    20458496   8e  Linux LVM






### 第一、扩展虚拟机硬盘空间
#### 1 创建物理分区
    命令进入fdisk命令模式:
    sudo fdisk /dev/sda
![alt 属性文本](../images/lmv6.png)

出现这个界面后输入n，创建新的分区。

![alt 属性文本](../images/lmv7.png)  
Partition type、Partition number和磁盘的范围都是默认就行，敲四个回车就可以。之后输入w写入分区表。

![alt 属性文本](../images/lmv8.png)  
输入sudo fdisk -l查看磁盘信息，会看到新出现的/dev/sda3。

![alt 属性文本](../images/lmv9.png)
### 【注意这里必须重启虚拟机】
#### 2 基于物理分区 创建物理卷（PV）
     partprobe /dev/sda4
     pvcreate /dev/sda4 
     通过 pvdisplay 或 pvs 查看当前的pv信息 

#### 3 基于物理卷（PV）创建卷组（VG  将PV加入到卷组中 通过vgcreate命令）
     vgcreate cotenosvg /dev/sda4
     通过 vgdisplay 或 vgs 命令查看vg的信息 

#### 4 基于卷组（VG）创建逻辑卷(LV)
    lvcreate -n  ExpandDisk1 -L 180G cotenosvg
    通过 lvdisplay 或 lvs 命令查看创建好的逻辑卷


#### 5 格式化文件系统
    mkfs -t ext4 /dev/cotenosvg/ExpandDisk1 用ext4的格式格式化/dev/cotenosvg/ExpandDisk1 
    查看：cd /dev/cotenosvg/ExpandDisk1
![alt 属性文本](../images/lmv3.png)
#### 6 挂载
    创建挂载点
    mkdir /ExpandDisk1
    将/dev/cotenosvg/ExpandDisk1 挂载到/ExpandDisk1
    
    mount /dev/cotenosvg/ExpandDisk1 /ExpandDisk1
然后df -h 可以看到已经挂载到/app下了 ，大小为lv 的大小180G

![alt 属性文本](../images/lmv4.png)

    cd /ExpandDisk1  
touch test.txt 在/ExpandDisk1 下创建一个测试文件test.txt，可以看到该挂载点是可以用了。

![alt 属性文本](../images/lmv5.png)

#### 7 设置开机加载
    echo "/dev/cotenosvg/ExpandDisk1 /ExpandDisk1 ext4 defaults 0 0" >>/etc/fstab
    
     开机自动挂载
    vi /etc/fstab
    
    在最下增加一行：
    /dev/db-lv/lv   /mnt/lvm        ext4    defaults        0       0

#### 参考文献
https://blog.csdn.net/ichen820/article/details/106274392

---


### 第二 其他、暴力方法 删除无用数据
错误原因是，docker  里面存在很多孤立的卷（删除容器的遗留），可以使用以下命令查看

    docker volume ls -qf dangling=true  

将会显示所有"已作废"的卷,具体里面有没有有用信息，自行判断。只需要将这些卷彻底删除，磁盘就够用了，执行：

    docker volume rm $(docker volume ls -qf dangling=true) #会删除mysql相关资源 慎用

**参考文献**
    https://www.cnblogs.com/raisins/p/13224353.html
    http://blog.key9.cn/index.php/article/90.html    
    https://www.cnblogs.com/bitter-first-sweet-last/p/6638634.html