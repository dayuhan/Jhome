第一步，设置Vmware 为桥接模式 桥接模式：
![图片标题](https://s1.51cto.com/images/20180825/1535188656993030.png?x-oss-process=image/watermark,size_16,text_QDUxQ1RP5Y2a5a6i,color_FFFFFF,t_100,g_se,x_10,y_10,shadow_90,type_ZmFuZ3poZW5naGVpdGk=)

    https://blog.51cto.com/13648313/2164307

第二步设置linux系统为静态地址：

    IP地址的设置一般是指设置某个网卡端口的IP，所以想设置IP，首先需要弄清楚是要为哪个网卡端口设置IP，CentOS7与一般的Linux系统一样，可以通过ifconfig命令查询当前的网络设置。

![图片标题](https://leanote.com/api/file/getImage?fileId=5fb1cfc6ab6441042100029c)


    由上图可知我的系统目前是使用网卡ens33访问Internet的，我们要设置的就是ens33的IP地址。
    
    
    弄清楚要设置IP的对象后，需要到/etc/sysconfig/network-scripts/下修改enp0s3的配置文件ifcfg-ens33。

![图片标题](https://leanote.com/api/file/getImage?fileId=5fb1cefeab64410421000294)



    使用vim打开并做如下修改

![图片标题](https://leanote.com/api/file/getImage?fileId=5fb1cefeab64410421000293)

    配置静态参数：
    BOOTPROTO="static"
    DEVICE="ens33"
    ONBOOT="yes"
    IPADDR="192.168.20.103"
    GATEWAY="192.168.20.1"
    （注意：设置和主机的网关一致才行，之后需要重启网络）
    NETMASK="255.255.255.0"
    DNS1="8.8.8.8"
    修改后保存并重启network服务即可
    
    service network restart

    有些修改后可能无法用CentOS联网的还需要添加DNS解析

![图片标题](https://leanote.com/api/file/getImage?fileId=5fb1d1a8ab6441020a0002c6)


在centOS 7下更改完静态ip后发现network服务重启不了
Job for network.service failed because the control process exited with error code
网上最常见的几种做法如下：
1.和 NetworkManager 服务有冲突，这个好解决，直接关闭 NetworkManger 服务就好了， service NetworkManager stop，并且禁止开机启动 chkconfig NetworkManager off 。之后重启就好了。

    systemctl stop NetworkManager 
    systemctl disable NetworkManager
    
    systemctl restart network -- 重启服务

2和配置文件的MAC地址不匹配，这个也好解决，使用ip addr（或ifconfig）查看mac地址






