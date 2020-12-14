
在centOS 7下更改完静态ip后发现network服务重启不了
Job for network.service failed because the control process exited with error code
网上最常见的几种做法如下：
1.和 NetworkManager 服务有冲突，这个好解决，直接关闭 NetworkManger 服务就好了， service NetworkManager stop，并且禁止开机启动 chkconfig NetworkManager off 。之后重启就好了。

    systemctl stop NetworkManager 
    systemctl disable NetworkManager
    
    systemctl restart network -- 重启服务

2和配置文件的MAC地址不匹配，这个也好解决，使用ip addr（或ifconfig）查看mac地址


参考：
https://blog.csdn.net/dongfei2033/article/details/81124465
https://www.cnblogs.com/unrecognized/p/11517888.html


